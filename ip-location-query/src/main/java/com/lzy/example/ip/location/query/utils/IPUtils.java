package com.lzy.example.ip.location.query.utils;

import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.lzy.example.ip.location.query.model.IpRegionData;
import lombok.extern.slf4j.Slf4j;

/**
 * 解析ip地址工具类
 */
@Slf4j
public class IPUtils {
    private static final String URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=";

    public static IpRegionData getAddress(IpRegionData data) {
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
