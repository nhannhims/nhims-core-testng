package com.nhims.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.nhims.constants.Configs;
import com.nhims.constants.FileConst;

public class HFile {
	public static String getConfig(Object configName) {
		return getConfig(FileConst.SETTING_CONFIG_FILE, configName);
	}

	public static String getConfig(String filePath, Object configName) {
		Properties prop = new Properties();
		try (FileInputStream fis = new FileInputStream(filePath)) {
			prop.load(fis);
			Object val = prop.getProperty(configName.toString());
			return val != null ? val.toString() : null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	public static String getConfigEnvironment(Object configName) {
		Object environment = getConfig("environment");
		String filePath = FileConst.ENVIRONMENT_DEFAUT_FILE;
		if (environment != null) {
			String envStr = environment.toString().trim();
			if (envStr.equalsIgnoreCase(Configs.Environment.Production.name())) {
				filePath = FileConst.ENVIRONMENT_PRODUCTION_FILE;
			} else if (envStr.equalsIgnoreCase(Configs.Environment.Staging.name())) {
				filePath = FileConst.ENVIRONMENT_DEFAUT_FILE;
			} else if (envStr.equalsIgnoreCase(Configs.Environment.Nightlight.name())) {
				filePath = FileConst.ENVIRONMENT_NIGHTLIGHT_FILE;
			}
		}
		return getConfig(filePath, configName.toString());
	}
}

