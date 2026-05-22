package com.nhims.browsers;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;

import com.nhims.constants.JavaScript;
import com.nhims.constants.TimeConst;
import com.nhims.constants.Constants.LOCATION;
import com.nhims.utils.Convert;
import com.nhims.utils.Logger;

public class BrowserExtensions {
	/**
	 * Waits for the page to finish loading by checking the document readystate.
	 * Times out after SEC_PAGE_LOAD_WAIT seconds if loading takes too long.
	 */
	public static void waitPageLoading() {
		String currentUrl = Convert.formatStringToUTF8(Navigation.getCurrentUrl());
		try {
			Browsers.waitExplicit(TimeConst.SEC_PAGE_LOAD_WAIT)
					.until(driver -> pageLoadingStage().equals("complete"));
			Logger.info("[" + currentUrl + "] loading is successful");
		} catch (Exception e) {
			Logger.warning("[" + currentUrl + "] loading has problem, time wait is [" + TimeConst.SEC_PAGE_LOAD_WAIT + "]");
		}
	}

	/**
	 * Executes a JavaScript command to retrieve the current document readyState.
	 *
	 * @return the readyState string (e.g. "complete", "interactive", "loading")
	 */
	private static String pageLoadingStage() {
		return ((JavascriptExecutor) Browsers.browser()).executeScript(JavaScript.PAGE_LOADING).toString();
	}

	/**
	 * Deletes all cookies from the current browser session.
	 */
	public static void deleteAllCookies() {
		Browsers.browser().manage().deleteAllCookies();
		Logger.info("-----Delete All Cookies");
	}

	/**
	 * Opens a new empty tab in the browser using JavaScript.
	 */
	public static void openNewTab() {
		((JavascriptExecutor) Browsers.browser()).executeScript("window.open();");
		Logger.info("-----Open new Tab on browser");
	}

	/**
	 * Gets a list of all open window handles/tabs.
	 *
	 * @return an ArrayList of tab window handle strings
	 */
	public static ArrayList<String> getAllTabs() {
		ArrayList<String> tabs = new ArrayList<>(Browsers.browser().getWindowHandles());
		return tabs;
	}

	/**
	 * Switches the browser focus to the most recently opened tab.
	 */
	public static void moveToNewTab() {
		ArrayList<String> tabs = getAllTabs();
		Browsers.browser().switchTo().window(tabs.get(tabs.size() - 1));
		Logger.info("-----Move to new Tab on browser");
	}

	/**
	 * Switches the browser focus back to the default (first) tab.
	 */
	public static void moveToDefaultTab() {
		ArrayList<String> tabs = getAllTabs();
		Browsers.browser().switchTo().window(tabs.get(0));
		Logger.info("-----Move to Default Tab on browser");
	}

	/**
	 * Switches the browser focus to the tab at the specified index.
	 *
	 * @param idx the index of the tab to switch to
	 */
	public static void moveToTabWithIndex(int idx) {
		ArrayList<String> tabs = getAllTabs();
		Browsers.browser().switchTo().window(tabs.get(idx));
		Logger.info("-----Move to Tab has index [" + idx + "]");
	}

	/**
	 * Scrolls the page window to either the top or bottom of the page.
	 *
	 * @param location target location, must be Constants.LOCATION.TOP or Constants.LOCATION.BOTTOM
	 */
	public static void moveTo(Object location) {
		if (location.equals(LOCATION.TOP)) {
			((JavascriptExecutor) Browsers.browser()).executeScript(JavaScript.SCROLL_TO_TOP);
		}

		if (location.equals(LOCATION.BOTTOM)) {
			((JavascriptExecutor) Browsers.browser()).executeScript(JavaScript.SCROLL_TO_BOTTOM);
		}
	}
}
