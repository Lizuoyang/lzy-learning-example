package com.lzy.example.design.patterns.stragegy.impl;

import com.lzy.example.design.patterns.stragegy.StatisticBaseHandler;
import org.springframework.stereotype.Component;

/**
 * 年统计策略
 * @author Lizuoyang
 * @date 2024/01/26
 */
@Component("year")
public class StatisticByYearHandler implements StatisticBaseHandler {
    @Override
    public void doStatistic() {
        System.out.println("StatisticByYearHandler");
    }
}
