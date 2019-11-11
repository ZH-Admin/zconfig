package com.hang.client.service;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 * 1、一分钟一次，主动从server拉取数据 pull
 * 2、被server推送数据 pushed
 */
public interface HotConfigScheduleProcessor {

    /**
     * 主动   pull data
     */
    void pullData();

    /**
     * 被动   pushed data
     */
    void pushedData();

}
