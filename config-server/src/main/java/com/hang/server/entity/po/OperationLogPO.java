package com.hang.server.entity.po;

import lombok.Data;

import java.util.Date;

/**
 * @author hangs.zhang
 * @date 19-8-21 下午2:53
 * *********************
 * function:
 */
@Data
public class OperationLogPO {

    private Integer id;

    private String appName;

    private Integer propertiesId;

    private String operationType;

    private Integer operationVersion;

    private String operationUserId;

    private Date createTime;

}
