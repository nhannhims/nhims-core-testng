package com.nhims.scripts;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nhims.browsers.Navigation;
import com.nhims.constants.Configs.EnvironmentConfig;
import com.nhims.listeners.TestListener;
import com.nhims.pages.HomePage;
import com.nhims.pages.TestCasesPage;
import com.nhims.utils.HFile;
import com.nhims.utils.Logger;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

/**
 * Test class for Test Cases page.
 * Verifies that user is navigated to test cases page successfully.
 */
@Listeners(TestListener.class)
@Story("Test Cases")
public class TestCasesTest {

	/**
	 * TC0007: Verify Test Cases Page.
	 * Navigates to the home page, clicks the Test Cases button,
	 * and verifies the test cases page is displayed.
	 */
	@Test(testName = "TC0007", description = "Test Case 7: Verify Test Cases Page")
	@Description("Verify that user is navigated to test cases page successfully")
	@Severity(SeverityLevel.CRITICAL)
	public void testVerifyTestCasesPage() {
		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Click on 'Test Cases' button");
		HomePage.clickTestCases();

		// Bypass potential Google vignette ad redirect
		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating directly to test cases page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/test_cases");
		}

		Logger.info("4. Verify user is navigated to test cases page successfully");
		Assert.assertTrue(TestCasesPage.isTestCasesPageVisible(), "Test Cases page is not visible!");
		String titleText = TestCasesPage.getTitleText();
		Assert.assertEquals(titleText.toLowerCase(), "test cases",
				"Expected test cases page title to be 'TEST CASES' but got: " + titleText);
	}
}
