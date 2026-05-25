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
	private static final Control btnLogout = new Control("a[href='/logout']");
	private static final Control lblLoggedInAs = new Control("//a[contains(., 'Logged in as')]");

	/**
	 * Checks if the Home Page is visible by verifying the logo element.
	 *
	 * @return true if the home page logo is displayed
	 */
	@Step("Check if the Home Page is visible")
	public static boolean isHomePageVisible() {
		Logger.info("Check if Home Page is visible");
		return imgLogo.isVisible();
	}

	/**
	 * Clicks the 'Signup / Login' button on the home page header.
	 */
	@Step("Click 'Signup / Login' button")
	public static void clickSignupLogin() {
		Logger.info("Click on 'Signup / Login' button");
		btnSignupLogin.get().click();
	}

	/**
	 * Clicks the 'Delete Account' button on the home page header.
	 */
	@Step("Click 'Delete Account' button")
	public static void clickDeleteAccount() {
		Logger.info("Click on 'Delete Account' button");
		btnDeleteAccount.get().click();
	}

	/**
	 * Clicks the 'Logout' button on the home page header.
	 */
	@Step("Click 'Logout' button")
	public static void clickLogout() {
		Logger.info("Click on 'Logout' button");
		btnLogout.get().click();
	}

	/**
	 * Gets the logged in user status text (e.g. "Logged in as username").
	 *
	 * @return the logged in user text, or empty string if not visible
	 */
	@Step("Get the logged in user status text")
	public static String getLoggedInUserText() {
		Logger.info("Get logged in user status text");
		if (lblLoggedInAs.isVisible()) {
			return lblLoggedInAs.get().getText();
		}
		return "";
	}
}
