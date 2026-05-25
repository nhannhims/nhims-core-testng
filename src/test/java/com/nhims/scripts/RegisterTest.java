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
import com.nhims.pages.HomePage;
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
 * Test class for User Registration feature.
 * Verifies end-to-end user registration, login verification, and account deletion.
 */
@Listeners(TestListener.class)
@Story("User Management")
public class RegisterTest {

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
	 * TC0001: Register User.
	 * Verifies that a user can register successfully, log in, and delete their account.
	 */
	@Test(testName = "TC0001", description = "Test Case 1: Register User")
	@Description("Verify that a user can register successfully, log in, and delete their account")
	@Severity(SeverityLevel.BLOCKER)
	public void testRegisterUser() {
		// Get default test data from UserAccount model
		UserAccount user = UserAccount.getDefaultUser();

		// Generate dynamic test data for unique registration
		String timestamp = HDate.formatDate("yyyyMMddHHmmss");
		String username = "TestUser_" + timestamp;
		String email = "testuser_" + timestamp + "@gmail.com";

		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Click on 'Signup / Login' button");
		HomePage.clickSignupLogin();

		Logger.info("4. Verify 'New User Signup!' is visible");
		Assert.assertTrue(SignupLoginPage.isNewUserSignupVisible(), "'New User Signup!' title is not visible!");
		String signupTitleText = SignupLoginPage.getNewUserSignupText();
		Assert.assertEquals(signupTitleText.toLowerCase(), "new user signup!",
				"Expected signup title text 'New User Signup!' but got: " + signupTitleText);

		Logger.info("5. Enter name and email address");
		SignupLoginPage.enterSignupNameAndEmail(username, email);

		Logger.info("6. Click 'Signup' button");
		SignupLoginPage.clickSignup();

		Logger.info("7. Verify that 'ENTER ACCOUNT INFORMATION' is visible");
		Assert.assertTrue(RegisterPage.isEnterAccountInfoVisible(), "'ENTER ACCOUNT INFORMATION' is not visible!");
		String registerTitleText = RegisterPage.getEnterAccountInfoText();
		Assert.assertEquals(registerTitleText.toLowerCase(), "enter account information",
				"Expected title text 'ENTER ACCOUNT INFORMATION' but got: " + registerTitleText);

		Logger.info("8. Fill details: Title, Name, Email, Password, Date of birth");
		// First verify prefilled Name and Email (which match the values from Step 6)
		String prefilledName = RegisterPage.getPrefilledName();
		String prefilledEmail = RegisterPage.getPrefilledEmail();
		Assert.assertEquals(prefilledName, username, "Prefilled name in registration form does not match!");
		Assert.assertEquals(prefilledEmail, email, "Prefilled email in registration form does not match!");
		// Fill Title, Password, Date of birth
		RegisterPage.fillAccountDetails(user.getTitle(), user.getPassword(), user.getBirthDay(), user.getBirthMonth(), user.getBirthYear());

		Logger.info("9. Select checkbox 'Sign up for our newsletter!'");
		RegisterPage.selectNewsletter();

		Logger.info("10. Select checkbox 'Receive special offers from our partners!'");
		RegisterPage.selectSpecialOffers();

		Logger.info("11. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number");
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

		Logger.info("12. Click 'Create Account button'");
		RegisterPage.clickCreateAccount();

		// Track created account for cleanup via API if test fails afterward
		createdEmail = email;
		createdPassword = user.getPassword();

		Logger.info("13. Verify that 'ACCOUNT CREATED!' is visible");
		Assert.assertTrue(AccountCreatedPage.isAccountCreatedVisible(), "'ACCOUNT CREATED!' page is not visible!");
		String createdText = AccountCreatedPage.getAccountCreatedText();
		Assert.assertEquals(createdText.toLowerCase(), "account created!",
				"Expected success message 'ACCOUNT CREATED!' but got: " + createdText);

		Logger.info("14. Click 'Continue' button");
		AccountCreatedPage.clickContinue();

		// Bypass potential Google vignette ad redirect
		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		Logger.info("15. Verify that 'Logged in as username' is visible");
		String loggedInText = HomePage.getLoggedInUserText();
		String expectedLoginStatus = "Logged in as " + username;
		Assert.assertTrue(loggedInText.contains(expectedLoginStatus),
				"Expected logged in status containing '" + expectedLoginStatus + "' but got: " + loggedInText);

		Logger.info("16. Click 'Delete Account' button");
		HomePage.clickDeleteAccount();

		Logger.info("17. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button");
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
	 * TC0005: Register User with existing email.
	 * Verifies that an error message is displayed when trying to register with an already registered email.
	 */
	@Test(testName = "TC0005", description = "Test Case 5: Register User with existing email")
	@Description("Verify error 'Email Address already exist!' is visible when registering with an already registered email")
	@Severity(SeverityLevel.CRITICAL)
	public void testRegisterUserWithExistingEmail() {
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

		Logger.info("4. Verify 'New User Signup!' is visible");
		Assert.assertTrue(SignupLoginPage.isNewUserSignupVisible(), "'New User Signup!' title is not visible!");
		String signupTitleText = SignupLoginPage.getNewUserSignupText();
		Assert.assertEquals(signupTitleText.toLowerCase(), "new user signup!",
				"Expected signup title text 'New User Signup!' but got: " + signupTitleText);

		Logger.info("5. Enter name and already registered email address");
		SignupLoginPage.enterSignupNameAndEmail(username, email);

		Logger.info("6. Click 'Signup' button");
		SignupLoginPage.clickSignup();

		Logger.info("7. Verify error 'Email Address already exist!' is visible");
		Assert.assertTrue(SignupLoginPage.isSignupErrorVisible(),
				"Signup error message 'Email Address already exist!' is not visible!");
		String errorText = SignupLoginPage.getSignupErrorText();
		Assert.assertEquals(errorText.toLowerCase(), "email address already exist!",
				"Expected error message 'Email Address already exist!' but got: " + errorText);

		// Account still exists, cleanup will be handled by @AfterMethod
	}
}
