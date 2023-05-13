package com.nhims.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.nhims.constants.Configs;
import com.nhims.constants.FileConst;

public class HFile {
	public static String getConfig(Object configName) {
		String val = null;
		FileInputStream fis = null;
		Properties prop = new Properties();
		try {
			fis = new FileInputStream(FileConst.SETTING_CONFIG_FILE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			prop.load(fis);
			val = prop.getProperty(configName.toString()).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}

	public static String getConfig(String filePath, Object configName) {
		String val = null;
		FileInputStream fis = null;
		Properties prop = new Properties();
		try {
			fis = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			prop.load(fis);
			val = prop.getProperty(configName.toString()).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}

	public static String getConfigEnvironment(Object configName) {
		Object environment = getConfig("environment");
		String filePath = FileConst.ENVIRONMENT_DEFAUT_FILE;
		if (environment.equals(Configs.Environment.Production)) {
			filePath = FileConst.ENVIRONMENT_PRODUCTION_FILE;
		}
		if (environment.equals(Configs.Environment.Staging)) {
			filePath = FileConst.ENVIRONMENT_DEFAUT_FILE;
		}
		if (environment.equals(Configs.Environment.Nightlight)) {
			filePath = FileConst.ENVIRONMENT_NIGHTLIGHT_FILE;
		}
		return getConfig(filePath, configName.toString());
	}
}
