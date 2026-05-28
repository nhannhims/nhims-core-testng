package com.nhims.scripts;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nhims.api.AccountAPI;
import com.nhims.browsers.Navigation;
import com.nhims.constants.Configs.EnvironmentConfig;
import com.nhims.data.UserAccount;
import com.nhims.listeners.TestListener;
import com.nhims.pages.AccountCreatedPage;
import com.nhims.pages.AccountDeletedPage;
import com.nhims.pages.CartPage;
import com.nhims.pages.CheckoutPage;
import com.nhims.pages.HomePage;
import com.nhims.pages.PaymentPage;
import com.nhims.pages.ProductsPage;
import com.nhims.pages.RegisterPage;
import com.nhims.pages.SignupLoginPage;
import com.nhims.utils.HDate;
import com.nhims.utils.HFile;
import com.nhims.utils.Logger;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

/**
 * Test class for Place Order feature.
 * Verifies end-to-end flows of placing an order with different registration/login timings:
 * registering during checkout (TC0014), registering before checkout (TC0015),
 * and logging in before checkout (TC0016).
 */
@Listeners(TestListener.class)
@Story("Place Order")
public class PlaceOrderTest {

	private static final int FIRST_PRODUCT_INDEX = 1;
	private static final String ORDER_COMMENT = "Please deliver between 9 AM and 5 PM.";
	private static final String CARD_NAME = "Test Card Holder";
	private static final String CARD_NUMBER = "4111111111111111";
	private static final String CARD_CVC = "123";
	private static final String CARD_EXPIRY_MONTH = "12";
	private static final String CARD_EXPIRY_YEAR = "2028";

	/** Tracks created account email for cleanup via API if test fails (thread-safe for parallel execution). */
	private final ThreadLocal<String> createdEmail = new ThreadLocal<>();
	/** Tracks created account password for cleanup via API if test fails (thread-safe for parallel execution). */
	private final ThreadLocal<String> createdPassword = new ThreadLocal<>();

	/**
	 * Cleanup method that runs after every test (even on failure).
	 * Deletes any account that was created during the test via API.
	 */
	@AfterMethod(alwaysRun = true)
	public void cleanupAccount() {
		String email = createdEmail.get();
		String password = createdPassword.get();
		if (email != null && password != null) {
			Logger.info("Cleanup: Attempting to delete account via API for email: " + email);
			AccountAPI.deleteAccount(email, password);
			createdEmail.remove();
			createdPassword.remove();
		}
	}

	/**
	 * TC0014: Place Order - Register while Checkout.
	 * Verifies that a user can add products to cart, register during checkout,
	 * complete payment, and then delete their account.
	 */
	@Test(testName = "TC0014", description = "Test Case 14: Place Order - Register while Checkout")
	@Description("Verify that a user can add products to cart, proceed to checkout, register during checkout, complete payment and delete account")
	@Severity(SeverityLevel.BLOCKER)
	public void testPlaceOrderRegisterWhileCheckout() {
		UserAccount user = UserAccount.getDefaultUser();

		String timestamp = HDate.uniqueTimestamp();
		String username = "TestUser_" + timestamp;
		String email = "testuser_" + timestamp + "@gmail.com";

		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Add products to cart");
		HomePage.clickProducts();

		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to products page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/products");
		}

		Assert.assertTrue(ProductsPage.isAllProductsPageVisible(), "ALL PRODUCTS page is not visible!");
		ProductsPage.hoverOverProductAndAddToCart(FIRST_PRODUCT_INDEX);

