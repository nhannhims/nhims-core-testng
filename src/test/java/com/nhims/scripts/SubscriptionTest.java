package com.nhims.scripts;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nhims.browsers.BrowserExtensions;
import com.nhims.browsers.Navigation;
import com.nhims.constants.Configs.EnvironmentConfig;
import com.nhims.constants.Constants.LOCATION;
import com.nhims.listeners.TestListener;
import com.nhims.pages.CartPage;
import com.nhims.pages.HomePage;
import com.nhims.utils.HDate;
import com.nhims.utils.HFile;
import com.nhims.utils.Logger;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

/**
 * Test class for Subscription feature.
 * Verifies the subscription functionality on the home page footer.
 */
@Listeners(TestListener.class)
@Story("Subscription")
public class SubscriptionTest {

	/**
	 * TC0010: Verify Subscription in home page.
	 * Navigates to the home page, scrolls down to the footer,
	 * verifies the SUBSCRIPTION text is visible, enters an email address
	 * and clicks the subscribe button, then verifies the success message.
	 */
	@Test(testName = "TC0010", description = "Test Case 10: Verify Subscription in home page")
	@Description("Verify that user can subscribe from the home page footer and see the success message")
	@Severity(SeverityLevel.CRITICAL)
	public void testVerifySubscriptionHomePage() {
		String email = "subscribe_" + HDate.uniqueTimestamp() + "@gmail.com";

		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		// Bypass potential Google vignette ad redirect
		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Scroll down to footer");
		BrowserExtensions.moveTo(LOCATION.BOTTOM);

		Logger.info("4. Verify text 'SUBSCRIPTION' is visible");
		Assert.assertTrue(HomePage.isSubscriptionVisible(), "'SUBSCRIPTION' text is not visible!");
		String subscriptionText = HomePage.getSubscriptionText();
		Assert.assertTrue(subscriptionText.toLowerCase().contains("subscription"),
				"Expected text to contain 'SUBSCRIPTION' but got: " + subscriptionText);

		Logger.info("5. Enter email address in input and click arrow button");
		HomePage.subscribeEmail(email);

		Logger.info("6. Verify success message 'You have been successfully subscribed!' is visible");
		Assert.assertTrue(HomePage.isSubscribeSuccessMessageVisible(),
				"Subscription success message is not visible!");
		String successMessage = HomePage.getSubscribeSuccessMessageText();
		Assert.assertTrue(successMessage.toLowerCase().contains("successfully subscribed"),
				"Expected success message to contain 'successfully subscribed' but got: " + successMessage);
	}

	/**
	 * TC0011: Verify Subscription in Cart page.
	 * Navigates to the home page, clicks the Cart button to go to the cart page,
	 * scrolls down to the footer, verifies the SUBSCRIPTION text is visible,
	 * enters an email address and clicks the subscribe button, then verifies the success message.
	 */
	@Test(testName = "TC0011", description = "Test Case 11: Verify Subscription in Cart page")
	@Description("Verify that user can subscribe from the cart page footer and see the success message")
	@Severity(SeverityLevel.CRITICAL)
	public void testVerifySubscriptionCartPage() {
		String email = "subscribe_" + HDate.uniqueTimestamp() + "@gmail.com";

		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		// Bypass potential Google vignette ad redirect
		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Click 'Cart' button");
		HomePage.clickCart();

		// Bypass potential Google vignette ad redirect
		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating directly to Cart page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/view_cart");
		}

		Logger.info("4. Scroll down to footer");
		BrowserExtensions.moveTo(LOCATION.BOTTOM);

		Logger.info("5. Verify text 'SUBSCRIPTION' is visible");
		Assert.assertTrue(CartPage.isSubscriptionVisible(), "'SUBSCRIPTION' text is not visible on Cart page!");
		String subscriptionText = CartPage.getSubscriptionText();
		Assert.assertTrue(subscriptionText.toLowerCase().contains("subscription"),
				"Expected text to contain 'SUBSCRIPTION' but got: " + subscriptionText);

		Logger.info("6. Enter email address in input and click arrow button");
		CartPage.subscribeEmail(email);

		Logger.info("7. Verify success message 'You have been successfully subscribed!' is visible");
		Assert.assertTrue(CartPage.isSubscribeSuccessMessageVisible(),
				"Subscription success message is not visible on Cart page!");
		String successMessage = CartPage.getSubscribeSuccessMessageText();
		Assert.assertTrue(successMessage.toLowerCase().contains("successfully subscribed"),
				"Expected success message to contain 'successfully subscribed' but got: " + successMessage);
	}
}
