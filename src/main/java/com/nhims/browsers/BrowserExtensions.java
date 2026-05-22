package com.nhims.browsers;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;

import com.nhims.constants.JavaScript;
import com.nhims.constants.TimeConst;
import com.nhims.constants.Constants.LOCATION;
import com.nhims.utils.Convert;
import com.nhims.utils.Logger;

public class BrowserExtensions {
	public static void waitPageLoading() {
		boolean flag = false;
		String currentUrl = Convert.formatStringToUTF8(Navigation.getCurrentUrl());
		for (int i = 0; i < TimeConst.SEC_SHORT_WAIT; i++) {
			String stage = pageLoadingStage();
			if (stage.equals("complete")) {
				flag = true;
				break;
			} else {
				Browsers.waitBySec(TimeConst.SEC_MINIMUM_WAIT);
			}
		}

		if (flag == true) {
			Logger.info("[" + currentUrl + "] loading is successfull");
		} else {
			Logger.warning("[" + currentUrl + "] loading has problem, time wait is [" + TimeConst.SEC_SHORT_WAIT + "]");
		}
	}

	private static String pageLoadingStage() {
		return ((JavascriptExecutor) Browsers.browser()).executeScript(JavaScript.PAGE_LOADING).toString();
	}

	public static void deleteAllCookies() {
		Browsers.browser().manage().deleteAllCookies();
		Logger.info("-----Delete All Cookies");
	}

	public static void openNewTab() {
		((JavascriptExecutor) Browsers.browser()).executeScript("window.open();");
		Logger.info("-----Open new Tab on browser");
	}

	public static ArrayList<String> getAllTabs() {
		ArrayList<String> tabs = new ArrayList<>(Browsers.browser().getWindowHandles());
		return tabs;
	}

	public static void moveToNewTab() {
		ArrayList<String> tabs = getAllTabs();
		Browsers.browser().switchTo().window(tabs.get(tabs.size() - 1));
		Logger.info("-----Move to new Tab on browser");
	}

	public static void moveToDefaultTab() {
		ArrayList<String> tabs = getAllTabs();
		Browsers.browser().switchTo().window(tabs.get(0));
		Logger.info("-----Move to Default Tab on browser");
	}

	public static void moveToTabWithIndex(int idx) {
		ArrayList<String> tabs = getAllTabs();
		Browsers.browser().switchTo().window(tabs.get(idx));
		Logger.info("-----Move to Tab has index [" + idx + "]");
	}

	public static void moveTo(Object location) {
		if (location.equals(LOCATION.TOP)) {
			((JavascriptExecutor) Browsers.browser()).executeScript(JavaScript.SCROLL_TO_TOP);
		}

		if (location.equals(LOCATION.BOTTOM)) {
			((JavascriptExecutor) Browsers.browser()).executeScript(JavaScript.SCROLL_TO_BOTTOM);
		}
	}
}
