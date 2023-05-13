package com.nhims.constants;

public class Configs {
	public static enum Environment {
		Production, Staging, Nightlight
	}

	public static enum DriverLoad {
		Chrome, Edge, Firefox, Android, IOS
	}

	public static enum DriverStatus {
		New, Current
	}
	
	public static enum ConfigFile {
		environment,
		driver,
		report,
		capture,
		video,
		logger
	}
	
	public static enum EnvironmentConfig {
		applicationUrl
	}
}
