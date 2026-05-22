package com.nhims.constants;

import java.nio.file.Paths;

public class FileConst {
	public static final String MAIN_PATH = System.getProperty("user.dir");
	public static final String LOG_FILE = Paths.get(MAIN_PATH, "test-reports", "logs", "%s.txt").toString();
	public static final String SCREENSHOT_FOLDER = Paths.get(MAIN_PATH, "test-reports", "screenshots").toString();
	public static final String VIDEO_FOLDER = Paths.get(MAIN_PATH, "test-reports", "videos").toString();
	public static final String SETTING_CONFIG_FILE = Paths.get(MAIN_PATH, "src", "test", "resources", "settings", "configs.properties").toString();
	public static final String ENVIRONMENT_DEFAULT_FILE = Paths.get(MAIN_PATH, "src", "test", "resources", "settings", "staging.properties").toString();
	public static final String ENVIRONMENT_PRODUCTION_FILE = Paths.get(MAIN_PATH, "src", "test", "resources", "settings", "production.properties").toString();
	public static final String ENVIRONMENT_NIGHTLIGHT_FILE = Paths.get(MAIN_PATH, "src", "test", "resources", "settings", "nightlight.properties").toString();
}
