package com.nhims.drivers;

import org.openqa.selenium.WebDriver;

import com.nhims.utils.HFile;
import com.nhims.constants.Configs.ConfigFile;

/**
 * Thread-safe Singleton controller for managing WebDriver per thread.
 * Uses ThreadLocal to ensure each thread (parallel test) has its own driver instance.
 */
public class DriverController {
	private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
	public static final DriverController instance = new DriverController();

	private DriverController() {
	}

	/**
	 * Retrieves the thread-local WebDriver instance.
	 *
	 * @return the WebDriver instance for the current thread
	 */
	public WebDriver getDriver() {
		return driverThread.get();
	}

	/**
	 * Sets the thread-local WebDriver instance.
	 *
	 * @param driver the WebDriver instance to set
	 */
	public void setDriver(WebDriver driver) {
		driverThread.set(driver);
	}

	/**
	 * Starts a driver based on the 'driver' value in configs.properties.
	 * Supports: chrome, firefox, edge (case-insensitive).
	 */
	public void startDriver() {
		if (driverThread.get() == null) {
			String browserType = HFile.getConfig(ConfigFile.driver);
			driverThread.set(BrowserFactory.create(browserType));
		}
	}

	/**
	 * Starts a ChromeDriver instance for the current thread.
	 *
	 * @deprecated Use {@link #startDriver()} instead. Kept for backwards compatibility.
	 */
	@Deprecated
	public void startChromeDriver() {
		if (driverThread.get() == null) {
			driverThread.set(BrowserFactory.create("chrome"));
		}
	}

	/**
	 * Quits the active WebDriver instance for the current thread and removes it from ThreadLocal.
	 */
	public void stopDriver() {
		WebDriver driver = driverThread.get();
		if (driver != null) {
			driver.quit();
			driverThread.remove();
		}
	}
}
