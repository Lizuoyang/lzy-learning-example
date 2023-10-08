package com.lzy.example.dynamic.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * TestUser DO
 * </p>
 *
 * @author lzy
 * @since 2023/9/14 15:56
 */
@Data
@TableName("test_user")
public class TestUserDO {
    private String userName;
}
