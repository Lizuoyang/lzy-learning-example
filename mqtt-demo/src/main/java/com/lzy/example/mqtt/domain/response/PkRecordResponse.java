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
    /**
     * 蓝队总贡献
     */
    private Integer blueTeamScore;
    /**
     * 红队总贡献
     */
    private Integer redTeamScore;
    /**
     * 蓝队贡献排行榜
     */
    private List<UserInfoResponse> blueUserList;
    /**
     * 红队贡献排行榜
     */
    private List<UserInfoResponse> redUserList;
}
