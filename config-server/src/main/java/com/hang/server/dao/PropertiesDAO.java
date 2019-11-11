package com.hang.server.dao;

import com.hang.server.entity.po.PropertiesPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hangs.zhang
 * @date 19-8-21 下午3:03
 * *********************
 * function:
 */
@Repository
public interface PropertiesDAO {

    /**
     * 查询每一个appName对应的version最大的配置文件
     *
     * @return
     */
    List<PropertiesPO> selectLastVersionProperties();

    /**
     * 根据appName获取每一个version最大的配置文件
     *
     * @param appName
     * @return
     */
    List<PropertiesPO> selectLastVersionPropertiesByAppName(String appName);

}
