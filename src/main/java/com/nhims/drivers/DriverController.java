package com.nhims.drivers;

import org.openqa.selenium.WebDriver;

public class DriverController {
	public WebDriver driver = null;
	public static DriverController instance = new DriverController();
	
	public DriverController() {
		
	}
	
	public void startChromeDriver() {
		if(driver == null) {
			driver = ChromeDriverControl.load();
		}
	}
	
	public void stopDriver() {
		if(driver != null) {
			driver.quit();
		}
	}
}
