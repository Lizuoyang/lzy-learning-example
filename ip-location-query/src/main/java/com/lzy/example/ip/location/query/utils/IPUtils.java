package com.lzy.example.ip.location.query.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * IP地址
 */
@Slf4j
public class IPUtils {
    private static final String URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=";

    public static String getAddress(String ip) {
        try {
            URL realUrl = new URL(URL + ip + "&json=true");
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setReadTimeout(6000);
            conn.setConnectTimeout(6000);
            conn.setInstanceFollowRedirects(false);
            int code = conn.getResponseCode();
            StringBuilder sb = new StringBuilder();
            String ipaddr = "";
            if (code == 200) {
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                //if(window.IPCallBack)
                //{IPCallBack({"ip":"117.153.202.54","pro":"湖北省","proCode":"420000","city":"恩施州","cityCode":"422800","region":"","regionCode":"0",
                //"addr":"湖北省恩施州 移通","regionNames":"","err":""});}
                ipaddr = sb.substring(sb.indexOf("addr") + 7, sb.indexOf("regionNames") - 3);
            }
            return ipaddr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
