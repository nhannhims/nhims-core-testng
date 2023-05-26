package com.nhims.pages;

import com.nhims.browsers.Browsers;
import com.nhims.controls.Control;

public class GenaralPage extends Browsers {
	private static Control txtSearch = new Control("//input[@id='js-searchKeywords']");
	private static Control navMainMenu = new Control("//div[@class='header']//a[text()='%s']");
	private static Control navChildMenu = new Control("//div[@class='header']//a[text()='%s']/..//li//span[text()='%s']");

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
