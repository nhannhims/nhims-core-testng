package com.nhims.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * @deprecated Use {@link BrowserFactory#create(String)} instead.
 * This class is kept only for backward compatibility with DriverExtensions.
 */
@Deprecated
public class ChromeDriverControl {
	/**
	 * Creates a new ChromeDriver instance with standard options.
	 *
	 * @return a new WebDriver ChromeDriver instance
	 * @deprecated Use BrowserFactory.create("chrome") instead.
	 */
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
