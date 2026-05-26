package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Test Cases page.
 */
public class TestCasesPage extends BasePage {
	private static final Control lblTitle = new Control("h2.title.text-center");

	/**
	 * Checks if the Test Cases page is visible by verifying the title element.
	 *
	 * @return true if the test cases page title is displayed
	 */
	@Step("Check if the Test Cases page is visible")
	public static boolean isTestCasesPageVisible() {
		Logger.info("Check if the Test Cases page is visible");
		return lblTitle.isVisible();
	}

	/**
	 * Gets the title text on the Test Cases page.
	 *
	 * @return the title text, or empty string if not visible
	 */
	@Step("Get the title text on the Test Cases page")
	public static String getTitleText() {
		Logger.info("Get the title text on the Test Cases page");
		if (lblTitle.isVisible()) {
			return lblTitle.get().getText();
		}
		return "";
	}
}
