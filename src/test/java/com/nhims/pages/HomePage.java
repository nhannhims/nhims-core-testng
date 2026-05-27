package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.pages.components.SubscriptionComponent;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Home page.
 */
public class HomePage extends BasePage {
	private static final Control imgLogo = new Control("div.logo");
	private static final Control btnSignupLogin = new Control("a[href='/login']");
	private static final Control btnDeleteAccount = new Control("a[href='/delete_account']");
	private static final Control btnLogout = new Control("a[href='/logout']");
	private static final Control btnContactUs = new Control("a[href='/contact_us']");
	private static final Control lblLoggedInAs = new Control("//a[contains(., 'Logged in as')]");
	private static final Control btnTestCases = new Control("a[href='/test_cases']");
	private static final Control btnProducts = new Control("a[href='/products']");
	private static final Control btnCart = new Control("a[href='/view_cart']");
	private static final Control lnkViewProductFirst = new Control(
			"//div[@class='features_items']//div[@class='choose']//a[contains(text(),'View Product')][1]");
	private static final SubscriptionComponent subscription = new SubscriptionComponent(
			"div.single-widget:has(#susbscribe_email) h2",
			"#susbscribe_email",
			"#subscribe",
			"#success-subscribe");

	/**
	 * Checks if the Home Page is visible by verifying the logo element.
	 *
	 * @return true if the home page logo is displayed
	 */
	@Step("Check if the Home Page is visible")
	public static boolean isHomePageVisible() {
		Logger.info("Check if Home Page is visible");
		return imgLogo.isVisible();
	}

	/**
	 * Clicks the 'Signup / Login' button on the home page header.
	 */
	@Step("Click 'Signup / Login' button")
	public static void clickSignupLogin() {
		Logger.info("Click on 'Signup / Login' button");
		btnSignupLogin.get().click();
	}

	/**
	 * Clicks the 'Delete Account' button on the home page header.
	 */
	@Step("Click 'Delete Account' button")
	public static void clickDeleteAccount() {
		Logger.info("Click on 'Delete Account' button");
		btnDeleteAccount.get().click();
	}

	/**
	 * Clicks the 'Logout' button on the home page header.
	 */
	@Step("Click 'Logout' button")
	public static void clickLogout() {
		Logger.info("Click on 'Logout' button");
		btnLogout.get().click();
	}

	/**
	 * Clicks the 'Contact Us' button on the home page header.
	 */
	@Step("Click 'Contact Us' button")
	public static void clickContactUs() {
		Logger.info("Click on 'Contact Us' button");
		btnContactUs.get().click();
	}

	/**
	 * Gets the logged in user status text (e.g. "Logged in as username").
	 *
	 * @return the logged in user text, or empty string if not visible
	 */
	@Step("Get the logged in user status text")
	public static String getLoggedInUserText() {
		Logger.info("Get logged in user status text");
		if (lblLoggedInAs.isVisible()) {
			return lblLoggedInAs.get().getText();
		}
		return "";
	}

	/**
	 * Click 'Test Cases' button.
	 */
	@Step("Click 'Test Cases' button")
	public static void clickTestCases() {
		Logger.info("Click on 'Test Cases' button");
		btnTestCases.get().click();
	}

	/**
	 * Click 'Products' button.
	 */
	@Step("Click 'Products' button")
	public static void clickProducts() {
		Logger.info("Click on 'Products' button");
		btnProducts.get().click();
	}

	/**
	 * Click 'Cart' button.
	 */
	@Step("Click 'Cart' button")
	public static void clickCart() {
		Logger.info("Click on 'Cart' button");
		btnCart.get().click();
	}

	/**
	 * Clicks 'View Product' link of the first product on the home page.
	 */
	@Step("Click 'View Product' for first product")
	public static void clickViewProductOnHomePage() {
		Logger.info("Click 'View Product' for first product");
		lnkViewProductFirst.get().click();
	}

	/**
	 * Gets the href attribute of the first product's 'View Product' link.
	 *
	 * @return the product detail URL, or empty string if not visible
	 */
	@Step("Get 'View Product' href of first product")
	public static String getFirstProductDetailUrl() {
		Logger.info("Get 'View Product' href of first product");
		if (lnkViewProductFirst.isVisible()) {
			return lnkViewProductFirst.get().getAttr("href");
		}
		return "";
	}

	/**
	 * Checks if the 'SUBSCRIPTION' heading is visible in the footer.
	 *
	 * @return true if the subscription heading is visible, false otherwise
	 */
	@Step("Check if 'SUBSCRIPTION' heading is visible")
	public static boolean isSubscriptionVisible() {
		Logger.info("Check if 'SUBSCRIPTION' heading is visible");
		return subscription.isSubscriptionVisible();
	}

	/**
	 * Gets the text of the 'SUBSCRIPTION' heading in the footer.
	 *
	 * @return the subscription heading text, or empty string if not visible
	 */
	@Step("Get the text of 'SUBSCRIPTION' heading")
	public static String getSubscriptionText() {
		Logger.info("Get 'SUBSCRIPTION' heading text");
		return subscription.getSubscriptionText();
	}

	/**
	 * Enters an email address and clicks the subscribe arrow button.
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
