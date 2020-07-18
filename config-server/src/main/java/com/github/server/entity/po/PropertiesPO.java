package com.github.server.entity.po;

import lombok.Data;

import java.util.Date;

/**
 * @author hangs.zhang
 * @date 19-8-21 下午2:49
 * *********************
 * function:
 */
@Data
public class PropertiesPO {

    private Integer id;

    private String appName;

    private Integer propertiesId;

    private String propertiesName;

    private String content;

    private String propertiesDescription;

    /**
     * 0 已修改,未审核
     * 1 审核中
     * 2 发布状态
     */
    private Integer state;

    private Integer version;

    private String environment;

    private Date createTime;

    private Date updateTime;

}
