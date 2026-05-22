package com.nhims.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.nhims.browsers.Browsers;
import com.nhims.constants.Configs.ConfigFile;
import com.nhims.drivers.DriverController;
import com.nhims.utils.HFile;
import com.nhims.utils.Logger;
import com.nhims.utils.RecordVideo;

public class TestListener implements ITestListener {
	private final String os = System.getProperty("os.name").toLowerCase();

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
		Logger.info("### [START] Suite Context: " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		Logger.info("### [END] Suite Context: " + context.getName());
	}

	@Override
	public void onTestStart(ITestResult result) {
		Logger.info("*[TEST][START][" + getMethodName(result) + "] " + getTestDescription(result));
		// Start driver based on configs.properties (supports chrome, firefox, edge)
		DriverController.instance.startDriver();
		Logger.info("### [START][DRIVER] Loaded from config: " + HFile.getConfig(ConfigFile.driver));

		if ("true".equalsIgnoreCase(HFile.getConfig(ConfigFile.video)) && !os.contains("mac os")) {
			RecordVideo.startRecord(getMethodName(result));
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		captureAndStop(result, "PASSED");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		captureAndStop(result, "FAILED");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Logger.info("*[TEST][END][SKIPPED][" + getMethodName(result) + "] " + getTestDescription(result));
		stopDriverQuietly();
	}

	// ─── Private helpers ─────────────────────────────────────────────────────────

	private void captureAndStop(ITestResult result, String status) {
		if ("true".equalsIgnoreCase(HFile.getConfig(ConfigFile.capture))) {
			Browsers.takeScreenshot(getMethodName(result));
		}
		if ("true".equalsIgnoreCase(HFile.getConfig(ConfigFile.video)) && !os.contains("mac os")) {
			RecordVideo.stopRecord();
		}
		Logger.info("*[TEST][END][" + status + "][" + getMethodName(result) + "] " + getTestDescription(result));
		Logger.info(" ");
		stopDriverQuietly();
	}

	private void stopDriverQuietly() {
		try {
			DriverController.instance.stopDriver();
			Logger.info("### [END] Stop Driver");
		} catch (Exception e) {
			Logger.error("Can not handle quit driver: " + e.getMessage());
		}
	}
}
