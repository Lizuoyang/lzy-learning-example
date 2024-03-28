package com.lzy.example.ip.location.query.utils;

import com.lzy.example.ip.location.query.model.IpRegionData;
import com.lzy.example.ip.location.query.strategy.selector.IpResolveStrategySelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 解析ip地址工具类
 */
@Slf4j
@Component
public class IPRegionUtils {
    @Autowired
    private IpResolveStrategySelector selector;

    public IpRegionData getAddress(Integer type, IpRegionData data) {
        return selector.doInvoke(type, data);
    }

}
