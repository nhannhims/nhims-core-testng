package com.nhims.pages;

import com.nhims.browsers.BrowserExtensions;
import com.nhims.browsers.Navigation;
import com.nhims.utils.Logger;

/**
 * Base class for all Page Objects.
 * Provides common utility methods available to all pages.
 * Keeps page classes DRY by centralizing shared browser interactions.
 */
public abstract class BasePage {

	/**
	 * Navigates the browser to the given URL and waits for the page to load.
	 *
	 * @param url the full URL to navigate to
	 */
	protected static void goTo(String url) {
		Navigation.visitTo(url);
	}

	/**
	 * Waits until the current page finishes loading (document.readyState == 'complete').
	 */
	protected static void waitForLoad() {
		BrowserExtensions.waitPageLoading();
	}

	/**
	 * Logs an informational verification message.
	 *
	 * @param label    the label/description of the field being verified
	 * @param actual   the actual value observed
	 * @param expected the expected value or condition
	 */
	protected static void logVerify(String label, String actual, String expected) {
		Logger.info("[Verify] " + label + " -> Actual [" + actual + "] Expected [" + expected + "]");
	}
}
