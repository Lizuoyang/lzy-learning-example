package com.lzy.example.ip.location.query.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.lzy.example.ip.location.query.model.IpRegionData;
import com.lzy.example.ip.location.query.utils.EasyExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class IpRegionService implements CommandLineRunner {
    @Autowired
    private EasyExcelUtils excelUtils;
    @Override
    public void run(String... args) {
        long startTime = System.currentTimeMillis();
        System.out.println("ip归属地解析开始");
        String filePath = scanner("请输入要解析ip地址的文件路径，输入demo可使用默认文件");
        List<IpRegionData> list = excelUtils.read(filePath);
        excelUtils.write(list,filePath);
        System.out.println("耗时："+ DateUtil.formatBetween(System.currentTimeMillis()-startTime));
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append(tip);
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StrUtil.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new RuntimeException("请输入 " + tip + "!");
    }
}
