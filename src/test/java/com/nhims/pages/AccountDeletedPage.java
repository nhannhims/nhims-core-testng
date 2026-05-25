package com.nhims.pages;

import com.nhims.pages.components.NotificationComponent;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Account Deleted success page.
 * Uses NotificationComponent to handle the common title + continue pattern.
 */
public class AccountDeletedPage extends BasePage {
	private static final NotificationComponent notification =
			new NotificationComponent("h2[data-qa='account-deleted']", "a[data-qa='continue-button']");

	@Step("Check if 'ACCOUNT DELETED!' notification is visible")
	public static boolean isAccountDeletedVisible() {
		Logger.info("Check if 'ACCOUNT DELETED!' is visible");
		return notification.isTitleVisible();
	}

	@Step("Get the text of the 'ACCOUNT DELETED!' notification")
	public static String getAccountDeletedText() {
		Logger.info("Get 'ACCOUNT DELETED!' text");
		return notification.getTitleText();
	}

	@Step("Click the 'Continue' button on the Account Deleted page")
	public static void clickContinue() {
		Logger.info("Click 'Continue' button");
		notification.clickContinue();
	}
}
