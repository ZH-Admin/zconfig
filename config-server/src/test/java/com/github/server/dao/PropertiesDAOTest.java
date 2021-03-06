package com.github.server.dao;

import com.github.server.entity.po.PropertiesPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

/**
 * @author hangs.zhang
 * @date 19-8-21 下午4:27
 * *********************
 * function:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesDAOTest {

    @Resource
    private PropertiesDAO propertiesDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void selectLastVersionProperties() {
        List<PropertiesPO> propertiesPOS = propertiesDAO.selectLastVersionProperties();
        propertiesPOS.forEach(e -> LOGGER.info("data:{}", e));
    }

    @Test
    public void selectLastVersionPropertiesByAppName() {
        Map<String, PropertiesPO> map = propertiesDAO.selectLastVersionPropertiesByAppName("client-demo");
        map.forEach((k, v) -> {
            LOGGER.info("key:{}, value:{}", k, v);
        });
    }

    @Test
    public void selectMaxAppVersion() {
        Integer appVersion = propertiesDAO.selectMaxAppVersion("client-demo");
        LOGGER.info("version:{}", appVersion);
    }

}