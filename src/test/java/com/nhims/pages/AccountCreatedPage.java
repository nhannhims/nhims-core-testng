package com.nhims.pages;

import com.nhims.pages.components.NotificationComponent;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Account Created success page.
 * Uses NotificationComponent to handle the common title + continue pattern.
 */
public class AccountCreatedPage extends BasePage {
	private static final NotificationComponent notification =
			new NotificationComponent("h2[data-qa='account-created']", "a[data-qa='continue-button']");

	/**
	 * Checks if the 'ACCOUNT CREATED!' notification is visible.
	 *
	 * @return true if the notification is displayed
	 */
	@Step("Check if 'ACCOUNT CREATED!' notification is visible")
	public static boolean isAccountCreatedVisible() {
		Logger.info("Check if 'ACCOUNT CREATED!' is visible");
		return notification.isTitleVisible();
	}

	/**
	 * Gets the text of the 'ACCOUNT CREATED!' notification.
	 *
	 * @return the notification text, or empty string if not visible
	 */
	@Step("Get the text of the 'ACCOUNT CREATED!' notification")
	public static String getAccountCreatedText() {
		Logger.info("Get 'ACCOUNT CREATED!' text");
		return notification.getTitleText();
	}

	/**
	 * Clicks the 'Continue' button on the Account Created page.
	 */
	@Step("Click the 'Continue' button on the Account Created page")
	public static void clickContinue() {
		Logger.info("Click 'Continue' button");
		notification.clickContinue();
	}
}
