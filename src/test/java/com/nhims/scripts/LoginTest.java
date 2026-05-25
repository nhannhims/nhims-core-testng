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
import com.nhims.pages.AccountDeletedPage;
import com.nhims.pages.HomePage;
import com.nhims.pages.SignupLoginPage;
import com.nhims.utils.HDate;
import com.nhims.utils.HFile;
import com.nhims.utils.Logger;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

/**
 * Test class for Login feature test cases.
 */
@Listeners(TestListener.class)
@Story("User Management")
public class LoginTest {

	/** Tracks created account email for cleanup via API if test fails. */
	private String createdEmail;
	/** Tracks created account password for cleanup via API if test fails. */
	private String createdPassword;

	/**
	 * Cleanup method that runs after every test (even on failure).
	 * Deletes any account that was created during the test via API.
	 */
	@AfterMethod(alwaysRun = true)
	public void cleanupAccount() {
		if (createdEmail != null && createdPassword != null) {
			Logger.info("Cleanup: Attempting to delete account via API for email: " + createdEmail);
			AccountAPI.deleteAccount(createdEmail, createdPassword);
			createdEmail = null;
			createdPassword = null;
		}
	}

	/**
	 * TC0002: Login User with correct email and password.
	 * Verifies that a user can log in with valid credentials and then delete their account.
	 */
	@Test(testName = "TC0002", description = "Test Case 2: Login User with correct email and password")
	@Description("Verify that a user can log in with correct email and password, and then delete their account")
	@Severity(SeverityLevel.BLOCKER)
	public void testLoginUserWithCorrectCredentials() {
		// Get default test data from UserAccount model
		UserAccount user = UserAccount.getDefaultUser();

		// Generate dynamic test data for unique registration
		String timestamp = HDate.formatDate("yyyyMMddHHmmss");
		String username = "TestUser_" + timestamp;
		String email = "testuser_" + timestamp + "@gmail.com";

		// Precondition: Create a user account via API before test
		Logger.info("Precondition: Create a user account via API");
		user.setName(username);
		user.setEmail(email);
		Assert.assertTrue(AccountAPI.createAccount(user), "Failed to create user account via API!");

		// Track created account for cleanup via API if test fails afterward
		createdEmail = email;
		createdPassword = user.getPassword();

		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Click on 'Signup / Login' button");
		HomePage.clickSignupLogin();

		Logger.info("4. Verify 'Login to your account' is visible");
		Assert.assertTrue(SignupLoginPage.isLoginToYourAccountVisible(),
				"'Login to your account' title is not visible!");
		String loginTitleText = SignupLoginPage.getLoginToYourAccountText();
		Assert.assertEquals(loginTitleText.toLowerCase(), "login to your account",
				"Expected login title text 'Login to your account' but got: " + loginTitleText);

		Logger.info("5. Enter correct email address and password");
		SignupLoginPage.enterLoginEmailAndPassword(email, user.getPassword());

		Logger.info("6. Click 'login' button");
		SignupLoginPage.clickLogin();

		// Bypass potential Google vignette ad redirect
		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		Logger.info("7. Verify that 'Logged in as username' is visible");
		String loggedInText = HomePage.getLoggedInUserText();
		String expectedLoginStatus = "Logged in as " + username;
		Assert.assertTrue(loggedInText.contains(expectedLoginStatus),
				"Expected logged in status containing '" + expectedLoginStatus + "' but got: " + loggedInText);

		Logger.info("8. Click 'Delete Account' button");
		HomePage.clickDeleteAccount();

		Logger.info("9. Verify that 'ACCOUNT DELETED!' is visible");
		Assert.assertTrue(AccountDeletedPage.isAccountDeletedVisible(), "'ACCOUNT DELETED!' page is not visible!");
		String deletedText = AccountDeletedPage.getAccountDeletedText();
		Assert.assertEquals(deletedText.toLowerCase(), "account deleted!",
				"Expected success message 'ACCOUNT DELETED!' but got: " + deletedText);
		AccountDeletedPage.clickContinue();

		// Account already deleted via UI, clear tracking to avoid double delete
		createdEmail = null;
		createdPassword = null;
	}

