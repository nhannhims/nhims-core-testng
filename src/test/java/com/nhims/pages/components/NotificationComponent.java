package com.nhims.pages.components;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Reusable component representing a notification page with a title message and a continue button.
 * Used by AccountCreatedPage and AccountDeletedPage to eliminate duplicate code.
 *
 * <p>Usage example:</p>
 * <pre>
 *   private static final NotificationComponent notification =
 *       new NotificationComponent("h2[data-qa='account-created']", "a[data-qa='continue-button']");
 * </pre>
 */
public class NotificationComponent {

	private final Control lblTitle;
	private final Control btnContinue;

	/**
	 * Creates a NotificationComponent with the given locators.
	 *
	 * @param titleLocator    the CSS/XPath locator for the notification title element
	 * @param continueLocator the CSS/XPath locator for the continue button element
	 */
	public NotificationComponent(String titleLocator, String continueLocator) {
		this.lblTitle = new Control(titleLocator);
		this.btnContinue = new Control(continueLocator);
	}

	/**
	 * Checks if the notification title is visible on the page.
	 *
	 * @return true if the title element is visible
	 */
	@Step("Check if notification title is visible")
	public boolean isTitleVisible() {
		Logger.info("Check if notification title is visible");
		return lblTitle.isVisible();
	}

	/**
	 * Gets the text of the notification title.
	 *
	 * @return the title text, or empty string if not visible
	 */
	@Step("Get notification title text")
	public String getTitleText() {
		Logger.info("Get notification title text");
		if (lblTitle.isVisible()) {
			return lblTitle.get().getText();
		}
		return "";
	}

	/**
	 * Clicks the continue button.
	 */
	@Step("Click 'Continue' button on notification page")
	public void clickContinue() {
		Logger.info("Click 'Continue' button");
		btnContinue.get().click();
	}
}
