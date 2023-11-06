package com.lzy.example.mybatis.plus.general;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName PropertiesUtil
 * @Description 读取properties文件工具类
 * @Author LiZuoYang
 * @Date 2021/12/2 11:24
 **/
public class PropertiesUtil {
    private static Properties props;
    static {
        loadProps();
    }

    synchronized static private void loadProps(){
        System.out.println("start loading properties file content.......");
        props = new Properties();
        InputStream in = null;
        try {
            // 第一种，通过类加载器进行获取properties文件流-->
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream("mybatis-plus-general.properties");
            // 第二种，通过类进行获取properties文件流-->
            //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            System.out.println("mybatis-plus-general.properties file not found");
        } catch (IOException e) {
            System.out.println("IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println("mybatis-plus-general.properties file close error");
            }
        }
        System.out.println("Load properties Success...........");
        System.out.println("properties file content: " + props);
    }

    /**
     * 根据key获取配置文件中的属性
     */
    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    /**
     * 根据key获取配置文件中的属性，当为null时返回指定的默认值
     */
    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
