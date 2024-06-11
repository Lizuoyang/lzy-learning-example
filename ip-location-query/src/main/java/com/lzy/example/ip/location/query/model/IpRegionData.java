package com.lzy.example.ip.location.query.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class IpRegionData {
    @ExcelProperty(value = "用户id")
    private String userId;
    @ExcelProperty(value = "用户名称")
    private String userName;
    @ExcelProperty(value = "用户ip")
    private String userIp;
    @ExcelProperty(value = "日期")
    private String createTime;
    @ExcelProperty(value = "用户归属地")
    private String userIpRegion;
}
