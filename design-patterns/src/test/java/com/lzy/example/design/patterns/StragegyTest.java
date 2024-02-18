package com.lzy.example.design.patterns;

import com.lzy.example.design.patterns.stragegy.selector.StatisticStrategySelector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StragegyTest {
    @Autowired
    private StatisticStrategySelector selector;
    @Test
    public void test() {
        selector.doInvoke("week");
        selector.doInvoke("month");
        selector.doInvoke("year");
    }
}
