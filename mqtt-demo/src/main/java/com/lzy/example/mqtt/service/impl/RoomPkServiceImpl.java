package com.lzy.example.mqtt.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson.JSONObject;
import com.lzy.example.mqtt.domain.ResponseResult;
import com.lzy.example.mqtt.domain.enums.TeamNameEnum;
import com.lzy.example.mqtt.domain.request.CreatePkRequest;
import com.lzy.example.mqtt.domain.request.SendGiftRequest;
import com.lzy.example.mqtt.domain.response.PkRecordResponse;
import com.lzy.example.mqtt.domain.response.UserInfoResponse;
import com.lzy.example.mqtt.mqtt.MyMQTTClient;
import com.lzy.example.mqtt.service.RedisService;
import com.lzy.example.mqtt.service.RoomPkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Lizuoyang
 * @date 2024/04/12
 */
@Service
@Slf4j
public class RoomPkServiceImpl implements RoomPkService {
    public static final String PK_CREATE_TOPIC = "pk/inRoom";
    public static final Integer PK_JOIN_NUMBER = 2;
    public static final DelayQueue<DelayedTask> delayedQueue = new DelayQueue<>();
    private static Integer spiritFlag = 0;
    /**
     * 客户端
     */
    @Autowired
    private MyMQTTClient myMQTTClient;

    /**
     * redis
     */
    @Autowired
    private RedisService redisService;

    @Override
    public String createPk(CreatePkRequest request) {
        if (CollUtil.isEmpty(request.getUserIds()) || request.getUserIds().size() < PK_JOIN_NUMBER) {
            throw new RuntimeException("参与最少不能低于2人！");
        }

        if (request.getUserIds().size() % PK_JOIN_NUMBER > 0) {
            throw new RuntimeException("参与人数必须是双数！");
        }
//        List<List<String>> partition = ListUtil.partition(request.getUserIds(), request.getUserIds().size() / PK_JOIN_NUMBER);
        List<List<String>> partition = userIdListPartition(request.getUserIds());
        // 订阅主题
        partition.get(0).forEach(item -> createTeam(item, request.getRoomId(), TeamNameEnum.RED.getCode()));
        partition.get(1).forEach(item -> createTeam(item, request.getRoomId(), TeamNameEnum.BLUE.getCode()));
        // 开启向客户端推送pk数据
        spiritFlag = 0;
        // 创建延迟队列，延迟推送pk结果
        createDelayQueue(request);
        // 创建定时任务，每秒推送pk数据
        createScheduler(request);
        return PK_CREATE_TOPIC;
    }

    @Override
    public void sendMsg(SendGiftRequest request) {
        UserInfoResponse userInfoResponse = (UserInfoResponse)redisService.hGet(PK_CREATE_TOPIC + ":" + request.getRoomId(), request.getUserId());
        if (ObjUtil.isNull(userInfoResponse)) {
            throw new RuntimeException("用户异常");
        }
        userInfoResponse.setPointNum(userInfoResponse.getPointNum() + request.getGiftNum());
        log.info("用户：{}, 刷了礼物：{}， 数量：{}", request.getUserId(), request.getGiftId(), request.getGiftNum());
        myMQTTClient.publish(JSONObject.toJSONString(ResponseResult.success(2001,"用户刷礼物信息", userInfoResponse)), PK_CREATE_TOPIC, 1);
        redisService.hSet(PK_CREATE_TOPIC + ":" + request.getRoomId() ,request.getUserId(), userInfoResponse);
    }

    private List<List<String>> userIdListPartition(List<String> userIds) {
        AtomicInteger teamFlag = new AtomicInteger();
        AtomicInteger addCount = new AtomicInteger();
        List<List<String>> partition = new ArrayList<>();
        if (userIds.size() == 2) {
            partition = ListUtil.partition(userIds, 1);
        } else {
            List<String> redTeam = new ArrayList<>();
            List<String> blueTeam = new ArrayList<>();
            userIds.forEach(item -> {
                if (teamFlag.get() == 0) {
                    redTeam.add(item);
                    if (addCount.getAndIncrement() == 1) {
                        teamFlag.set(1);
                        addCount.set(0);
                    }
                } else {
                    blueTeam.add(item);
                    if (addCount.getAndIncrement() == 1) {
                        teamFlag.set(0);
                        addCount.set(0);
                    }
                }
            });
            partition.add(redTeam);
            partition.add(blueTeam);
        }

        return partition;
    }

