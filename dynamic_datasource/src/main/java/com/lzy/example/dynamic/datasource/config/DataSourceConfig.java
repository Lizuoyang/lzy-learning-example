package com.lzy.example.dynamic.datasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.lzy.example.dynamic.datasource.utils.MyDynamicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 数据源自动配置类
 * </p>
 *
 * @author lzy
 * @since 2023/9/14 15:25
 */
@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    public DataSource slaveDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "myDynamicDataSource")
    @Primary
    public MyDynamicDataSource myDynamicDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        DataSource defaultDataSource  = masterDataSource();
        dataSourceMap.put("master", defaultDataSource);
        dataSourceMap.put("slave", slaveDataSource());
        return new MyDynamicDataSource(defaultDataSource, dataSourceMap);
    }
}
