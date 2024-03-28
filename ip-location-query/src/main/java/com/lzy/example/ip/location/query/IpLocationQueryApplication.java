package com.lzy.example.ip.location.query;

import com.lzy.example.ip.location.query.model.IpRegionData;
import com.lzy.example.ip.location.query.utils.EasyExcelUtils;
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

        List<IpRegionData> list = EasyExcelUtils.read();

        EasyExcelUtils.write(list);

    }
}