    private void createTeam(String userId, String roomId, String teamName) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUserId(userId);
        userInfoResponse.setPointNum(0);
        userInfoResponse.setTeamName(teamName);
        redisService.hSet(PK_CREATE_TOPIC + ":" + roomId ,userId, userInfoResponse);
    }

    private void createScheduler(CreatePkRequest request) {
        ScheduledExecutorService spiritScheduler = Executors.newSingleThreadScheduledExecutor();
        spiritScheduler.scheduleAtFixedRate(() -> {
            if (spiritFlag == 0) {
                PkRecordResponse pkRecordResponse = new PkRecordResponse();
                Map<Object, Object> maps = redisService.hGetAll(PK_CREATE_TOPIC + ":" + request.getRoomId());
                List<UserInfoResponse> list = maps.values().stream().map(item -> (UserInfoResponse) item).collect(Collectors.toList());
                Map<String, Integer> sumPointNumsMap = sumPointNums(request.getRoomId());
                pkRecordResponse.setBlueTeamScore(sumPointNumsMap.get(TeamNameEnum.BLUE.getCode()));
                pkRecordResponse.setRedTeamScore(sumPointNumsMap.get(TeamNameEnum.RED.getCode()));
                pkRecordResponse.setUserList(list);
                // 发送消息到客户端
                myMQTTClient.publish(JSONObject.toJSONString(ResponseResult.success(2002, "房间pk明细", pkRecordResponse)), PK_CREATE_TOPIC, 1);
            }

        }, 0, 1, TimeUnit.SECONDS); // 从现在开始，每两秒执行一次任务
    }

    private void createDelayQueue(CreatePkRequest request) {
        // 创建一个调度器
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // 添加延时任务到队列
        delayedQueue.offer(new DelayedTask(PK_CREATE_TOPIC + "/" + request.getRoomId(), request.getPkTime() * 1000));

        log.info("延时队列任务数：" + delayedQueue.size());

        // 启动任务调度，用于处理延时队列中的任务
        scheduler.scheduleAtFixedRate(() -> {
            DelayedTask task = delayedQueue.poll();
            if (task != null) {
                log.info(task.getName());
                Map<String, Integer> resultMap = sumPointNums(request.getRoomId());
                List<Map.Entry<String,Integer>> list = new ArrayList(resultMap.entrySet());
                log.info("pk分数：{}", JSONObject.toJSONString(resultMap));
                Map<String, Object> pkResult = new HashMap<>();
                pkResult.put("roomId", request.getRoomId());
                // 小于0：蓝队胜利，等于0：平局，大于0：红队胜利，
                pkResult.put("pkResult", list.get(0).getValue() - list.get(1).getValue());
                // 关闭向客户端推送pk数据
                spiritFlag = 1;
                // 向订阅的客户端发送pk结果
                myMQTTClient.publish(JSONObject.toJSONString(ResponseResult.success(2003,"pk结果（小于0：蓝队胜利，等于0：平局，大于0：红队胜利）",pkResult)), PK_CREATE_TOPIC, 1);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * 统计pk分数
     * @param roomId
     * @return {@link Map}<{@link String}, {@link Integer}>
     */
    private Map<String, Integer> sumPointNums(String roomId) {
        Map<String, Integer> resultMap = new HashMap<>();
        Map<Object, Object> maps = redisService.hGetAll(PK_CREATE_TOPIC + ":" + roomId);
        List<UserInfoResponse> list = maps.values().stream().map(item -> (UserInfoResponse) item).collect(Collectors.toList());
        Map<String, List<UserInfoResponse>> groupMaps = list.stream().collect(Collectors.groupingBy(UserInfoResponse::getTeamName));
        for (TeamNameEnum nameEnum : TeamNameEnum.values()) {
            List<UserInfoResponse> items = groupMaps.get(nameEnum.getCode());
            resultMap.put(nameEnum.getCode(), items.stream().mapToInt(UserInfoResponse::getPointNum).sum());
        }
        return resultMap;
    }

    static class DelayedTask implements Delayed {
        private final String name;
        private final long executeTime;

        public DelayedTask(String name, long delay) {
            this.name = name;
            this.executeTime = System.currentTimeMillis() + delay;
        }

        public String getName() {
            return name;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long delay = executeTime - System.currentTimeMillis();
            return unit.convert(delay, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed other) {
            return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), other.getDelay(TimeUnit.MILLISECONDS));
        }
    }
}
