package com.nhims.pages;

import com.nhims.pages.components.SubscriptionComponent;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Cart page.
 */
public class CartPage extends BasePage {
	private static final SubscriptionComponent subscription = new SubscriptionComponent(
			"div.single-widget:has(#susbscribe_email) h2",
			"#susbscribe_email",
			"#subscribe",
			"#success-subscribe");

	/**
	 * Checks if the subscription heading is visible in the footer.
	 *
	 * @return true if the subscription heading is visible, false otherwise
	 */
	@Step("Check if 'SUBSCRIPTION' heading is visible")
	public static boolean isSubscriptionVisible() {
		Logger.info("Check if 'SUBSCRIPTION' heading is visible");
		return subscription.isSubscriptionVisible();
	}

	/**
	 * Gets the text of the subscription heading in the footer.
	 *
	 * @return the subscription heading text, or empty string if not visible
	 */
	@Step("Get the text of 'SUBSCRIPTION' heading")
	public static String getSubscriptionText() {
		Logger.info("Get 'SUBSCRIPTION' heading text");
		return subscription.getSubscriptionText();
	}

	/**
	 * Enters an email address and clicks the subscribe button.
	 *
	 * @param email the email address to subscribe with
	 */
	@Step("Subscribe with email: {email}")
	public static void subscribeEmail(String email) {
		Logger.info("Enter email and click subscribe button");
		subscription.subscribeEmail(email);
	}

	/**
	 * Checks if the subscription success message is visible.
	 *
	 * @return true if the success message is visible, false otherwise
	 */
	@Step("Check if subscription success message is visible")
	public static boolean isSubscribeSuccessMessageVisible() {
		Logger.info("Check if subscription success message is visible");
		return subscription.isSubscribeSuccessMessageVisible();
	}

	/**
	 * Gets the text of the subscription success message.
	 *
	 * @return the success message text, or empty string if not visible
	 */
	@Step("Get the subscription success message text")
	public static String getSubscribeSuccessMessageText() {
		Logger.info("Get subscription success message text");
		return subscription.getSubscribeSuccessMessageText();
	}
}
