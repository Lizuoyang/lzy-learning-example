package com.lzy.example.mqtt.domain.response;

import lombok.Data;

import java.util.List;

/**
 * pk详情响应类
 * @author Lizuoyang
 * @date 2024/04/16
 */
@Data
public class PkRecordResponse {
    private Integer blueTeamScore;
    private Integer redTeamScore;
    private List<UserInfoResponse> userList;
}
