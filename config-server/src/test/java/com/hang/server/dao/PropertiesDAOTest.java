package com.hang.server.dao;

import com.hang.server.entity.po.PropertiesPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.lang.invoke.MethodHandles;
import java.util.List;

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
    }
}