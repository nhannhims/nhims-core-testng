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

	/**
	 * Checks if the 'ACCOUNT DELETED!' notification is visible.
	 *
	 * @return true if the notification is displayed
	 */
	@Step("Check if 'ACCOUNT DELETED!' notification is visible")
	public static boolean isAccountDeletedVisible() {
		Logger.info("Check if 'ACCOUNT DELETED!' is visible");
		return notification.isTitleVisible();
	}

	/**
	 * Gets the text of the 'ACCOUNT DELETED!' notification.
	 *
	 * @return the notification text, or empty string if not visible
	 */
	@Step("Get the text of the 'ACCOUNT DELETED!' notification")
	public static String getAccountDeletedText() {
		Logger.info("Get 'ACCOUNT DELETED!' text");
		return notification.getTitleText();
	}

	/**
	 * Clicks the 'Continue' button on the Account Deleted page.
	 */
	@Step("Click the 'Continue' button on the Account Deleted page")
	public static void clickContinue() {
		Logger.info("Click 'Continue' button");
		notification.clickContinue();
	}
}
