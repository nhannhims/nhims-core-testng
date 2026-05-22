package com.nhims.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.nhims.browsers.Browsers;
import com.nhims.constants.Configs.ConfigFile;
import com.nhims.drivers.DriverController;
import com.nhims.utils.HDate;
import com.nhims.utils.HFile;
import com.nhims.utils.Logger;
import com.nhims.utils.RecordVideo;

import io.qameta.allure.Allure;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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
		Browsers.setScreenshotFolder(HDate.formatDate("yyyy_MM_dd_hh_mm_ss"));
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
		String methodName = getMethodName(result);
		if ("true".equalsIgnoreCase(HFile.getConfig(ConfigFile.capture))) {
			Browsers.takeScreenshot(methodName);
			if ("FAILED".equals(status)) {
				attachScreenshotToAllure(methodName);
			}
		}
		if ("true".equalsIgnoreCase(HFile.getConfig(ConfigFile.video)) && !os.contains("mac os")) {
			RecordVideo.stopRecord();
		}
		Logger.info("*[TEST][END][" + status + "][" + methodName + "] " + getTestDescription(result));
		Logger.info(" ");
		stopDriverQuietly();
	}

	private void attachScreenshotToAllure(String methodName) {
		try {
			File dir = new File(com.nhims.constants.FileConst.SCREENSHOT_FOLDER);
			if (!dir.exists() || !dir.isDirectory()) return;
			File[] subDirs = dir.listFiles(File::isDirectory);
			if (subDirs == null) return;
			for (File subDir : subDirs) {
				File img = new File(subDir, methodName + ".png");
				if (img.exists()) {
					InputStream is = new FileInputStream(img);
					Allure.addAttachment("Screenshot on Failure", "image/png", is, ".png");
					break;
				}
			}
		} catch (Exception e) {
			Logger.warning("Could not attach screenshot to Allure: " + e.getMessage());
		}
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
