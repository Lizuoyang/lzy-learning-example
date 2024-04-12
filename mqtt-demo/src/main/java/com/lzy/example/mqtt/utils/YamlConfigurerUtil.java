package com.lzy.example.mqtt.utils;

import java.util.Properties;

/**
 * 获取配置文件属性工具类
 * @author Lizuoyang
 * @date 2024/04/12
 */
public class YamlConfigurerUtil {
    private static Properties ymlProperties = new Properties();

    public YamlConfigurerUtil(Properties properties) {
        ymlProperties = properties;
    }

    public static String getStrYmlVal(String key) {
        return ymlProperties.getProperty(key);
    }

    public static Integer getIntegerYmlVal(String key) {
        return Integer.valueOf(ymlProperties.getProperty(key));
    }
}
