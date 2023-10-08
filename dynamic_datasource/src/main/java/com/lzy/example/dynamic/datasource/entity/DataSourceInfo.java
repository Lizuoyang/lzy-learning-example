package com.lzy.example.dynamic.datasource.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据源信息对象
 * </p>
 *
 * @author lzy
 * @since 2023/9/14 16:45
 */
@Data
@Accessors(chain = true)
public class DataSourceInfo {
    /**
     * 数据库地址
     */
    private String url;
    /**
     * 数据库用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据库驱动
     */
    private String driverClassName;
    /**
     * 数据库key，即保存Map中的key
     */
    private String key;
}
