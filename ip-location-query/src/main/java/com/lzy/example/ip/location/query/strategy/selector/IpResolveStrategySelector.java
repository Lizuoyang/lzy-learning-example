package com.lzy.example.ip.location.query.strategy.selector;

import com.lzy.example.ip.location.query.model.IpRegionData;
import com.lzy.example.ip.location.query.strategy.IpResolveStrategyBaseHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 策略选择器
 * @author Lizuoyang
 * @date 2024/03/28
 */
@Component
public class IpResolveStrategySelector {
    @Resource
    private Map<String, IpResolveStrategyBaseHandler> selectorMap;

    /**
     * 根据类型选择对应的策略
     * @param index
     * @return {@link IpResolveStrategyBaseHandler}
     */
    public IpResolveStrategyBaseHandler select(String index) {
        return selectorMap.get(index);
    }

    public IpRegionData doInvoke(Integer index, IpRegionData data) {
        IpResolveStrategyBaseHandler handler = select("strategy" + index);
        return handler.doResolve(data);
    }
}
