package com.lzy.example.ip.location.query.strategy.impl;

import com.lzy.example.ip.location.query.model.IpRegionData;
import com.lzy.example.ip.location.query.strategy.IpResolveStrategyBaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 解析ip归属地-策略2
 * @author Lizuoyang
 * @date 2024/03/28
 */
@Slf4j
@Component("strategy2")
public class Strategy2 implements IpResolveStrategyBaseHandler {

    @Override
    public IpRegionData doResolve(IpRegionData data) {
        Searcher searcher = null;
        String ip = data.getUserIp();
        try {
            File file = ResourceUtils.getFile("classpath:ipdb/ip2region.xdb");
            String dbPath = file.getPath();
            searcher = Searcher.newWithFileOnly(dbPath);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            data.setUserIpRegion("归属地为空");
        } catch (IOException e) {
            log.error(e.getMessage());
            data.setUserIpRegion("归属地为空");
        }

        String region = null;
        try {
            region = searcher.search(ip);
            data.setUserIpRegion(region);
        } catch (Exception e) {
            log.error(e.getMessage());
            data.setUserIpRegion("归属地为空");
        }
        return data;
    }

}
