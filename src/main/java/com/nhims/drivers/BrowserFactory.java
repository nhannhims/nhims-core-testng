package com.nhims.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Factory class for creating WebDriver instances based on browser type.
 * Reads the browser type from the configs.properties file or system property.
 * Usage: BrowserFactory.create("chrome") / "firefox" / "edge"
 */
public class BrowserFactory {

	/**
	 * Creates a WebDriver instance for the given browser type.
	 *
	 * @param browserType browser name (case-insensitive): "chrome", "firefox", "edge"
	 * @return configured WebDriver instance
	 */
	public static WebDriver create(String browserType) {
		if (browserType == null) {
			throw new IllegalArgumentException("Browser type must not be null");
		}
		switch (browserType.trim().toLowerCase()) {
			case "chrome":
				return buildChrome();
			case "firefox":
				return buildFirefox();
			case "edge":
				return buildEdge();
			default:
				throw new IllegalArgumentException("Unsupported browser type: [" + browserType + "]. Supported: chrome, firefox, edge.");
		}
	}

	/**
	 * Configures ChromeOptions and builds a new ChromeDriver instance.
	 *
	 * @return configured ChromeDriver instance
	 */
	private static WebDriver buildChrome() {
		ChromeOptions opts = new ChromeOptions();
		opts.addArguments("--start-maximized");
		opts.addArguments("--disable-extensions");
		opts.addArguments("--disable-popup-blocking");
		opts.addArguments("--remote-allow-origins=*");
		return new ChromeDriver(opts);
	}

	/**
	 * Configures FirefoxOptions and builds a new FirefoxDriver instance.
	 *
	 * @return configured FirefoxDriver instance
	 */
	private static WebDriver buildFirefox() {
		FirefoxOptions opts = new FirefoxOptions();
		opts.addArguments("--start-maximized");
		return new FirefoxDriver(opts);
	}

	/**
	 * Configures EdgeOptions and builds a new EdgeDriver instance.
	 *
	 * @return configured EdgeDriver instance
	 */
	private static WebDriver buildEdge() {
		EdgeOptions opts = new EdgeOptions();
		opts.addArguments("--start-maximized");
		opts.addArguments("--disable-extensions");
		opts.addArguments("--disable-popup-blocking");
		opts.addArguments("--remote-allow-origins=*");
		return new EdgeDriver(opts);
	}
}
