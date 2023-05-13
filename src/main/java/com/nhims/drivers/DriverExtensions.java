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
			Logger.Info("-----(Load)(" + browserType + ") new driver is created");
		}
	}

	public static void switchWebDriver(Object driverName) {
		if (driverName.equals(DriverStatus.New)) {
			setDriver(newDriver);
			Logger.Info("-----(Switch) > [New Driver]");
		} else {
			setDriver(currentDriver);
			Logger.Info("-----(Switch) > [Old Driver]");
		}
	}

	public static void stopNewDriver() {
		if (newDriver != null) {
			newDriver.quit();
			Logger.Info("-----(Stop New Driver)");
		}
	}
}
