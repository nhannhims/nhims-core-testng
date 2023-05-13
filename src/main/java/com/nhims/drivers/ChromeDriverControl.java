package com.nhims.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverControl {
	public static WebDriver load() {
		ChromeOptions opts = new ChromeOptions();
		opts.addArguments("--start-maximized");
		opts.addArguments("--disable-extensions");
		opts.addArguments("--disable-popup-blocking");
		opts.addArguments("--remote-allow-origins=*");

		WebDriver driver = new ChromeDriver(opts);
		return driver;
	}
}
