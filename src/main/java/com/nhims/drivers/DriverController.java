package com.nhims.drivers;

import org.openqa.selenium.WebDriver;

public class DriverController {
	private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
	public static final DriverController instance = new DriverController();
	
	public DriverController() {
		
	}
	
	public WebDriver getDriver() {
		return driverThread.get();
	}
	
	public void setDriver(WebDriver driver) {
		driverThread.set(driver);
	}
	
	public void startChromeDriver() {
		if(driverThread.get() == null) {
			driverThread.set(ChromeDriverControl.load());
		}
	}
	
	public void stopDriver() {
		WebDriver driver = driverThread.get();
		if(driver != null) {
			driver.quit();
			driverThread.remove();
		}
	}
}

