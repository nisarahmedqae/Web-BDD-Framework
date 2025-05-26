package com.tmb.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import com.tmb.constants.FrameworkConstants;
import com.tmb.enums.ConfigProperties;
import com.tmb.exceptions.InvalidPathForPropertyFileException;
import com.tmb.exceptions.PropertyFileUsageException;

public final class PropertyUtils {

	private PropertyUtils() {

	}

	private static Properties prop = new Properties();
	private static final Map<String, String> CONFIGMAP = new HashMap<>();

	static {
		try (FileInputStream fis = new FileInputStream(FrameworkConstants.getConfigFilePath())) {
			prop.load(fis);

			for (Map.Entry<Object, Object> entry : prop.entrySet()) {
				CONFIGMAP.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()).trim());
			}

			// prop.entrySet().forEach(entry ->
			// CONFIGMAP.put(String.valueOf(entry.getKey()),
			// String.valueOf(entry.getValue())));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static String getValue(ConfigProperties key) {
		if (Objects.isNull(key) || Objects.isNull(CONFIGMAP.get(key.name().toLowerCase()))) {
			throw new PropertyFileUsageException(
					"Property name" + key + " is not found. Please check config.properties");
		}
		return CONFIGMAP.get(key.name().toLowerCase());
	}

}
