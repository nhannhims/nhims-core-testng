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

	/**
	 * Gets the custom test name from the TestNG result.
	 *
	 * @param result the TestNG execution result
	 * @return the name of the test
	 */
	public String getTestName(ITestResult result) {
		return result.getTestName();
	}

	/**
	 * Gets the test description defined in the TestNG annotation.
	 *
	 * @param result the TestNG execution result
	 * @return the test description string
	 */
	public String getTestDescription(ITestResult result) {
		return result.getMethod().getDescription();
	}

	/**
	 * Gets the class name of the executing test script.
	 *
	 * @param result the TestNG execution result
	 * @return the full name of the test class
	 */
	public String getFileScriptName(ITestResult result) {
		return result.getMethod().getTestClass().getName();
	}

	/**
	 * Gets the method name of the executing test.
	 *
	 * @param result the TestNG execution result
	 * @return the name of the test method
	 */
	public String getMethodName(ITestResult result) {
		return result.getMethod().getMethodName();
	}

	/**
	 * Hook executed when the test suite/context starts.
	 * Initializes screenshot directory based on current timestamp.
	 *
	 * @param context the TestNG test context
	 */
	@Override
	public void onStart(ITestContext context) {
		Browsers.setScreenshotFolder(HDate.formatDate("yyyy_MM_dd_hh_mm_ss"));
		Logger.info("### [START] Suite Context: " + context.getName());
	}

	/**
	 * Hook executed when the test suite/context finishes.
	 *
	 * @param context the TestNG test context
	 */
	@Override
	public void onFinish(ITestContext context) {
		Logger.info("### [END] Suite Context: " + context.getName());
	}

	/**
	 * Hook executed before a test method starts.
	 * Initializes the WebDriver instance and optionally begins video recording.
	 *
	 * @param result the TestNG execution result
	 */
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

	/**
	 * Hook executed when a test method passes successfully.
	 * Captures screenshots and stops active recording/driver.
	 *
	 * @param result the TestNG execution result
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		captureAndStop(result, "PASSED");
	}

	/**
	 * Hook executed when a test method fails.
	 * Captures screenshot, attaches it to the Allure report, and stops active recording/driver.
	 *
	 * @param result the TestNG execution result
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		captureAndStop(result, "FAILED");
	}

	/**
	 * Hook executed when a test method is skipped.
	 *
	 * @param result the TestNG execution result
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		Logger.info("*[TEST][END][SKIPPED][" + getMethodName(result) + "] " + getTestDescription(result));
		stopDriverQuietly();
	}

	// ─── Private helpers ─────────────────────────────────────────────────────────

	/**
	 * Common tear-down routine to capture state on completion/failure and stop browser driver.
	 *
	 * @param result the TestNG execution result
	 * @param status the end status of the test ("PASSED" or "FAILED")
	 */
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

	/**
	 * Attaches a screenshot file matching the given test method name to the Allure report.
	 *
	 * @param methodName the name of the failing test method
	 */
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

	/**
	 * Stops the browser driver without throwing exceptions.
	 */
	private void stopDriverQuietly() {
		try {
			DriverController.instance.stopDriver();
			Logger.info("### [END] Stop Driver");
		} catch (Exception e) {
			Logger.error("Can not handle quit driver: " + e.getMessage());
		}
	}
}
