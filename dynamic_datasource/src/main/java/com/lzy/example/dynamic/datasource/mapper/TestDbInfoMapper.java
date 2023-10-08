package com.lzy.example.dynamic.datasource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzy.example.dynamic.datasource.entity.TestDbInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * TestDbInfo Mapper
 * </p>
 *
 * @author lzy
 * @since 2023/9/14 16:52
 */
@Mapper
public interface TestDbInfoMapper extends BaseMapper<TestDbInfoDO> {
}
