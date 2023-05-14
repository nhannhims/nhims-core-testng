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
	private static WebDriver driver = null;
	private static String screenshotFolder = HDate.formatDate("yyyy_MM_dd_hh_mm_ss");

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

	public static void takeScreenshot(String scenario) {
		HFolder.createMoreFolder("test-reports", "screenshots", screenshotFolder);
		String path = FileConst.SCREENSHOT_FOLDER + "//" + screenshotFolder + "//" + scenario + ".png";
		TakesScreenshot scrShot = ((TakesScreenshot) browser());
		File source = scrShot.getScreenshotAs(OutputType.FILE);
		try {
			Files.copy(source.toPath(), Paths.get(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.Error("Can not screenshot");
			e.printStackTrace();
		}
	}
}
