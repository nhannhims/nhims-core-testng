package com.nhims.browsers;

import com.nhims.utils.Convert;
import com.nhims.utils.Logger;

public class Navigation {
	public static void visitTo(String url) {
		BrowserExtensions.deleteAllCookies();
		Browsers.browser().navigate().to(url);
		Logger.Info("-----Open Web Appication [" + url + "]");
		BrowserExtensions.waitPageLoading();
	}

	public static void toBack() {
		Browsers.browser().navigate().back();
		Logger.Info("Browser navigate to [BACK]");
		BrowserExtensions.waitPageLoading();
	}

	public static void toNext() {
		Browsers.browser().navigate().forward();
		Logger.Info("Browser navigate to [NEXT]");
		BrowserExtensions.waitPageLoading();
	}

	public static void toReferesh() {
		Browsers.browser().navigate().refresh();
		Logger.Info("Browser navigate to [REFRESH]");
		BrowserExtensions.waitPageLoading();
	}

	public static String getTitle() {
		return Convert.formatStringToUTF8(Browsers.browser().getTitle());
	}

	public static String getCurrentUrl() {
		return Convert.formatStringToUTF8(Browsers.browser().getCurrentUrl());
	}
}
