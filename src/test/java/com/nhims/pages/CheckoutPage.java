package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Checkout page.
 * Handles address details verification, order review, comment, and placing orders.
 */
public class CheckoutPage extends BasePage {
	private static final Control lblAddressDetails = new Control("//h2[contains(text(),'Address Details')]");
	private static final Control lblReviewYourOrder = new Control("//h2[contains(text(),'Review Your Order')]");
	private static final Control lblDeliveryAddress = new Control("#address_delivery");
	private static final Control lblBillingAddress = new Control("#address_invoice");
	private static final Control txtComment = new Control("textarea[name='message']");
	private static final Control btnPlaceOrder = new Control("a.check_out");

	/**
	 * Checks if the 'Address Details' section is visible on the checkout page.
	 *
	 * @return true if the Address Details heading is displayed
	 */
	@Step("Check if 'Address Details' section is visible")
	public static boolean isAddressDetailsVisible() {
		Logger.info("Check if 'Address Details' section is visible");
		return lblAddressDetails.isVisible();
	}

	/**
	 * Checks if the 'Review Your Order' section is visible on the checkout page.
	 *
	 * @return true if the Review Your Order heading is displayed
	 */
	@Step("Check if 'Review Your Order' section is visible")
	public static boolean isReviewYourOrderVisible() {
		Logger.info("Check if 'Review Your Order' section is visible");
		return lblReviewYourOrder.isVisible();
	}

	/**
	 * Gets the delivery address text from the checkout page.
	 *
	 * @return the delivery address text, or empty string if not visible
	 */
	@Step("Get the delivery address text")
	public static String getDeliveryAddressText() {
		Logger.info("Get delivery address text");
		if (lblDeliveryAddress.isVisible()) {
			return lblDeliveryAddress.get().getText();
		}
		return "";
	}

	/**
	 * Gets the billing address text from the checkout page.
	 *
	 * @return the billing address text, or empty string if not visible
	 */
	@Step("Get the billing address text")
	public static String getBillingAddressText() {
		Logger.info("Get billing address text");
		if (lblBillingAddress.isVisible()) {
			return lblBillingAddress.get().getText();
		}
		return "";
	}

	/**
	 * Enters a comment in the order message text area.
	 *
	 * @param comment the comment text to enter
	 */
	@Step("Enter comment in order message text area")
	public static void enterComment(String comment) {
		Logger.info("Enter comment: " + comment);
		txtComment.get().type(comment);
	}

	/**
	 * Clicks the 'Place Order' button to proceed to payment.
	 */
	@Step("Click 'Place Order' button")
	public static void clickPlaceOrder() {
		Logger.info("Click 'Place Order' button");
		btnPlaceOrder.get().click();
	}
}
