package com.lzy.example.design.patterns.stragegy.selector;

import com.lzy.example.design.patterns.stragegy.StatisticBaseHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 统计策略选择器
 * @author Lizuoyang
 * @date 2024/01/26
 */
@Component
public class StatisticStrategySelector {
    @Resource
    private Map<String, StatisticBaseHandler> selectorMap;

    /**
     * 根据类型选择对应的策略
     * @param type
     * @return {@link StatisticBaseHandler}
     */
    public StatisticBaseHandler select(String type) {
        return selectorMap.get(type);
    }

    public void doInvoke(String type) {
        StatisticBaseHandler handler = select(type);
        handler.doStatistic();
    }
}
