package com.lzy.example.mqtt.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * pk队伍名称枚举值
 * @author Lizuoyang
 * @date 2024/04/15
 */
@Getter
@AllArgsConstructor
public enum TeamNameEnum {
    RED("红队", "red"),
    BLUE("蓝队", "blue"),
    ;

    private String name;
    private String code;
}
