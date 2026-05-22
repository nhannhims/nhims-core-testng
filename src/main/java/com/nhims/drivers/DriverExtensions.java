package com.nhims.drivers;

import org.openqa.selenium.WebDriver;

import com.nhims.browsers.Browsers;
import com.nhims.constants.Configs.DriverStatus;
import com.nhims.utils.Logger;

public class DriverExtensions {
	private static final ThreadLocal<WebDriver> currentDriverThread = new ThreadLocal<>();
	private static final ThreadLocal<WebDriver> newDriverThread = new ThreadLocal<>();

	/**
	 * Creates a new WebDriver instance for a secondary browser session.
	 * Stores the secondary driver in a thread-local variable newDriverThread.
	 *
	 * @param browserType browser type identifier (e.g. "chrome", "firefox", "edge")
	 */
	public static void createNewDriver(Object browserType) {
		if (browserType != null) {
			WebDriver driver = BrowserFactory.create(browserType.toString());
			newDriverThread.set(driver);
			Logger.info("-----(Load)(" + browserType + ") new driver is created");
		}
	}

	/**
	 * Switches the active WebDriver instance between the main driver and secondary driver.
	 *
	 * @param driverName the driver status/type to switch to, from Configs.DriverStatus
	 */
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

	/**
	 * Quits the secondary WebDriver instance and cleans up thread-local variables.
	 */
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
