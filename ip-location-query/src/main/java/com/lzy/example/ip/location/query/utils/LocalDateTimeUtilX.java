package com.lzy.example.ip.location.query.utils;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * LocalDateTime 工具类
 * @author Lizuoyang
 * @date 2024/03/01
 */
public class LocalDateTimeUtilX extends LocalDateTimeUtil {
    public static Date parseDate(LocalDateTime localDateTime) {
        //获取系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        //时区的日期和时间
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        //获取时刻
        Date date = Date.from(zonedDateTime.toInstant());
        return date;
    }

    public static String formatYMDHSM(LocalDateTime dateTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String localTime = df.format(dateTime);
        return localTime;
    }
}
