package com.lzy.example.mybatis.plus.general.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 基础类
 * @author zuoyang.li
 * @date 2024/08/26
 */
@Getter
@Setter
public class BasicModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 逻辑删除
     */
    protected Boolean isDelete;
    /**
     * 创建人编码
     */
    protected String createBy;
    /**
     * 创建人名称
     */
    protected String createName;
    /**
     * 创建时间
     */
    protected LocalDateTime createTime;
    /**
     * 修改人编码
     */
    protected String updateBy;
    /**
     * 修改人名称
     */
    protected String updateName;
    /**
     * 修改时间
     */
    protected LocalDateTime updateTime;


}
