package com.lzy.example.dynamic.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 测试数据库信息对象
 * </p>
 *
 * @author lzy
 * @since 2023/9/14 16:49
 */
@Data
@TableName("test_db_info")
public class TestDbInfoDO {
    @TableId(type = IdType.AUTO)
    private Integer id;

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
     * 数据库name，即保存Map中的key
     */
    private String name;
}
