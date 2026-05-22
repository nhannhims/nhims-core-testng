package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Signup / Login page.
 */
public class SignupLoginPage extends BasePage {
	private static final Control lblNewUserSignup = new Control("div.signup-form h2");
	private static final Control txtSignupName = new Control("input[data-qa='signup-name']");
	private static final Control txtSignupEmail = new Control("input[data-qa='signup-email']");
	private static final Control btnSignup = new Control("button[data-qa='signup-button']");

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
}
