package com.github.server.dao;

import com.github.server.entity.po.PropertiesPO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
     */
    List<PropertiesPO> selectLastVersionProperties();

    /**
     * 根据appName获取每一个version最大的配置文件
     * @return
     */
    @MapKey("dataId")
    Map<String, PropertiesPO> selectLastVersionPropertiesByAppName(@Param("appName") String appName);

    Integer selectMaxAppVersion(@Param("appName") String appName);

}
