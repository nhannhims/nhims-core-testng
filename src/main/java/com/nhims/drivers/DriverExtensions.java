package com.nhims.drivers;

import org.openqa.selenium.WebDriver;

import com.nhims.browsers.Browsers;
import com.nhims.constants.Configs.DriverLoad;
import com.nhims.constants.Configs.DriverStatus;
import com.nhims.utils.Logger;

public class DriverExtensions {
	private static final ThreadLocal<WebDriver> currentDriverThread = new ThreadLocal<>();
	private static final ThreadLocal<WebDriver> newDriverThread = new ThreadLocal<>();

	public static void createNewDriver(Object browserType) {
		if (browserType.equals(DriverLoad.Chrome)) {
			WebDriver driver = ChromeDriverControl.load();
			newDriverThread.set(driver);
			Logger.info("-----(Load)(" + browserType + ") new driver is created");
		}
	}

	public static void switchWebDriver(Object driverName) {
		if (driverName.equals(DriverStatus.New)) {
			currentDriverThread.set(Browsers.browser());
			Browsers.setDriver(newDriverThread.get());
			Logger.info("-----(Switch) > [New Driver]");
		} else {
			Browsers.setDriver(currentDriverThread.get());
			Logger.info("-----(Switch) > [Old Driver]");
		}
	}

	public static void stopNewDriver() {
		WebDriver newDriver = newDriverThread.get();
		if (newDriver != null) {
			newDriver.quit();
			newDriverThread.remove();
			Logger.info("-----(Stop New Driver)");
		}
		currentDriverThread.remove();
	}
}
