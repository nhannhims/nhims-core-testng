package com.nhims.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.nhims.browsers.Browsers;
import com.nhims.constants.Configs.ConfigFile;
import com.nhims.constants.Configs.DriverLoad;
import com.nhims.drivers.DriverController;
import com.nhims.utils.HFile;
import com.nhims.utils.Logger;
import com.nhims.utils.RecordVideo;

public class TestListener implements ITestListener {
	private String os = System.getProperty("os.name").toLowerCase();
	private static WebDriver driver = null;

	public String getTestName(ITestResult result) {
		return result.getTestName();
	}

	public String getTestDescription(ITestResult result) {
		return result.getMethod().getDescription();
	}

	public String getFileScriptName(ITestResult result) {
		return result.getMethod().getTestClass().getName();
	}

	public String getMethodName(ITestResult result) {
		return result.getMethod().getMethodName();
	}

	@Override
	public void onStart(ITestContext context) {
		if (HFile.getConfig(ConfigFile.driver).equals(DriverLoad.Chrome.toString())) {
			DriverController.instance.startChromeDriver();
			driver = DriverController.instance.driver;
			Logger.Info("### [START][CHROME] Load Driver");
			Logger.Info(" ");
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		try {
			driver.quit();
			Logger.Info("### [END][ALL] Stop Driver");
		} catch (Exception e) {
			// TODO: handle exception
			Logger.Error("Can not handle quit driver");
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		Logger.Info("*[TEST][START][" + getMethodName(result) + "] " + getTestDescription(result));
		if (HFile.getConfig(ConfigFile.video).equals("true") && !os.contains("mac os")) {
			RecordVideo.StartRecord(getMethodName(result));
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		if (HFile.getConfig(ConfigFile.capture).equals("true")) {
			Browsers.takeScreenshot(getMethodName(result));
		}
		if (HFile.getConfig(ConfigFile.video).equals("true") && !os.contains("mac os")) {
			RecordVideo.stopRecord();
		}
		Logger.Info("*[TEST][END][PASSED][" + getMethodName(result) + "] " + getTestDescription(result));
		Logger.Info(" ");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		if (HFile.getConfig(ConfigFile.capture).equals("true")) {
			Browsers.takeScreenshot(getMethodName(result));
		}
		if (HFile.getConfig(ConfigFile.video).equals("true") && !os.contains("mac os")) {
			RecordVideo.stopRecord();
		}
		Logger.Info("*[TEST][END][FAILED][" + getMethodName(result) + "] " + getTestDescription(result));
		Logger.Info(" ");
	}

	@Override
	public void onTestSkipped(ITestResult result) {

	}
}
