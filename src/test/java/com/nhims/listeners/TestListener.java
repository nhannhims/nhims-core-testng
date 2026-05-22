package com.nhims.listeners;

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
		Logger.Info("### [START] Suite Context: " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		Logger.Info("### [END] Suite Context: " + context.getName());
	}

	@Override
	public void onTestStart(ITestResult result) {
		Logger.Info("*[TEST][START][" + getMethodName(result) + "] " + getTestDescription(result));
		if (HFile.getConfig(ConfigFile.driver).equals(DriverLoad.Chrome.toString())) {
			DriverController.instance.startChromeDriver();
			Logger.Info("### [START][CHROME] Load Driver");
		}
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
		try {
			DriverController.instance.stopDriver();
			Logger.Info("### [END] Stop Driver");
		} catch (Exception e) {
			Logger.Error("Can not handle quit driver: " + e.getMessage());
		}
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
		try {
			DriverController.instance.stopDriver();
			Logger.Info("### [END] Stop Driver");
		} catch (Exception e) {
			Logger.Error("Can not handle quit driver: " + e.getMessage());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Logger.Info("*[TEST][END][SKIPPED][" + getMethodName(result) + "] " + getTestDescription(result));
		try {
			DriverController.instance.stopDriver();
			Logger.Info("### [END] Stop Driver");
		} catch (Exception e) {
			Logger.Error("Can not handle quit driver: " + e.getMessage());
		}
	}
}

