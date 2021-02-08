package com.github.client.spring.load;

import com.github.client.utils.Constant;
import com.github.client.enums.ResultEnum;
import com.github.client.exception.ConfigBaseException;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hangs.zhang
 * @date 2019/11/11 16:22
 * *****************
 * function:
 */
@Component
public class FileSystemConfigLoader {

    @PostConstruct
    public void init() {
        File baseDir = new File(Constant.BASE_DIR);
        if (!baseDir.exists() && !baseDir.mkdir()) {
            // throw new ConfigBaseException(ResultEnum.CAN_NOT_CREATE_BASE_DIR);
        }
    }

    public void store2File(String dataId, String version, Map<String, String> config) throws IOException {
        Preconditions.checkArgument(ObjectUtils.allNotNull(dataId, version, config), "dataId or version or config is null");

        File configDir = new File(String.format(Constant.CONFIG_PATH, dataId));
        if (!configDir.exists() && !configDir.mkdir()) {
            throw new ConfigBaseException(ResultEnum.CAN_NOT_CREATE_CONFIG_DIR);
        }

        File configFile = new File(String.format(Constant.CONFIG_VERSION_ABSOLUTE_PATH, version));
        configFile.deleteOnExit();
        if (!configFile.createNewFile()) {
            throw new ConfigBaseException(ResultEnum.CAN_NOT_CREATE_CONFIG_FILE);
        }

        try (FileWriter fileWriter = new FileWriter(configFile)) {
            Properties properties = new Properties();
            properties.putAll(config);
            properties.store(fileWriter, null);
        }
    }

    public Map<String, String> loadFromFile(String configName, String version) throws IOException {
        Preconditions.checkArgument(ObjectUtils.allNotNull(configName, version), "configName or version or config is null");
        File file = new File(String.format(Constant.CONFIG_VERSION_ABSOLUTE_PATH, configName, version));
        Properties properties = new Properties();

        try (FileReader fileReader = new FileReader(file)) {
            properties.load(fileReader);
            Map<String, String> map = new ConcurrentHashMap<>(properties.size());
            properties.forEach((k, v) -> map.put((String) k, (String) v));
            return map;
        }
    }

}
