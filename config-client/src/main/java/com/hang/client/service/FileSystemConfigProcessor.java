package com.hang.client.service;

import com.google.common.base.Preconditions;
import com.hang.common.enums.ResultEnum;
import com.hang.common.exception.ConfigBaseException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hangs.zhang
 * @date 2019/11/11 16:22
 * *****************
 * function:
 */
@Service
public class FileSystemConfigProcessor {

    private final static String BASE_DIR = "~/.cache";

    private final static String CONFIG_PATH = BASE_DIR + "/%s";

    private final static String CONFIG_VERSION_ABSOLUTE_PATH = CONFIG_PATH + "/%s";

    private ConcurrentHashMap<String, AtomicInteger> versions = new ConcurrentHashMap<>(4);

    @PostConstruct
    public void init() {
        File baseDir = new File(BASE_DIR);
        if (!baseDir.exists()) {
            if (!baseDir.mkdir()) {
                throw new ConfigBaseException(ResultEnum.CAN_NOT_CREATE_BASE_DIR);
            }
        }
    }

    public void store2File(String configName, String version, Map<String, String> config) throws IOException {
        Preconditions.checkArgument(ObjectUtils.allNotNull(configName, version, config), "configName or version or config is null");

        File configDir = new File(String.format(CONFIG_PATH, configName));
        if (!configDir.exists()) {
            if (!configDir.mkdir()) {
                throw new ConfigBaseException(ResultEnum.CAN_NOT_CREATE_CONFIG_DIR);
            }
        }

        File configFile = new File(String.format(CONFIG_VERSION_ABSOLUTE_PATH, version));
        configFile.deleteOnExit();
        if (!configFile.createNewFile()) {
            throw new ConfigBaseException(ResultEnum.CAN_NOT_CREATE_CONFIG_FILE);
        }

        Properties properties = new Properties();
        properties.putAll(config);
        properties.store(new FileWriter(configFile), null);
    }

    public Map<String, String> loadFromFile(String configName, String version) throws IOException {
        Preconditions.checkArgument(ObjectUtils.allNotNull(configName, version), "configName or version or config is null");

        File file = new File(String.format(CONFIG_VERSION_ABSOLUTE_PATH, configName, version));
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        Map<String, String> map = new ConcurrentHashMap<>(properties.size());
        properties.forEach((k, v) -> map.put((String) k, (String) v));
        return map;
    }

}
