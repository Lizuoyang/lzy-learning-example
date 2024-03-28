package com.lzy.example.ip.location.query.utils;

import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 解析ip地址工具类
 */
@Slf4j
public class IPUtils {
    private static final String URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=";

    public static String getAddress(String ip) {
        try {
            String ipaddr = "";
            String httpRespStr = HttpUtils.doGet(URL + ip + "&json=true");
            JSONObject httpRespJson = null;
            try {
                httpRespJson = JSONObject.parseObject(httpRespStr);
            } catch (JSONException e) {
                return ipaddr;
            }
            if (ObjUtil.isNotNull(httpRespJson)) {
                ipaddr = httpRespJson.getString("addr");
            }
            return ipaddr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
