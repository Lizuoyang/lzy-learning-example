package com.lzy.example.dynamic.datasource.service;

import cn.hutool.core.collection.CollectionUtil;
import com.lzy.example.dynamic.datasource.entity.DataSourceInfo;
import com.lzy.example.dynamic.datasource.entity.TestDbInfoDO;
import com.lzy.example.dynamic.datasource.mapper.TestDbInfoMapper;
import com.lzy.example.dynamic.datasource.utils.MyDynamicDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据源自动加载器
 * </p>
 *
 * @author lzy
 * @since 2023/9/14 17:07
 */
@Component
public class LoadDataSourceRunner implements CommandLineRunner {
    @Resource
    private MyDynamicDataSource dynamicDataSource;
    @Resource
    private TestDbInfoMapper testDbInfoMapper;

    @Override
    public void run(String... args) throws Exception {
        List<TestDbInfoDO> testDbInfoDOS = testDbInfoMapper.selectList(null);
        if (CollectionUtil.isNotEmpty(testDbInfoDOS)) {
            List<DataSourceInfo> dataSourceInfos = new ArrayList<>();
            for (TestDbInfoDO dbInfoDO : testDbInfoDOS) {
                DataSourceInfo info = new DataSourceInfo();
                BeanUtils.copyProperties(dbInfoDO, info);
                info.setKey(dbInfoDO.getName());
                dataSourceInfos.add(info);
            }
            dynamicDataSource.createDataSource(dataSourceInfos);
        }
    }
}
