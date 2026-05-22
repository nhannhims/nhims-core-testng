package com.nhims.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.nhims.constants.Configs;
import com.nhims.constants.FileConst;

public class HFile {
	private static final Map<String, Properties> configCache = new ConcurrentHashMap<>();

	public static String getConfig(Object configName) {
		return getConfig(FileConst.SETTING_CONFIG_FILE, configName);
	}

	public static String getConfig(String filePath, Object configName) {
		Properties prop = configCache.computeIfAbsent(filePath, path -> {
			Properties p = new Properties();
			try (FileInputStream fis = new FileInputStream(path)) {
				p.load(fis);
			} catch (IOException e) {
				throw new RuntimeException("Could not load configuration file at: " + path, e);
			}
			return p;
		});
		Object val = prop.getProperty(configName.toString());
		return val != null ? val.toString() : null;
	}

	/**
	 * Gets a required configuration value. Throws IllegalStateException if the key is not found.
	 *
	 * @param configName the configuration key (resolved via configs.properties)
	 * @return the configuration value (never null)
	 * @throws IllegalStateException if the configuration key is not found
	 */
	public static String getConfigRequired(Object configName) {
		String value = getConfig(configName);
		if (value == null) {
			throw new IllegalStateException(
					"Required configuration key [" + configName + "] not found in " + FileConst.SETTING_CONFIG_FILE);
		}
		return value;
	}

	public static String getConfigEnvironment(Object configName) {
		Object environment = getConfig("environment");
		String filePath = FileConst.ENVIRONMENT_DEFAULT_FILE;
		if (environment != null) {
			String envStr = environment.toString().trim();
			if (envStr.equalsIgnoreCase(Configs.Environment.Production.name())) {
				filePath = FileConst.ENVIRONMENT_PRODUCTION_FILE;
			} else if (envStr.equalsIgnoreCase(Configs.Environment.Staging.name())) {
				filePath = FileConst.ENVIRONMENT_DEFAULT_FILE;
			} else if (envStr.equalsIgnoreCase(Configs.Environment.Nightlight.name())) {
				filePath = FileConst.ENVIRONMENT_NIGHTLIGHT_FILE;
			}
		}
		return getConfig(filePath, configName.toString());
	}
}
