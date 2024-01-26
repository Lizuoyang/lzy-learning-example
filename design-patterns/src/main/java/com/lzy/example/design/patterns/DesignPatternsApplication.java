package com.lzy.example.design.patterns;

import com.lzy.example.design.patterns.stragegy.selector.StatisticStrategySelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot 启动类
 * @author Lizuoyang
 * @date 2024/01/26
 */
@SpringBootApplication
public class DesignPatternsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DesignPatternsApplication.class, args);
    }
}
