package com.lzy.example.ip.location.query.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.lzy.example.ip.location.query.model.IpRegionData;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
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
        // 读取excel获取所有条数
        List<IpRegionData> alldatList = EasyExcel.read(READ_FILENAME).head(IpRegionData.class).sheet().doReadSync();
        List<IpRegionData> convertDataList = convertIpRegion(alldatList);
        // 筛选归属地为空
        recursion(convertDataList);
        return convertDataList;
    }

    public static List<IpRegionData> recursion(List<IpRegionData> alldatList) {
        List<IpRegionData> ipRegionIsNullList = alldatList.stream().filter(f -> ObjectUtil.equal(f.getUserIpRegion(), "归属地为空")).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(ipRegionIsNullList)) {
            // 转换成HashSet后删除性能更好
            HashSet<IpRegionData> ipRegionIsNullSet = new HashSet<>(ipRegionIsNullList);
            boolean removeFlag = alldatList.removeAll(ipRegionIsNullSet);
            if (removeFlag) {
                // 使用CollectionUtil.addAll()性能更好
                CollectionUtil.addAll(alldatList, convertIpRegion(ipRegionIsNullList));
                recursion(alldatList);
            }
        }
        return alldatList;
    }

    public static List<IpRegionData> convertIpRegion(List<IpRegionData> params) {
        List<IpRegionData> list = params.stream().limit(100).peek(data -> {
            String address = IPUtils.getAddress(data.getUserIp());
            if (StrUtil.isNotBlank(address)) {
                data.setUserIpRegion(address);
            } else {
                data.setUserIpRegion("归属地为空");
            }
            System.out.println("读取到数据:" + JSONObject.toJSONString(data));
            log.info("读取到数据:" + JSONObject.toJSONString(data));
        }).collect(Collectors.toList());
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
