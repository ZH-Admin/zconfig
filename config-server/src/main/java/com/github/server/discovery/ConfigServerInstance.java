package com.github.server.discovery;

import lombok.Data;

import java.util.Date;

/**
 * @author zhanghang
 * @date 2021/2/9 5:42 下午
 * *****************
 * function:
 */
@Data
public class ConfigServerInstance {

    private String ip;

    private String hostname;

    private Date lastRenewTime;

    private Long renewTimePeriod;

}
