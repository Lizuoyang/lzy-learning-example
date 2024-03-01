package com.lzy.example.ip.location.query.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class IpRegionData {
    @ExcelProperty(value = "账号")
    private String userName;
    @ExcelProperty(value = "IP")
    private String userIp;
    @ExcelProperty(value = "用户归属地")
    private String userIpRegion;
    @ExcelProperty(value = "时间")
    private String userDate;
}
