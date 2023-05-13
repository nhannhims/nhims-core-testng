package com.nhims.browsers;

import org.openqa.selenium.WebDriver;

import com.nhims.drivers.DriverController;

public class Browsers {
	private static WebDriver driver = null;

	public static WebDriver browser() {
		if(driver == null) {
			driver = DriverController.instance.driver;
		}
		return driver;
	}

	public static void waitBySec(int sec) {
		int time = sec * 1000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static WebDriver getDriver() {
		if (driver != null) {
			return driver;
		} else {
			return null;
		}
	}

	public static void setDriver(WebDriver newDriver) {
		driver = newDriver;
	}
}
