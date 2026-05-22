package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Account Created success page.
 */
public class AccountCreatedPage extends BasePage {
	private static final Control lblAccountCreated = new Control("h2[data-qa='account-created']");
	private static final Control btnContinue = new Control("a[data-qa='continue-button']");

	@Step("Check if 'ACCOUNT CREATED!' notification is visible")
	public static boolean isAccountCreatedVisible() {
		Logger.info("Check if 'ACCOUNT CREATED!' is visible");
		return lblAccountCreated.isVisible();
	}

	@Step("Get the text of the 'ACCOUNT CREATED!' notification")
	public static String getAccountCreatedText() {
		Logger.info("Get 'ACCOUNT CREATED!' text");
		if (lblAccountCreated.isVisible()) {
			return lblAccountCreated.get().getText();
		}
		return "";
	}

	@Step("Click the 'Continue' button on the Account Created page")
	public static void clickContinue() {
		Logger.info("Click 'Continue' button");
		btnContinue.get().click();
	}
}
