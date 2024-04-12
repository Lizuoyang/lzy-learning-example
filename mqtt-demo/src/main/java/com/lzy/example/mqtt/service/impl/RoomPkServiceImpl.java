package com.lzy.example.mqtt.service.impl;

import com.lzy.example.mqtt.domain.request.CreatePkRequest;
import com.lzy.example.mqtt.domain.response.UserInfoResponse;
import com.lzy.example.mqtt.mqtt.MyMQTTClient;
import com.lzy.example.mqtt.service.RedisService;
import com.lzy.example.mqtt.service.RoomPkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
@Slf4j
public class RoomPkServiceImpl implements RoomPkService {
    private static final String TOPIC = "pk/room:";
    private static final DelayQueue<DelayedTask> delayedQueue = new DelayQueue<>();

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
    public void createPk(CreatePkRequest request) {
        // 订阅主题
        request.getRedUserIds().forEach(item -> createTopicAndSubscribe(item, request.getRoomId(), "red"));
        request.getBlueUserIds().forEach(item -> createTopicAndSubscribe(item, request.getRoomId(), "blue"));
        // 创建调度器，延迟推送pk结果
        createDelayQueue(request);
    }

    private void createTopicAndSubscribe(String userId, String roomId, String teamName) {
        String topicName = TOPIC + roomId;
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUserId(userId);
        userInfoResponse.setPointNum(0);
        redisService.hSet(topicName + ":" + teamName ,userId, userInfoResponse);
        myMQTTClient.subscribe(topicName,1);
    }

    private static void createDelayQueue(CreatePkRequest request) {
        // 创建一个调度器
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // 添加延时任务到队列
        delayedQueue.offer(new DelayedTask(TOPIC + request.getRoomId(), request.getPkTime() * 60 * 1000));

        log.info("延时队列任务数：" + delayedQueue.size());

        // 启动任务调度，用于处理延时队列中的任务
        scheduler.scheduleAtFixedRate(() -> {
            DelayedTask task = delayedQueue.poll();
            if (task != null) {
                log.info(task.getName());
            }
        }, 0, 1, TimeUnit.SECONDS);
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