		Logger.info("4. Click 'Cart' button");
		ProductsPage.clickViewCart();

		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to cart page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/view_cart");
		}

		Logger.info("5. Verify that cart page is displayed");
		Assert.assertTrue(CartPage.isShoppingCartPageVisible(), "Cart page is not displayed!");

		Logger.info("6. Click 'Proceed To Checkout'");
		CartPage.clickProceedToCheckout();

		Logger.info("7. Click 'Register / Login' button");
		CartPage.clickRegisterLoginOnModal();

		Logger.info("8. Fill all details in Signup and create account");
		Assert.assertTrue(SignupLoginPage.isNewUserSignupVisible(), "'New User Signup!' title is not visible!");
		SignupLoginPage.enterSignupNameAndEmail(username, email);
		SignupLoginPage.clickSignup();

		Assert.assertTrue(RegisterPage.isEnterAccountInfoVisible(), "'ENTER ACCOUNT INFORMATION' is not visible!");
		RegisterPage.fillAccountDetails(user.getTitle(), user.getPassword(), user.getBirthDay(), user.getBirthMonth(), user.getBirthYear());
		RegisterPage.selectNewsletter();
		RegisterPage.selectSpecialOffers();
		RegisterPage.fillAddressDetails(
				user.getFirstName(),
				user.getLastName(),
				user.getCompany(),
				user.getAddress1(),
				user.getAddress2(),
				user.getCountry(),
				user.getState(),
				user.getCity(),
				user.getZipcode(),
				user.getMobileNumber());
		RegisterPage.clickCreateAccount();

		createdEmail.set(email);
		createdPassword.set(user.getPassword());

		Logger.info("9. Verify 'ACCOUNT CREATED!' and click 'Continue' button");
		Assert.assertTrue(AccountCreatedPage.isAccountCreatedVisible(), "'ACCOUNT CREATED!' page is not visible!");
		String createdText = AccountCreatedPage.getAccountCreatedText();
		Assert.assertEquals(createdText.toLowerCase(), "account created!",
				"Expected success message 'ACCOUNT CREATED!' but got: " + createdText);
		AccountCreatedPage.clickContinue();

		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		Logger.info("10. Verify 'Logged in as username' at top");
		String loggedInText = HomePage.getLoggedInUserText();
		String expectedLoginStatus = "Logged in as " + username;
		Assert.assertTrue(loggedInText.contains(expectedLoginStatus),
				"Expected logged in status containing '" + expectedLoginStatus + "' but got: " + loggedInText);

		Logger.info("11. Click 'Cart' button");
		HomePage.clickCart();

		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to cart page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/view_cart");
		}

		Logger.info("12. Click 'Proceed To Checkout' button");
		CartPage.clickProceedToCheckout();

		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to checkout page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/checkout");
		}

		Logger.info("13. Verify Address Details and Review Your Order");
		Assert.assertTrue(CheckoutPage.isAddressDetailsVisible(), "Address Details section is not visible!");
		Assert.assertTrue(CheckoutPage.isReviewYourOrderVisible(), "Review Your Order section is not visible!");

		String deliveryAddress = CheckoutPage.getDeliveryAddressText();
		Assert.assertTrue(deliveryAddress.toLowerCase().contains(user.getFirstName().toLowerCase()),
				"Delivery address does not contain first name '" + user.getFirstName() + "'!");
		Assert.assertTrue(deliveryAddress.toLowerCase().contains(user.getLastName().toLowerCase()),
				"Delivery address does not contain last name '" + user.getLastName() + "'!");

		Logger.info("14. Enter description in comment text area and click 'Place Order'");
		CheckoutPage.enterComment(ORDER_COMMENT);
		CheckoutPage.clickPlaceOrder();

		Logger.info("15. Enter payment details: Name on Card, Card Number, CVC, Expiration date");
		PaymentPage.fillPaymentDetails(CARD_NAME, CARD_NUMBER, CARD_CVC, CARD_EXPIRY_MONTH, CARD_EXPIRY_YEAR);

		Logger.info("16. Click 'Pay and Confirm Order' button");
		PaymentPage.clickPayAndConfirmOrder();

		Logger.info("17. Verify success message 'Your order has been placed successfully!'");
		Assert.assertTrue(PaymentPage.isOrderPlacedSuccessVisible(),
				"Order placed success message is not visible!");
		String successMessage = PaymentPage.getOrderPlacedSuccessText();
		String successLower = successMessage.toLowerCase();
		Assert.assertTrue(
				successLower.contains("order has been placed successfully") 
				|| successLower.contains("order has been confirmed") 
				|| successLower.contains("order placed"),
				"Expected success message to contain 'order has been placed successfully' or 'order has been confirmed' but got: " + successMessage);

		Logger.info("18. Click 'Delete Account' button");
		HomePage.clickDeleteAccount();

		Logger.info("19. Verify 'ACCOUNT DELETED!' and click 'Continue' button");
		Assert.assertTrue(AccountDeletedPage.isAccountDeletedVisible(), "'ACCOUNT DELETED!' page is not visible!");
		String deletedText = AccountDeletedPage.getAccountDeletedText();
		Assert.assertEquals(deletedText.toLowerCase(), "account deleted!",
				"Expected success message 'ACCOUNT DELETED!' but got: " + deletedText);
		AccountDeletedPage.clickContinue();

		createdEmail.remove();
		createdPassword.remove();
	}

	/**
	 * TC0015: Place Order - Register before Checkout.
	 * Verifies that a user can register first, then add products to cart,
	 * proceed to checkout, complete payment, and delete their account.
	 */
	@Test(testName = "TC0015", description = "Test Case 15: Place Order - Register before Checkout")
	@Description("Verify that a user can register before checkout, add products to cart, complete payment and delete account")
	@Severity(SeverityLevel.BLOCKER)
	public void testPlaceOrderRegisterBeforeCheckout() {
		UserAccount user = UserAccount.getDefaultUser();

		String timestamp = HDate.uniqueTimestamp();
		String username = "TestUser_" + timestamp;
		String email = "testuser_" + timestamp + "@gmail.com";

		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Click 'Signup / Login' button");
		HomePage.clickSignupLogin();

		Logger.info("4. Fill all details in Signup and create account");
		Assert.assertTrue(SignupLoginPage.isNewUserSignupVisible(), "'New User Signup!' title is not visible!");
		SignupLoginPage.enterSignupNameAndEmail(username, email);
		SignupLoginPage.clickSignup();

		Assert.assertTrue(RegisterPage.isEnterAccountInfoVisible(), "'ENTER ACCOUNT INFORMATION' is not visible!");
		RegisterPage.fillAccountDetails(user.getTitle(), user.getPassword(), user.getBirthDay(), user.getBirthMonth(), user.getBirthYear());
		RegisterPage.selectNewsletter();
		RegisterPage.selectSpecialOffers();
		RegisterPage.fillAddressDetails(
				user.getFirstName(),
				user.getLastName(),
				user.getCompany(),
				user.getAddress1(),
				user.getAddress2(),
				user.getCountry(),
				user.getState(),
				user.getCity(),
				user.getZipcode(),
				user.getMobileNumber());
		RegisterPage.clickCreateAccount();

		createdEmail.set(email);
		createdPassword.set(user.getPassword());

		Logger.info("5. Verify 'ACCOUNT CREATED!' and click 'Continue' button");
		Assert.assertTrue(AccountCreatedPage.isAccountCreatedVisible(), "'ACCOUNT CREATED!' page is not visible!");
		String createdText = AccountCreatedPage.getAccountCreatedText();
		Assert.assertEquals(createdText.toLowerCase(), "account created!",
				"Expected success message 'ACCOUNT CREATED!' but got: " + createdText);
		AccountCreatedPage.clickContinue();

		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		Logger.info("6. Verify 'Logged in as username' at top");
		String loggedInText = HomePage.getLoggedInUserText();
		String expectedLoginStatus = "Logged in as " + username;
		Assert.assertTrue(loggedInText.contains(expectedLoginStatus),
				"Expected logged in status containing '" + expectedLoginStatus + "' but got: " + loggedInText);

		Logger.info("7. Add products to cart");
		HomePage.clickProducts();

		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to products page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/products");
		}

		Assert.assertTrue(ProductsPage.isAllProductsPageVisible(), "ALL PRODUCTS page is not visible!");
		ProductsPage.hoverOverProductAndAddToCart(FIRST_PRODUCT_INDEX);
		ProductsPage.clickContinueShopping();

		Logger.info("8. Click 'Cart' button");
		HomePage.clickCart();

		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to cart page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/view_cart");
		}

		Logger.info("9. Verify that cart page is displayed");
		Assert.assertTrue(CartPage.isShoppingCartPageVisible(), "Cart page is not displayed!");

		Logger.info("10. Click Proceed To Checkout");
		CartPage.clickProceedToCheckout();

		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to checkout page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/checkout");
		}

		Logger.info("11. Verify Address Details and Review Your Order");
		Assert.assertTrue(CheckoutPage.isAddressDetailsVisible(), "Address Details section is not visible!");
		Assert.assertTrue(CheckoutPage.isReviewYourOrderVisible(), "Review Your Order section is not visible!");

		String deliveryAddress = CheckoutPage.getDeliveryAddressText();
		Assert.assertTrue(deliveryAddress.toLowerCase().contains(user.getFirstName().toLowerCase()),
				"Delivery address does not contain first name '" + user.getFirstName() + "'!");
		Assert.assertTrue(deliveryAddress.toLowerCase().contains(user.getLastName().toLowerCase()),
				"Delivery address does not contain last name '" + user.getLastName() + "'!");

		Logger.info("12. Enter description in comment text area and click 'Place Order'");
		CheckoutPage.enterComment(ORDER_COMMENT);
		CheckoutPage.clickPlaceOrder();

		Logger.info("13. Enter payment details: Name on Card, Card Number, CVC, Expiration date");
		PaymentPage.fillPaymentDetails(CARD_NAME, CARD_NUMBER, CARD_CVC, CARD_EXPIRY_MONTH, CARD_EXPIRY_YEAR);

		Logger.info("14. Click 'Pay and Confirm Order' button");
		PaymentPage.clickPayAndConfirmOrder();

		Logger.info("15. Verify success message 'Your order has been placed successfully!'");
		Assert.assertTrue(PaymentPage.isOrderPlacedSuccessVisible(),
				"Order placed success message is not visible!");
		String successMessage = PaymentPage.getOrderPlacedSuccessText();
		String successLower = successMessage.toLowerCase();
		Assert.assertTrue(
				successLower.contains("order has been placed successfully")
				|| successLower.contains("order has been confirmed")
				|| successLower.contains("order placed"),
				"Expected success message to contain 'order has been placed successfully' or 'order has been confirmed' but got: " + successMessage);

		Logger.info("16. Click 'Delete Account' button");
		HomePage.clickDeleteAccount();

		Logger.info("17. Verify 'ACCOUNT DELETED!' and click 'Continue' button");
		Assert.assertTrue(AccountDeletedPage.isAccountDeletedVisible(), "'ACCOUNT DELETED!' page is not visible!");
		String deletedText = AccountDeletedPage.getAccountDeletedText();
		Assert.assertEquals(deletedText.toLowerCase(), "account deleted!",
				"Expected success message 'ACCOUNT DELETED!' but got: " + deletedText);
		AccountDeletedPage.clickContinue();

		createdEmail.remove();
		createdPassword.remove();
	}

	/**
	 * TC0016: Place Order - Login before Checkout.
	 * Verifies that a user can log in with an existing account, add products to cart,
	 * proceed to checkout, complete payment, and then delete their account.
	 */
	@Test(testName = "TC0016", description = "Test Case 16: Place Order - Login before Checkout")
	@Description("Verify that a user can log in before checkout, add products to cart, complete payment and delete account")
	@Severity(SeverityLevel.BLOCKER)
	public void testPlaceOrderLoginBeforeCheckout() {
		UserAccount user = UserAccount.getDefaultUser();

		String timestamp = HDate.uniqueTimestamp();
		String username = "TestUser_" + timestamp;
		String email = "testuser_" + timestamp + "@gmail.com";

		// Precondition: Create a user account via API before test
		Logger.info("Precondition: Create a user account via API");
		user.setName(username);
		user.setEmail(email);
		Assert.assertTrue(AccountAPI.createAccount(user), "Failed to create user account via API!");

		// Track created account for cleanup via API if test fails afterward
		createdEmail.set(email);
		createdPassword.set(user.getPassword());

		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Click 'Signup / Login' button");
		HomePage.clickSignupLogin();

		Logger.info("4. Fill email, password and click 'Login' button");
		Assert.assertTrue(SignupLoginPage.isLoginToYourAccountVisible(),
				"'Login to your account' title is not visible!");
		SignupLoginPage.enterLoginEmailAndPassword(email, user.getPassword());
		SignupLoginPage.clickLogin();

		// Bypass potential Google vignette ad redirect
		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		Logger.info("5. Verify 'Logged in as username' at top");
		String loggedInText = HomePage.getLoggedInUserText();
		String expectedLoginStatus = "Logged in as " + username;
		Assert.assertTrue(loggedInText.contains(expectedLoginStatus),
				"Expected logged in status containing '" + expectedLoginStatus + "' but got: " + loggedInText);

		Logger.info("6. Add products to cart");
		HomePage.clickProducts();

		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to products page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/products");
		}

		Assert.assertTrue(ProductsPage.isAllProductsPageVisible(), "ALL PRODUCTS page is not visible!");
		ProductsPage.hoverOverProductAndAddToCart(FIRST_PRODUCT_INDEX);
		ProductsPage.clickContinueShopping();

		Logger.info("7. Click 'Cart' button");
		HomePage.clickCart();

		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to cart page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/view_cart");
		}

		Logger.info("8. Verify that cart page is displayed");
		Assert.assertTrue(CartPage.isShoppingCartPageVisible(), "Cart page is not displayed!");

		Logger.info("9. Click Proceed To Checkout");
		CartPage.clickProceedToCheckout();

		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to checkout page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/checkout");
		}

		Logger.info("10. Verify Address Details and Review Your Order");
		Assert.assertTrue(CheckoutPage.isAddressDetailsVisible(), "Address Details section is not visible!");
		Assert.assertTrue(CheckoutPage.isReviewYourOrderVisible(), "Review Your Order section is not visible!");

		String deliveryAddress = CheckoutPage.getDeliveryAddressText();
		Assert.assertTrue(deliveryAddress.toLowerCase().contains(user.getFirstName().toLowerCase()),
				"Delivery address does not contain first name '" + user.getFirstName() + "'!");
		Assert.assertTrue(deliveryAddress.toLowerCase().contains(user.getLastName().toLowerCase()),
				"Delivery address does not contain last name '" + user.getLastName() + "'!");

		Logger.info("11. Enter description in comment text area and click 'Place Order'");
		CheckoutPage.enterComment(ORDER_COMMENT);
		CheckoutPage.clickPlaceOrder();

		Logger.info("12. Enter payment details: Name on Card, Card Number, CVC, Expiration date");
		PaymentPage.fillPaymentDetails(CARD_NAME, CARD_NUMBER, CARD_CVC, CARD_EXPIRY_MONTH, CARD_EXPIRY_YEAR);

		Logger.info("13. Click 'Pay and Confirm Order' button");
		PaymentPage.clickPayAndConfirmOrder();

		Logger.info("14. Verify success message 'Your order has been placed successfully!'");
		Assert.assertTrue(PaymentPage.isOrderPlacedSuccessVisible(),
				"Order placed success message is not visible!");
		String successMessage = PaymentPage.getOrderPlacedSuccessText();
		String successLower = successMessage.toLowerCase();
		Assert.assertTrue(
				successLower.contains("order has been placed successfully")
				|| successLower.contains("order has been confirmed")
				|| successLower.contains("order placed"),
				"Expected success message to contain 'order has been placed successfully' or 'order has been confirmed' but got: " + successMessage);

		Logger.info("15. Click 'Delete Account' button");
		HomePage.clickDeleteAccount();

		Logger.info("16. Verify 'ACCOUNT DELETED!' and click 'Continue' button");
		Assert.assertTrue(AccountDeletedPage.isAccountDeletedVisible(), "'ACCOUNT DELETED!' page is not visible!");
		String deletedText = AccountDeletedPage.getAccountDeletedText();
		Assert.assertEquals(deletedText.toLowerCase(), "account deleted!",
				"Expected success message 'ACCOUNT DELETED!' but got: " + deletedText);
		AccountDeletedPage.clickContinue();

		// Account already deleted via UI, clear tracking to avoid double delete
		createdEmail.remove();
		createdPassword.remove();
	}
}