	/**
	 * TC0003: Login User with incorrect email and password.
	 * Verifies that an error message is displayed when logging in with invalid credentials.
	 */
	@Test(testName = "TC0003", description = "Test Case 3: Login User with incorrect email and password")
	@Description("Verify that error 'Your email or password is incorrect!' is visible when login with incorrect credentials")
	@Severity(SeverityLevel.CRITICAL)
	public void testLoginUserWithIncorrectCredentials() {
		// Incorrect test data for negative login test
		String incorrectEmail = "invalid_" + HDate.formatDate("yyyyMMddHHmmss") + "@gmail.com";
		String incorrectPassword = "WrongPassword123!";

		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Click on 'Signup / Login' button");
		HomePage.clickSignupLogin();

		Logger.info("4. Verify 'Login to your account' is visible");
		Assert.assertTrue(SignupLoginPage.isLoginToYourAccountVisible(),
				"'Login to your account' title is not visible!");
		String loginTitleText = SignupLoginPage.getLoginToYourAccountText();
		Assert.assertEquals(loginTitleText.toLowerCase(), "login to your account",
				"Expected login title text 'Login to your account' but got: " + loginTitleText);

		Logger.info("5. Enter incorrect email address and password");
		SignupLoginPage.enterLoginEmailAndPassword(incorrectEmail, incorrectPassword);

		Logger.info("6. Click 'login' button");
		SignupLoginPage.clickLogin();

		Logger.info("7. Verify error 'Your email or password is incorrect!' is visible");
		Assert.assertTrue(SignupLoginPage.isLoginErrorVisible(),
				"Login error message 'Your email or password is incorrect!' is not visible!");
		String errorText = SignupLoginPage.getLoginErrorText();
		Assert.assertEquals(errorText, "Your email or password is incorrect!",
				"Expected error message 'Your email or password is incorrect!' but got: " + errorText);
	}

	/**
	 * TC0004: Logout User.
	 * Verifies that a logged-in user can log out and is navigated to the login page.
	 */
	@Test(testName = "TC0004", description = "Test Case 4: Logout User")
	@Description("Verify that a logged-in user can log out and is navigated to the login page")
	@Severity(SeverityLevel.CRITICAL)
	public void testLogoutUser() {
		// Get default test data from UserAccount model
		UserAccount user = UserAccount.getDefaultUser();

		// Generate dynamic test data for unique registration
		String timestamp = HDate.formatDate("yyyyMMddHHmmss");
		String username = "TestUser_" + timestamp;
		String email = "testuser_" + timestamp + "@gmail.com";

		// Precondition: Create a user account via API before test
		Logger.info("Precondition: Create a user account via API");
		user.setName(username);
		user.setEmail(email);
		Assert.assertTrue(AccountAPI.createAccount(user), "Failed to create user account via API!");

		// Track created account for cleanup via API if test fails afterward
		createdEmail = email;
		createdPassword = user.getPassword();

		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Click on 'Signup / Login' button");
		HomePage.clickSignupLogin();

		Logger.info("4. Verify 'Login to your account' is visible");
		Assert.assertTrue(SignupLoginPage.isLoginToYourAccountVisible(),
				"'Login to your account' title is not visible!");

		Logger.info("5. Enter correct email address and password");
		SignupLoginPage.enterLoginEmailAndPassword(email, user.getPassword());

		Logger.info("6. Click 'login' button");
		SignupLoginPage.clickLogin();

		// Bypass potential Google vignette ad redirect
		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		Logger.info("7. Verify that 'Logged in as username' is visible");
		String loggedInText = HomePage.getLoggedInUserText();
		String expectedLoginStatus = "Logged in as " + username;
		Assert.assertTrue(loggedInText.contains(expectedLoginStatus),
				"Expected logged in status containing '" + expectedLoginStatus + "' but got: " + loggedInText);

		Logger.info("8. Click 'Logout' button");
		HomePage.clickLogout();

		Logger.info("9. Verify that user is navigated to login page");
		Assert.assertTrue(SignupLoginPage.isLoginToYourAccountVisible(),
				"User was not navigated to login page after logout!");

		// Account still exists after logout, cleanup will be handled by @AfterMethod
	}
}
