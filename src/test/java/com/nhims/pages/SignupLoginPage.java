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

	// --- Signup form methods ---

	@Step("Check if 'New User Signup!' title is visible")
	public static boolean isNewUserSignupVisible() {
		Logger.info("Check if 'New User Signup!' title is visible");
		return lblNewUserSignup.isVisible();
	}

	@Step("Get the text of the 'New User Signup!' title")
	public static String getNewUserSignupText() {
		Logger.info("Get 'New User Signup!' text");
		if (lblNewUserSignup.isVisible()) {
			return lblNewUserSignup.get().getText();
		}
		return "";
	}

	@Step("Enter signup name '{name}' and email '{email}'")
	public static void enterSignupNameAndEmail(String name, String email) {
		Logger.info("Enter name: " + name + " and email: " + email);
		txtSignupName.get().type(name);
		txtSignupEmail.get().type(email);
	}

	@Step("Click the 'Signup' button")
	public static void clickSignup() {
		Logger.info("Click 'Signup' button");
		btnSignup.get().click();
	}

	// --- Login form methods ---

	@Step("Check if 'Login to your account' title is visible")
	public static boolean isLoginToYourAccountVisible() {
		Logger.info("Check if 'Login to your account' title is visible");
		return lblLoginToYourAccount.isVisible();
	}

	@Step("Get the text of the 'Login to your account' title")
	public static String getLoginToYourAccountText() {
		Logger.info("Get 'Login to your account' text");
		if (lblLoginToYourAccount.isVisible()) {
			return lblLoginToYourAccount.get().getText();
		}
		return "";
	}

	@Step("Enter login email '{email}' and password")
	public static void enterLoginEmailAndPassword(String email, String password) {
		Logger.info("Enter login email: " + email + " and password");
		txtLoginEmail.get().type(email);
		txtLoginPassword.get().type(password);
	}

	@Step("Click the 'Login' button")
	public static void clickLogin() {
		Logger.info("Click 'Login' button");
		btnLogin.get().click();
	}
}
