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
     * 参与pk用户id
     */
    private List<String> userIds;
    /**
     * pk时长
     */
    private Integer pkTime;
}
