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

		Logger.info("1. Launch browser & 2. Navigate to url 'http://automationexercise.com'");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		Logger.info("3. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("4. Click on 'Signup / Login' button");
		HomePage.clickSignupLogin();

		Logger.info("5. Verify 'Login to your account' is visible");
		Assert.assertTrue(SignupLoginPage.isLoginToYourAccountVisible(),
				"'Login to your account' title is not visible!");
		String loginTitleText = SignupLoginPage.getLoginToYourAccountText();
		Assert.assertEquals(loginTitleText.toLowerCase(), "login to your account",
				"Expected login title text 'Login to your account' but got: " + loginTitleText);

		Logger.info("6. Enter correct email address and password");
		SignupLoginPage.enterLoginEmailAndPassword(email, user.getPassword());

		Logger.info("7. Click 'login' button");
		SignupLoginPage.clickLogin();

		// Bypass potential Google vignette ad redirect
		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		Logger.info("8. Verify that 'Logged in as username' is visible");
		String loggedInText = HomePage.getLoggedInUserText();
		String expectedLoginStatus = "Logged in as " + username;
		Assert.assertTrue(loggedInText.contains(expectedLoginStatus),
				"Expected logged in status containing '" + expectedLoginStatus + "' but got: " + loggedInText);

		Logger.info("9. Click 'Delete Account' button");
		HomePage.clickDeleteAccount();

		Logger.info("10. Verify that 'ACCOUNT DELETED!' is visible");
		Assert.assertTrue(AccountDeletedPage.isAccountDeletedVisible(), "'ACCOUNT DELETED!' page is not visible!");
		String deletedText = AccountDeletedPage.getAccountDeletedText();
		Assert.assertEquals(deletedText.toLowerCase(), "account deleted!",
				"Expected success message 'ACCOUNT DELETED!' but got: " + deletedText);
		AccountDeletedPage.clickContinue();

		// Account already deleted via UI, clear tracking to avoid double delete
		createdEmail = null;
		createdPassword = null;
	}
}
