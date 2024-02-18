package com.lzy.example.ip.location.query;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.lzy.example.ip.location.query.model.IpRegionData;
import com.lzy.example.ip.location.query.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 主启动方法
 * @author Lizuoyang
 * @date 2024/02/18
 */
@Slf4j
public class IpLocationQueryApplication {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("IpLocationQueryApplication Start ");

        String fileName = "D:\\后台管理系统IP归属地3.xls";
        List<IpRegionData> list = EasyExcel.read(fileName).head(IpRegionData.class).sheet().doReadSync();
        for (IpRegionData data : list) {
            String address = IPUtils.getAddress(data.getUserIp());
            if (StrUtil.isNotBlank(address)) {
                data.setUserIpRegion(address);
            } else {
                data.setUserIpRegion("归属地为空");
            }
            log.info("读取到数据:{}", JSONObject.toJSONString(data));
            Thread.sleep(500);
        }
        // List<IpRegionData> ipRegionDataList = list.stream().filter(f -> ObjectUtil.equal(f.getUserIpRegion(), "归属地为空")).collect(Collectors.toList());
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write("D:\\后台管理系统IP归属地4.xls", IpRegionData.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();
            excelWriter.write(list, writeSheet);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

        /*String address = IPUtils.getAddress("60.255.166.116\n".replaceAll("\\n","").trim());
        System.out.println("=== (在线)访问者的地址为："+address+" === ");*/
    }
}
