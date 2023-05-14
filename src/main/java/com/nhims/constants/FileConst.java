package com.nhims.constants;

public class FileConst {
	public static String MAIN_PATH = System.getProperty("user.dir");
	public static String LOG_FILE = MAIN_PATH + "/test-reports/logs/%s.txt";
	public static String SCREENSHOT_FOLDER = MAIN_PATH + "//test-reports/screenshots";
	public static String VIDEO_FOLDER = MAIN_PATH + "/test-reports/videos";
	public static String SETTING_CONFIG_FILE = MAIN_PATH + "/src/test/resources/settings/configs.properties";
	public static String ENVIRONMENT_DEFAUT_FILE = MAIN_PATH + "/src/test/resources/settings/staging.properties";
	public static String ENVIRONMENT_PRODUCTION_FILE = MAIN_PATH + "/src/test/resources/settings/production.properties";
	public static String ENVIRONMENT_NIGHTLIGHT_FILE = MAIN_PATH + "/src/test/resources/settings/nightlight.properties";
}
