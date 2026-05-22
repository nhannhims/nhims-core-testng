package com.nhims.pages;

import com.nhims.controls.Control;

/**
 * General page — common elements and actions shared across multiple pages
 * (e.g. navigation bar, global search).
 */
public class GeneralPage extends BasePage {
	private static final Control txtSearch = new Control("//input[contains(@class,'js-searchKeywords')]");
	private static final Control navMainMenu = new Control("//*[contains(@class,'header')]//a[text()='%s']");
	private static final Control navChildMenu = new Control("//*[contains(@class,'header')]//a[text()='%s']/..//li//*[contains(text(),'%s')]");

	public static void executeSearchProduct(String prodName) {
		txtSearch.get().type(prodName).then().enter();
	}

	public static void selectMenuOnNavigationBar(Object menuName) {
		navMainMenu.setDynamicLocator(menuName).get().click();
	}

	public static void selectChildMenuOnNavigationBar(Object mainMenu, Object childMenu) {
		navMainMenu.setDynamicLocator(mainMenu).get();
		navChildMenu.setDynamicLocator(mainMenu, childMenu).get().click();
	}
}
