package com.lzy.example.mqtt.domain.response;

import lombok.Data;

/**
 * 用户详情
 * @author Lizuoyang
 * @date 2024/04/12
 */
@Data
public class UserInfoResponse {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 赠送礼物积分
     */
    private Integer pointNum;
}
