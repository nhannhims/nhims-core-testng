package com.nhims.pages;

import com.nhims.controls.Control;
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
	private static final Control lblShoppingCart = new Control(".breadcrumbs .active");
	private static final Control lblCartProductName = new Control(
			"(//table[@id='cart_info_table']//tbody/tr)[%d]/td[@class='cart_description']//h4/a");
	private static final Control lblCartProductPrice = new Control(
			"(//table[@id='cart_info_table']//tbody/tr)[%d]/td[@class='cart_price']/p");
	private static final Control btnCartProductQuantity = new Control(
			"(//table[@id='cart_info_table']//tbody/tr)[%d]/td[@class='cart_quantity']/button");
	private static final Control lblCartProductTotal = new Control(
			"(//table[@id='cart_info_table']//tbody/tr)[%d]/td[@class='cart_total']//p");

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

	/**
	 * Checks if the Shopping Cart page is visible by verifying the breadcrumb.
	 *
	 * @return true if the shopping cart breadcrumb is displayed
	 */
	@Step("Check if Shopping Cart page is visible")
	public static boolean isShoppingCartPageVisible() {
		Logger.info("Check if Shopping Cart page is visible");
		return lblShoppingCart.isVisible();
	}

	/**
	 * Gets the product name from the cart table by row index.
	 *
	 * @param index the 1-based row index in the cart table
	 * @return the product name, or empty string if not visible
	 */
	@Step("Get cart product name at row {0}")
	public static String getCartProductName(int index) {
		Logger.info("Get cart product name at row " + index);
		Control productName = lblCartProductName.setDynamicLocator(index);
		if (productName.isVisible()) {
			return productName.get().getText();
		}
		return "";
	}

	/**
	 * Gets the product unit price from the cart table by row index.
	 *
	 * @param index the 1-based row index in the cart table
	 * @return the price text, or empty string if not visible
	 */
	@Step("Get cart product price at row {0}")
	public static String getCartProductPrice(int index) {
		Logger.info("Get cart product price at row " + index);
		Control productPrice = lblCartProductPrice.setDynamicLocator(index);
		if (productPrice.isVisible()) {
			return productPrice.get().getText();
		}
		return "";
	}

	/**
	 * Gets the product quantity from the cart table by row index.
	 *
	 * @param index the 1-based row index in the cart table
	 * @return the quantity text, or empty string if not visible
	 */
	@Step("Get cart product quantity at row {0}")
	public static String getCartProductQuantity(int index) {
		Logger.info("Get cart product quantity at row " + index);
		Control productQuantity = btnCartProductQuantity.setDynamicLocator(index);
		if (productQuantity.isVisible()) {
			return productQuantity.get().getText();
		}
		return "";
	}

	/**
	 * Gets the product total price from the cart table by row index.
	 *
	 * @param index the 1-based row index in the cart table
	 * @return the total price text, or empty string if not visible
	 */
	@Step("Get cart product total price at row {0}")
	public static String getCartProductTotalPrice(int index) {
		Logger.info("Get cart product total price at row " + index);
		Control productTotal = lblCartProductTotal.setDynamicLocator(index);
		if (productTotal.isVisible()) {
			return productTotal.get().getText();
		}
		return "";
	}
}
