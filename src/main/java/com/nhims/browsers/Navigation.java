package com.nhims.browsers;

import com.nhims.utils.Convert;
import com.nhims.utils.Logger;

public class Navigation {
	/**
	 * Navigates the browser to the specified URL.
	 * Deletes cookies first and waits for the page to finish loading.
	 *
	 * @param url the destination URL
	 */
	public static void visitTo(String url) {
		BrowserExtensions.deleteAllCookies();
		Browsers.browser().navigate().to(url);
		Logger.info("-----Open Web Appication [" + url + "]");
		BrowserExtensions.waitPageLoading();
	}

	/**
	 * Navigates the browser to the specified URL without deleting cookies first.
	 *
	 * @param url the destination URL
	 */
	public static void navigateTo(String url) {
		Browsers.browser().navigate().to(url);
		Logger.info("-----Navigate to [" + url + "]");
		BrowserExtensions.waitPageLoading();
	}


	/**
	 * Simulates clicking the browser back button.
	 */
	public static void toBack() {
		Browsers.browser().navigate().back();
		Logger.info("Browser navigate to [BACK]");
		BrowserExtensions.waitPageLoading();
	}

	/**
	 * Simulates clicking the browser forward button.
	 */
	public static void toNext() {
		Browsers.browser().navigate().forward();
		Logger.info("Browser navigate to [NEXT]");
		BrowserExtensions.waitPageLoading();
	}

	/**
	 * Reloads the current page.
	 */
	public static void toRefresh() {
		Browsers.browser().navigate().refresh();
		Logger.info("Browser navigate to [REFRESH]");
		BrowserExtensions.waitPageLoading();
	}

	/**
	 * Gets the text title of the current browser tab.
	 *
	 * @return the page title formatted in UTF-8
	 */
	public static String getTitle() {
		return Convert.formatStringToUTF8(Browsers.browser().getTitle());
	}

	/**
	 * Gets the current active URL of the browser.
	 *
	 * @return the current URL formatted in UTF-8
	 */
	public static String getCurrentUrl() {
		return Convert.formatStringToUTF8(Browsers.browser().getCurrentUrl());
	}
}
