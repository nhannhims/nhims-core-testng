package com.nhims.main;

import com.nhims.browsers.Navigation;
import com.nhims.constants.Configs.DriverLoad;
import com.nhims.constants.Configs.DriverStatus;
import com.nhims.constants.Configs.EnvironmentConfig;
import com.nhims.drivers.DriverController;
import com.nhims.drivers.DriverExtensions;
import com.nhims.utils.HFile;
import com.nhims.utils.RecordVideo;

/**
 * Manual demo runner — NOT a TestNG test case.
 * Run this class's main() method directly to verify framework functionality
 * without TestNG involvement.
 */
public class DemoRunner {

	public static void main(String[] args) {
		RecordVideo.startRecord("TC001");
		DriverController.instance.startDriver();
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		DriverExtensions.createNewDriver(DriverLoad.Chrome);
		DriverExtensions.switchWebDriver(DriverStatus.New);
		Navigation.visitTo("https://wikipedia.vn");
		DriverExtensions.stopNewDriver();
		DriverExtensions.switchWebDriver(DriverStatus.Current);
		DriverController.instance.stopDriver();
		RecordVideo.stopRecord();
	}

}
