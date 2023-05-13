package com.nhims.browsers;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nhims.drivers.DriverController;

public class Browsers {
	private static WebDriver driver = null;

	public static WebDriver browser() {
		if (driver == null) {
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

	public static void waitByMiliSec(int miliSec) {
		try {
			Thread.sleep(miliSec);
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
	
	public static WebDriverWait waitExplicit(int sec) {
		WebDriverWait wait = new WebDriverWait(browser(), Duration.ofSeconds(sec));
		return wait;
	}
}
