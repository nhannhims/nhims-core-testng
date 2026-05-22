package com.nhims.scripts;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nhims.browsers.Navigation;
import com.nhims.constants.Configs.EnvironmentConfig;
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

@Listeners(TestListener.class)
@Story("User Management")
public class RegisterTest {

	// Configurable Test Data
	private static final String TITLE = "Mr";
	private static final String PASSWORD = "SecurePassword123";
	private static final String BIRTH_DAY = "20";
	private static final String BIRTH_MONTH = "May";
	private static final String BIRTH_YEAR = "1995";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";
	private static final String COMPANY = "Test Company";
	private static final String ADDRESS_1 = "123 Main Street";
	private static final String ADDRESS_2 = "Suite 450";
	private static final String COUNTRY = "United States";
	private static final String STATE = "New York";
	private static final String CITY = "New York City";
	private static final String ZIPCODE = "10001";
	private static final String MOBILE_NUMBER = "1234567890";

	@Test(testName = "TC0001", description = "Test Case 1: Register User")
	@Description("Verify that a user can register successfully, log in, and delete their account")
	@Severity(SeverityLevel.BLOCKER)
	public void testRegisterUser() {
		// Generate dynamic test data for unique registration
		String timestamp = HDate.formatDate("yyyyMMddHHmmss");
		String username = "TestUser_" + timestamp;
		String email = "testuser_" + timestamp + "@gmail.com";

		// 1. Launch browser & 2. Navigate to url 'http://automationexercise.com'
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		// 3. Verify that home page is visible successfully
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		// 4. Click on 'Signup / Login' button
		HomePage.clickSignupLogin();

		// 5. Verify 'New User Signup!' is visible
		Assert.assertTrue(SignupLoginPage.isNewUserSignupVisible(), "'New User Signup!' title is not visible!");
		String signupTitleText = SignupLoginPage.getNewUserSignupText();
		Assert.assertEquals(signupTitleText.toLowerCase(), "new user signup!",
				"Expected signup title text 'New User Signup!' but got: " + signupTitleText);

		// 6. Enter name and email address
		SignupLoginPage.enterSignupNameAndEmail(username, email);

		// 7. Click 'Signup' button
		SignupLoginPage.clickSignup();

		// 8. Verify that 'ENTER ACCOUNT INFORMATION' is visible
		Assert.assertTrue(RegisterPage.isEnterAccountInfoVisible(), "'ENTER ACCOUNT INFORMATION' is not visible!");
		String registerTitleText = RegisterPage.getEnterAccountInfoText();
		Assert.assertEquals(registerTitleText.toLowerCase(), "enter account information",
				"Expected title text 'ENTER ACCOUNT INFORMATION' but got: " + registerTitleText);

		// 9. Fill details: Title, Name, Email, Password, Date of birth
		// First verify prefilled Name and Email (which match the values from Step 6)
		String prefilledName = RegisterPage.getPrefilledName();
		String prefilledEmail = RegisterPage.getPrefilledEmail();
		Assert.assertEquals(prefilledName, username, "Prefilled name in registration form does not match!");
		Assert.assertEquals(prefilledEmail, email, "Prefilled email in registration form does not match!");
		// Fill Title, Password, Date of birth
		RegisterPage.fillAccountDetails(TITLE, PASSWORD, BIRTH_DAY, BIRTH_MONTH, BIRTH_YEAR);

		// 10. Select checkbox 'Sign up for our newsletter!'
		RegisterPage.selectNewsletter();

		// 11. Select checkbox 'Receive special offers from our partners!'
		RegisterPage.selectSpecialOffers();

		// 12. Fill details: First name, Last name, Company, Address, Address2, Country,
		// State, City, Zipcode, Mobile Number
		RegisterPage.fillAddressDetails(
				FIRST_NAME,
				LAST_NAME,
				COMPANY,
				ADDRESS_1,
				ADDRESS_2,
				COUNTRY,
				STATE,
				CITY,
				ZIPCODE,
				MOBILE_NUMBER);

		// 13. Click 'Create Account button'
		RegisterPage.clickCreateAccount();

		// 14. Verify that 'ACCOUNT CREATED!' is visible
		Assert.assertTrue(AccountCreatedPage.isAccountCreatedVisible(), "'ACCOUNT CREATED!' page is not visible!");
		String createdText = AccountCreatedPage.getAccountCreatedText();
		Assert.assertEquals(createdText.toLowerCase(), "account created!",
				"Expected success message 'ACCOUNT CREATED!' but got: " + createdText);

		// 15. Click 'Continue' button
		AccountCreatedPage.clickContinue();

		// Bypass potential Google vignette ad redirect
		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		// 16. Verify that 'Logged in as username' is visible
		String loggedInText = HomePage.getLoggedInUserText();
		String expectedLoginStatus = "Logged in as " + username;
		Assert.assertTrue(loggedInText.contains(expectedLoginStatus),
				"Expected logged in status containing '" + expectedLoginStatus + "' but got: " + loggedInText);

		// 17. Click 'Delete Account' button
		HomePage.clickDeleteAccount();

		// 18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
		Assert.assertTrue(AccountDeletedPage.isAccountDeletedVisible(), "'ACCOUNT DELETED!' page is not visible!");
		String deletedText = AccountDeletedPage.getAccountDeletedText();
		Assert.assertEquals(deletedText.toLowerCase(), "account deleted!",
				"Expected success message 'ACCOUNT DELETED!' but got: " + deletedText);
		AccountDeletedPage.clickContinue();
	}
}
