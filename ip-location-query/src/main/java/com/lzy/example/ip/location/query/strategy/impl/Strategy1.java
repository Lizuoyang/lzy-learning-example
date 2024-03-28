package com.lzy.example.ip.location.query.strategy.impl;

import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.lzy.example.ip.location.query.model.IpRegionData;
import com.lzy.example.ip.location.query.strategy.IpResolveStrategyBaseHandler;
import com.lzy.example.ip.location.query.utils.HttpUtils;
import org.springframework.stereotype.Component;

/**
 * 解析ip归属地-策略1
 * @author Lizuoyang
 * @date 2024/03/28
 */
@Component("strategy1")
public class Strategy1 implements IpResolveStrategyBaseHandler {
    private static final String URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=";

    @Override
    public IpRegionData doResolve(IpRegionData data) {
        try {
            String httpRespStr = HttpUtils.doGet(URL + data.getUserIp() + "&json=true");
            JSONObject httpRespJson = null;
            try {
                httpRespJson = JSONObject.parseObject(httpRespStr);
            } catch (JSONException e) {
                data.setUserIpRegion("归属地为空");
                return data;
            }
            if (ObjUtil.isNotNull(httpRespJson)) {
                data.setUserIpRegion(httpRespJson.getString("addr"));
            } else {
                data.setUserIpRegion("归属地为空");
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
