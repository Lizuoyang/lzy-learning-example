package com.lzy.example.dynamic.datasource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzy.example.dynamic.datasource.entity.TestUserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * TestUser Mapper
 * </p>
 *
 * @author lzy
 * @since 2023/9/14 15:57
 */
@Mapper
public interface TestUserMapper extends BaseMapper<TestUserDO> {
}
