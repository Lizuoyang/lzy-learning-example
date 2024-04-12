package com.lzy.example.mqtt.domain.request;

import lombok.Data;

import java.util.List;

/**
 * 创建PK请求对象
 * @author Lizuoyang
 * @date 2024/04/12
 */
@Data
public class CreatePkRequest {
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 参与pk的红队用户id
     */
    private List<String> redUserIds;
    /**
     * 参与pk的蓝队用户id
     */
    private List<String> blueUserIds;
    /**
     * pk时长
     */
    private Integer pkTime;
    /**
     * 惩罚时长
     */
    private Integer pkPunishTime;
}
