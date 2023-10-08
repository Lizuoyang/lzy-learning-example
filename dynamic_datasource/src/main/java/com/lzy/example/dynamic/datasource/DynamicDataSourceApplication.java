package com.lzy.example.dynamic.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * <p>
 * 动态数据源启动类
 * </p>
 *
 * @author lzy
 * @since 2023/9/14 15:54
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DynamicDataSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DynamicDataSourceApplication.class, args);
    }
}
