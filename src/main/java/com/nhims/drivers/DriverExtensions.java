package com.nhims.drivers;

import org.openqa.selenium.WebDriver;

import com.nhims.browsers.Browsers;
import com.nhims.constants.Configs.DriverLoad;
import com.nhims.constants.Configs.DriverStatus;
import com.nhims.utils.Logger;

public class DriverExtensions extends Browsers {
	private static WebDriver currentDriver = browser();
	private static WebDriver newDriver = null;

	public static void createNewDriver(Object browserType) {
		if (browserType.equals(DriverLoad.Chrome)) {
			newDriver = ChromeDriverControl.load();
			Logger.info("-----(Load)(" + browserType + ") new driver is created");
		}
	}

	public static void switchWebDriver(Object driverName) {
		if (driverName.equals(DriverStatus.New)) {
			setDriver(newDriver);
			Logger.info("-----(Switch) > [New Driver]");
		} else {
			setDriver(currentDriver);
			Logger.info("-----(Switch) > [Old Driver]");
		}
	}

	public static void stopNewDriver() {
		if (newDriver != null) {
			newDriver.quit();
			Logger.info("-----(Stop New Driver)");
		}
	}
}
