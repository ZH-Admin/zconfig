package com.github.server.service.impl;

import com.github.client.enums.ResultEnum;
import com.github.server.dao.PropertiesDAO;
import com.github.server.entity.po.PropertiesPO;
import com.github.server.exceptions.ConfigServerException;
import com.github.server.exceptions.utils.Verify;
import com.github.server.service.ConfigManageService;
import com.github.server.utils.KeyUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author hangs.zhang
 * @date 2019/4/6
 * *****************
 * function:
 */
@Service
public class ConfigManageServiceImpl implements ConfigManageService {

    @Autowired
    private PropertiesDAO propertiesDAO;

    /**
     * key为app-code + HotConfig-value
     */
    private final Map<String, PropertiesPO> config = Maps.newConcurrentMap();

    @PostConstruct
    public void init() {
        initConfig();
    }

    @Override
    public void initConfig() {
        List<PropertiesPO> propertiesPOS = propertiesDAO.selectLastVersionProperties();
        if (!Objects.isNull(propertiesPOS)) {
            propertiesPOS.forEach(propertiesPO -> {
                config.put(KeyUtils.getRealKey(propertiesPO.getAppName(), propertiesPO.getPropertiesName()), propertiesPO);
            });
        }
    }

    @Override
    public PropertiesPO getConfig(String appName, String hConfigKey) {
        if (StringUtils.isBlank(hConfigKey) || StringUtils.isBlank(appName)) {
            throw new ConfigServerException(ResultEnum.PARAM_ERROR);
        }
        return config.get(KeyUtils.getRealKey(appName, hConfigKey));
    }

}
