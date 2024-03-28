package com.lzy.example.ip.location.query.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.lzy.example.ip.location.query.model.IpRegionData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * EasyExcel 工具类
 * @author Lizuoyang
 * @date 2024/03/28
 */
@Slf4j
@Component
public class EasyExcelUtils {
    public static final String READ_FILENAME = "ip-location-query\\doc\\ip.xlsx";
    public static final String WRITE_FILENAME = "ip-location-query\\doc\\ipRegionResult.xlsx";

    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    public  List<IpRegionData> read(String filePath) {
        if ("demo".equals(filePath)) {
            filePath = READ_FILENAME;
        }
        File readFile = new File(filePath);
        if (!readFile.exists()) {
            throw new RuntimeException("文件不存在！");
        }
        // 读取excel获取所有条数
        List<IpRegionData> alldatList = EasyExcel.read(readFile).head(IpRegionData.class).sheet().doReadSync();
        List<IpRegionData> convertDataList = convertIpRegion(alldatList);
        // 筛选归属地为空
        recursion(convertDataList);
        return convertDataList;
    }

    public List<IpRegionData> recursion(List<IpRegionData> alldatList) {
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

    public List<IpRegionData> convertIpRegion(List<IpRegionData> params) {
        List<Future<IpRegionData>> futuresList = params.stream().limit(100).map(data -> {
            Future<IpRegionData> future = taskExecutor.submit(() -> IPUtils.getAddress(data));
            return  future;
        }).collect(Collectors.toList());

        List<IpRegionData> list = futuresList.stream().map(future -> {
            IpRegionData result = new IpRegionData();
            try {
                result =  future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return result;
        }).collect(Collectors.toList());
        return list;
    }

    public void write(List<IpRegionData> list, String filePath) {
        if ("demo".equals(filePath)) {
            filePath = WRITE_FILENAME;
        } else {
            File readFile = new File(filePath);
            List<String> fileNames = StrUtil.split(readFile.getName(), ".");
            filePath = filePath.replace(readFile.getName(),  fileNames.get(0) + LocalDateTimeUtilX.formatYMDHSM(LocalDateTime.now()) + "." + fileNames.get(1));
        }
        ExcelWriter excelWriter = null;
        File writeFile = new File(filePath);
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
        System.out.println("ip归属地解析完成，文件生成在：" + filePath);

    }
}
