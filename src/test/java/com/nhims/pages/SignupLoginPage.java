package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Signup / Login page.
 */
public class SignupLoginPage extends BasePage {
	// Signup form locators
	private static final Control lblNewUserSignup = new Control("div.signup-form h2");
	private static final Control txtSignupName = new Control("input[data-qa='signup-name']");
	private static final Control txtSignupEmail = new Control("input[data-qa='signup-email']");
	private static final Control btnSignup = new Control("button[data-qa='signup-button']");

	// Login form locators
	private static final Control lblLoginToYourAccount = new Control("div.login-form h2");
	private static final Control txtLoginEmail = new Control("input[data-qa='login-email']");
	private static final Control txtLoginPassword = new Control("input[data-qa='login-password']");
	private static final Control btnLogin = new Control("button[data-qa='login-button']");
	private static final Control lblLoginError = new Control(
			"//div[contains(@class,'login-form')]//p[contains(text(),'Your email or password is incorrect!')]");

	// --- Signup form methods ---

	/**
	 * Checks if the 'New User Signup!' title is visible.
	 *
	 * @return true if the title is displayed
	 */
	@Step("Check if 'New User Signup!' title is visible")
	public static boolean isNewUserSignupVisible() {
		Logger.info("Check if 'New User Signup!' title is visible");
		return lblNewUserSignup.isVisible();
	}

	/**
	 * Gets the text of the 'New User Signup!' title.
	 *
	 * @return the title text, or empty string if not visible
	 */
	@Step("Get the text of the 'New User Signup!' title")
	public static String getNewUserSignupText() {
		Logger.info("Get 'New User Signup!' text");
		if (lblNewUserSignup.isVisible()) {
			return lblNewUserSignup.get().getText();
		}
		return "";
	}

	/**
	 * Enters the signup name and email into the signup form.
	 *
	 * @param name  the user's full name
	 * @param email the user's email address
	 */
	@Step("Enter signup name '{name}' and email '{email}'")
	public static void enterSignupNameAndEmail(String name, String email) {
		Logger.info("Enter name: " + name + " and email: " + email);
		txtSignupName.get().type(name);
		txtSignupEmail.get().type(email);
	}

	/**
	 * Clicks the 'Signup' button to submit the signup form.
	 */
	@Step("Click the 'Signup' button")
	public static void clickSignup() {
		Logger.info("Click 'Signup' button");
		btnSignup.get().click();
	}

	// --- Login form methods ---

	/**
	 * Checks if the 'Login to your account' title is visible.
	 *
	 * @return true if the title is displayed
	 */
	@Step("Check if 'Login to your account' title is visible")
	public static boolean isLoginToYourAccountVisible() {
		Logger.info("Check if 'Login to your account' title is visible");
		return lblLoginToYourAccount.isVisible();
	}

	/**
	 * Gets the text of the 'Login to your account' title.
	 *
	 * @return the title text, or empty string if not visible
	 */
	@Step("Get the text of the 'Login to your account' title")
	public static String getLoginToYourAccountText() {
		Logger.info("Get 'Login to your account' text");
		if (lblLoginToYourAccount.isVisible()) {
			return lblLoginToYourAccount.get().getText();
		}
		return "";
	}

	/**
	 * Enters the login email and password into the login form.
	 *
	 * @param email    the user's email address
	 * @param password the user's password
	 */
	@Step("Enter login email '{email}' and password")
	public static void enterLoginEmailAndPassword(String email, String password) {
		Logger.info("Enter login email: " + email + " and password");
		txtLoginEmail.get().type(email);
		txtLoginPassword.get().type(password);
	}

	/**
	 * Clicks the 'Login' button to submit the login form.
	 */
	@Step("Click the 'Login' button")
	public static void clickLogin() {
		Logger.info("Click 'Login' button");
		btnLogin.get().click();
	}

	/**
	 * Checks if the login error message is visible on the page.
	 *
	 * @return true if the login error message is displayed
	 */
	@Step("Check if login error message is visible")
	public static boolean isLoginErrorVisible() {
		Logger.info("Check if login error message is visible");
		return lblLoginError.isVisible();
	}

	/**
	 * Gets the login error message text.
	 *
	 * @return the error message text, or empty string if not visible
	 */
	@Step("Get the login error message text")
	public static String getLoginErrorText() {
		Logger.info("Get login error message text");
		if (lblLoginError.isVisible()) {
			return lblLoginError.get().getText();
		}
		return "";
	}
}
