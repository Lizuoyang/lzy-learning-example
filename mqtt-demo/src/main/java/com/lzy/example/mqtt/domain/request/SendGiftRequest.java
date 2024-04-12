package com.lzy.example.mqtt.domain.request;

import lombok.Data;

/**
 * 送礼请求类
 * @author Lizuoyang
 * @date 2024/04/12
 */
@Data
public class SendGiftRequest {
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 礼物id
     */
    private String giftId;
    /**
     * 礼物数量
     */
    private Integer giftNum;
    /**
     * 队伍名称
     */
    private String teamName;
}
