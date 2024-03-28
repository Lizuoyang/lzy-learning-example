package com.lzy.example.ip.location.query.strategy;

import com.lzy.example.ip.location.query.model.IpRegionData;

/**
 * 统计抽象策略处理器
 * @author Lizuoyang
 * @date 2024/03/28
 */
public interface IpResolveStrategyBaseHandler {
    IpRegionData doResolve(IpRegionData data);
}
