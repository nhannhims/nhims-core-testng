package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Account Deleted success page.
 */
public class AccountDeletedPage extends BasePage {
	private static final Control lblAccountDeleted = new Control("h2[data-qa='account-deleted']");
	private static final Control btnContinue = new Control("a[data-qa='continue-button']");

	@Step("Check if 'ACCOUNT DELETED!' notification is visible")
	public static boolean isAccountDeletedVisible() {
		Logger.info("Check if 'ACCOUNT DELETED!' is visible");
		return lblAccountDeleted.isVisible();
	}

	@Step("Get the text of the 'ACCOUNT DELETED!' notification")
	public static String getAccountDeletedText() {
		Logger.info("Get 'ACCOUNT DELETED!' text");
		if (lblAccountDeleted.isVisible()) {
			return lblAccountDeleted.get().getText();
		}
		return "";
	}

	@Step("Click the 'Continue' button on the Account Deleted page")
	public static void clickContinue() {
		Logger.info("Click 'Continue' button");
		btnContinue.get().click();
	}
}
