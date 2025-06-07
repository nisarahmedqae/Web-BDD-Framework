package com.nahmed.utils;

import com.nahmed.constants.FrameworkConstants;
import com.nahmed.enums.ConfigProperties;
import com.nahmed.exceptions.PropertyFileUsageException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public final class PropertyUtils {

    private PropertyUtils() {

    }

    private static Properties prop = new Properties();
    private static final Map<String, String> CONFIGMAP = new HashMap<>();

    static {
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.getConfigFilePath())) {
            prop.load(fis);

            for (Map.Entry<Object, Object> entry : prop.entrySet()) {
                CONFIGMAP.put(entry.getKey().toString().toLowerCase(), entry.getValue().toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static String getValue(ConfigProperties key) {
        if (Objects.isNull(key) || Objects.isNull(CONFIGMAP.get(key.toString().toLowerCase()))) {
            throw new PropertyFileUsageException(
                    "Property name " + key + " is not found. Please check config.properties");
        }
        return CONFIGMAP.get(key.toString().toLowerCase());
    }

    public static String getValue(String key) {
        if (Objects.isNull(key) || Objects.isNull(CONFIGMAP.get(key.toLowerCase()))) {
            throw new PropertyFileUsageException(
                    "Property name" + key + " is not found. Please check config.properties");
        }
        return CONFIGMAP.get(key.toLowerCase());
    }

}
