package com.github.server.service;

import com.github.client.enums.ResultEnum;
import com.github.server.dao.PropertiesDAO;
import com.github.server.entity.po.PropertiesPO;
import com.github.server.exceptions.ConfigServerException;
import com.github.client.utils.KeyUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
public class ConfigService {

    @Autowired
    private PropertiesDAO propertiesDAO;

    /**
     * key为appName+dataId
     */
    private static final Map<String, PropertiesPO> APP_DATA_CONFIG = Maps.newConcurrentMap();

    private static final Map<String, List<PropertiesPO>> APP_CONFIG = Maps.newConcurrentMap();

    /**
     * key: appName
     * valye: max(appVersion)
     */
    private static final Map<String, Integer> APP_MAX_VERSION_CONFIG = Maps.newConcurrentMap();

    @PostConstruct
    public void init() {
        loadConfig();
    }

    public void loadConfig() {
        List<PropertiesPO> propertiesPOS = propertiesDAO.selectLastVersionProperties();
        if (!Objects.isNull(propertiesPOS)) {
            propertiesPOS.forEach(propertiesPO -> {
                String appName = propertiesPO.getAppName();
                APP_DATA_CONFIG.put(KeyUtils.getKey(appName, propertiesPO.getDataId()), propertiesPO);

                List<PropertiesPO> propertiesPOList = APP_CONFIG.get(appName);
                if (CollectionUtils.isEmpty(propertiesPOList)) {
                    propertiesPOList = new ArrayList<>();
                    APP_CONFIG.put(appName, propertiesPOList);
                }
                propertiesPOList.add(propertiesPO);

                if (!APP_MAX_VERSION_CONFIG.containsKey(appName)) {
                    APP_MAX_VERSION_CONFIG.put(appName, propertiesDAO.selectMaxAppVersion(appName));
                }
            });
        }
    }

    public PropertiesPO getConfig(String appName, String dataId) {
        if (StringUtils.isBlank(dataId) || StringUtils.isBlank(appName)) {
            throw new ConfigServerException(ResultEnum.PARAM_ERROR);
        }
        return APP_DATA_CONFIG.get(KeyUtils.getKey(appName, dataId));
    }

    public List<PropertiesPO> getConfig(String appName) {
        if (StringUtils.isBlank(appName)) {
            throw new ConfigServerException(ResultEnum.PARAM_ERROR);
        }
        return APP_CONFIG.get(appName);
    }

    public Integer getMaxAppVersion(String appName) {
        if (StringUtils.isBlank(appName)) {
            throw new ConfigServerException(ResultEnum.PARAM_ERROR);
        }
        return APP_MAX_VERSION_CONFIG.get(appName);
    }

}
