package com.nhims.pages.components;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Reusable component for the subscription footer block that appears on
 * multiple pages (HomePage, CartPage, etc.) of the AutomationExercise website.
 */
public class SubscriptionComponent {

	private final Control lblHeading;
	private final Control txtEmail;
	private final Control btnSubscribe;
	private final Control lblSuccessMessage;

	/**
	 * Constructs a SubscriptionComponent with the given CSS locators.
	 *
	 * @param headingLocator      locator for the subscription heading
	 * @param emailLocator        locator for the email input field
	 * @param subscribeLocator    locator for the subscribe button
	 * @param successMsgLocator   locator for the success message
	 */
	public SubscriptionComponent(String headingLocator, String emailLocator,
			String subscribeLocator, String successMsgLocator) {
		this.lblHeading = new Control(headingLocator);
		this.txtEmail = new Control(emailLocator);
		this.btnSubscribe = new Control(subscribeLocator);
		this.lblSuccessMessage = new Control(successMsgLocator);
	}

	/**
	 * Checks whether the subscription heading is visible.
	 *
	 * @return {@code true} if the subscription heading is displayed
	 */
	@Step("Check if subscription section is visible")
	public boolean isSubscriptionVisible() {
		Logger.info("Check if subscription section is visible");
		return lblHeading.isVisible();
	}

	/**
	 * Returns the text of the subscription heading.
	 * Returns an empty string if the heading is not visible.
	 *
	 * @return subscription heading text, or empty string if not visible
	 */
	@Step("Get subscription heading text")
	public String getSubscriptionText() {
		Logger.info("Get subscription heading text");
		if (!lblHeading.isVisible()) {
			return "";
		}
		return lblHeading.get().getText();
	}

	/**
	 * Enters the given email address and clicks the subscribe button.
	 *
	 * @param email the email address to subscribe with
	 */
	@Step("Subscribe with email: {0}")
	public void subscribeEmail(String email) {
		Logger.info("Subscribe with email: " + email);
		txtEmail.get().type(email);
		btnSubscribe.get().click();
	}

	/**
	 * Checks whether the subscription success message is visible.
	 *
	 * @return {@code true} if the success message is displayed
	 */
	@Step("Check if subscribe success message is visible")
	public boolean isSubscribeSuccessMessageVisible() {
		Logger.info("Check if subscribe success message is visible");
		return lblSuccessMessage.isVisible();
	}

	/**
	 * Returns the text of the subscription success message.
	 * Returns an empty string if the success message is not visible.
	 *
	 * @return success message text, or empty string if not visible
	 */
	@Step("Get subscribe success message text")
	public String getSubscribeSuccessMessageText() {
		Logger.info("Get subscribe success message text");
		if (!lblSuccessMessage.isVisible()) {
			return "";
		}
		return lblSuccessMessage.get().getText();
	}
}
