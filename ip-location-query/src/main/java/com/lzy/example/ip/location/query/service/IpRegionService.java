package com.lzy.example.ip.location.query.service;

import cn.hutool.core.date.DateUtil;
import com.lzy.example.ip.location.query.model.IpRegionData;
import com.lzy.example.ip.location.query.utils.EasyExcelUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IpRegionService implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("IpLocationQueryApplication Start ");
        List<IpRegionData> list = EasyExcelUtils.read();
        EasyExcelUtils.write(list);
        System.out.println("耗时："+ DateUtil.formatBetween(System.currentTimeMillis()-startTime));
    }
}
