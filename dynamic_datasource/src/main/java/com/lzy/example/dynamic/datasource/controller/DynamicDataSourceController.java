package com.lzy.example.dynamic.datasource.controller;

import com.lzy.example.dynamic.datasource.aspect.MyDs;
import com.lzy.example.dynamic.datasource.entity.TestUserDO;
import com.lzy.example.dynamic.datasource.mapper.TestUserMapper;
import com.lzy.example.dynamic.datasource.utils.DataSourceContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 动态数据源前端控制器
 * </p>
 *
 * @author lzy
 * @since 2023/9/14 16:16
 */
@RestController
@RequestMapping("/ds")
public class DynamicDataSourceController {
    @Resource
    private TestUserMapper testUserMapper;

    @GetMapping("/getData/{dataSourceName}")
    public String getData(@PathVariable("dataSourceName") String dataSourceName) {
        DataSourceContextHolder.setDataSource(dataSourceName);
        TestUserDO testUserDO = testUserMapper.selectOne(null);
        DataSourceContextHolder.removeDataSource();
        return testUserDO.getUserName();
    }

    @GetMapping("/getMasterData")
    public String getMasterData(){
        TestUserDO testUser = testUserMapper.selectOne(null);
        return testUser.getUserName();
    }

    @GetMapping("/getSlaveData")
    @MyDs("slave")
    public String getSlaveData(){
        TestUserDO testUser = testUserMapper.selectOne(null);
        return testUser.getUserName();
    }
}
