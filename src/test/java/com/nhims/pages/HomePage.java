package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Home page.
 */
public class HomePage extends BasePage {
	private static final Control imgLogo = new Control("div.logo");
	private static final Control btnSignupLogin = new Control("a[href='/login']");
	private static final Control btnDeleteAccount = new Control("a[href='/delete_account']");
	private static final Control lblLoggedInAs = new Control("//a[contains(., 'Logged in as')]");

	@Step("Check if the Home Page is visible")
	public static boolean isHomePageVisible() {
		Logger.info("Check if Home Page is visible");
		return imgLogo.isVisible();
	}

	@Step("Click 'Signup / Login' button")
	public static void clickSignupLogin() {
		Logger.info("Click on 'Signup / Login' button");
		btnSignupLogin.get().click();
	}

	@Step("Click 'Delete Account' button")
	public static void clickDeleteAccount() {
		Logger.info("Click on 'Delete Account' button");
		btnDeleteAccount.get().click();
	}

	@Step("Get the logged in user status text")
	public static String getLoggedInUserText() {
		Logger.info("Get logged in user status text");
		if (lblLoggedInAs.isVisible()) {
			return lblLoggedInAs.get().getText();
		}
		return "";
	}
}
