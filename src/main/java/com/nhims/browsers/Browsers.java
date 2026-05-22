package com.nhims.browsers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nhims.constants.FileConst;
import com.nhims.drivers.DriverController;
import com.nhims.utils.HDate;
import com.nhims.utils.HFolder;
import com.nhims.utils.Logger;

public class Browsers {
	private static String screenshotFolder = HDate.formatDate("yyyy_MM_dd_hh_mm_ss");

	public static WebDriver browser() {
		return DriverController.instance.getDriver();
	}

	/**
	 * Pauses the current thread for the given number of seconds.
	 * Use only for page-load polling — prefer WebDriverWait for element waits.
	 */
	public static void waitBySec(int sec) {
		try {
			Thread.sleep((long) sec * 1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			Logger.warning("waitBySec interrupted: " + e.getMessage());
		}
	}

	/**
	 * Pauses the current thread for the given number of milliseconds.
	 * Use only for page-load polling — prefer WebDriverWait for element waits.
	 */
	public static void waitByMiliSec(int miliSec) {
		try {
			Thread.sleep(miliSec);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			Logger.warning("waitByMiliSec interrupted: " + e.getMessage());
		}
	}

	public static WebDriver getDriver() {
		return browser();
	}

	public static void setDriver(WebDriver newDriver) {
		DriverController.instance.setDriver(newDriver);
	}

	public static WebDriverWait waitExplicit(int sec) {
		WebDriverWait wait = new WebDriverWait(browser(), Duration.ofSeconds(sec));
		return wait;
	}

	public static void takeScreenshot(String scenario) {
		HFolder.createMoreFolder("test-reports", "screenshots", screenshotFolder);
		String path = Paths.get(FileConst.SCREENSHOT_FOLDER, screenshotFolder, scenario + ".png").toString();
		TakesScreenshot scrShot = ((TakesScreenshot) browser());
		File source = scrShot.getScreenshotAs(OutputType.FILE);
		try {
			Files.copy(source.toPath(), Paths.get(path));
		} catch (IOException e) {
			Logger.error("Cannot take screenshot at: " + path + " — " + e.getMessage());
		}
	}
}
