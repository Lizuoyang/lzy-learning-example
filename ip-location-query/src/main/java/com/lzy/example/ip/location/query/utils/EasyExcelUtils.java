package com.lzy.example.ip.location.query.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.lzy.example.ip.location.query.model.IpRegionData;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EasyExcel 工具类
 * @author Lizuoyang
 * @date 2024/03/28
 */
@Slf4j
public class EasyExcelUtils {
    public static final String READ_FILENAME = "ip-location-query\\doc\\ip.xlsx";
    public static final String WRITE_FILENAME = "ip-location-query\\doc\\ip_mapper.xlsx";

    public static List<IpRegionData> read() {
        List<IpRegionData> list = EasyExcel.read(READ_FILENAME).head(IpRegionData.class).sheet().doReadSync();
        for (IpRegionData data : list.stream().limit(5).collect(Collectors.toList())) {
            String address = IPUtils.getAddress(data.getUserIp());
            if (StrUtil.isNotBlank(address)) {
                data.setUserIpRegion(address);
            } else {
                data.setUserIpRegion("归属地为空");
            }
            System.out.println("读取到数据:" +  JSONObject.toJSONString(data));
            log.info("读取到数据:" +  JSONObject.toJSONString(data));
        }
        return list;
    }

    public static void write(List<IpRegionData> list) {
        ExcelWriter excelWriter = null;
        File writeFile = new File(WRITE_FILENAME);
        if(!writeFile.exists())
        {
            try {
                writeFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            excelWriter = EasyExcel.write(writeFile, IpRegionData.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.write(list, writeSheet);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

}
