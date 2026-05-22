package com.nhims.controls;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.nhims.browsers.Browsers;
import com.nhims.constants.JavaScript;
import com.nhims.constants.TimeConst;
import com.nhims.utils.Convert;
import com.nhims.utils.HString;
import com.nhims.utils.Logger;

public class Control extends BaseControl {
	private String xpathOrCssSelector = null;
	private String iframe = null;
	private String timeout = null;
	private WebElement element = null;

	public Control(String xpathOrCssSelector) {
		// TODO Auto-generated constructor stub
		this.xpathOrCssSelector = xpathOrCssSelector;
		if (iframe != null) {
			this.iframe = null;
			Browsers.browser().switchTo().defaultContent();
			Logger.info("-----(Switch) Default");
		}

	}

	public Control(String xpathOrCssSelector, String iframe, String timeout) {
		// TODO Auto-generated constructor stub
		this.xpathOrCssSelector = xpathOrCssSelector;
		if (iframe != null) {
			this.iframe = iframe;
			Browsers.browser().switchTo().frame(getElement(iframe));
			Logger.info("-----(Switch) IFrame");
		}
		this.timeout = timeout;
	}
	private Control(String xpathOrCssSelector, String iframe, String timeout, WebElement element) {
		this.xpathOrCssSelector = xpathOrCssSelector;
		this.iframe = iframe;
		this.timeout = timeout;
		this.element = element;
	}

	public Control setDynamicLocator(Object... values) {
		Logger.info("(Set Dynamic Value) >> " + xpathOrCssSelector);
		String formattedSelector = HString.replace(this.xpathOrCssSelector, values);
		return new Control(formattedSelector, this.iframe, this.timeout, this.element);
	}

	public String getLocator() {
		return xpathOrCssSelector;
	}

	private void find() {
		Logger.info("(Find Element) >> " + xpathOrCssSelector);
		if (timeout == null) {
			element = getElement(xpathOrCssSelector);
		} else {
			for (int i = 0; i < Convert.stringToInt(timeout) - 1; i++) {
				if (i == 0) {
					if (isDisplay()) {
						element = getElement(xpathOrCssSelector);
						break;
					}
				} else {
					for (int j = 0; j < 9; j++) {
						if (j == 0) {
							Browsers.waitByMiliSec(100);
						} else {
							if (isDisplay()) {
								element = getElement(xpathOrCssSelector);
								break;
							} else {
								Browsers.waitByMiliSec(100);
							}
						}
					}
				}

			}
		}
		if (element == null) {
			element = getElement(xpathOrCssSelector);
		}
		focus(element);
	}

	public Actions get() {
		find();
		Actions eAction = new Actions(element);
		return eAction;
	}

	private void focus(WebElement element) {
		useAction().moveToElement(element).perform();
		if (!isFocus(element)) {
			useAction().scrollToElement(element).perform();
			if (!isFocus(element)) {
				((JavascriptExecutor) Browsers.browser()).executeScript(JavaScript.SCROLL_TO_ELEMENT, element);
			}
		}
	}

	private boolean isFocus(WebElement element) {
		boolean flag = false;
		if (Browsers.browser().switchTo().activeElement().equals(element)) {
			flag = true;
		}
		return flag;
	}

	public boolean isVisible() {
		boolean flag = false;
		find();
		try {
			Browsers.waitExplicit(TimeConst.SEC_NORMAL_WAIT).until(ExpectedConditions.visibilityOf(element));
			flag = true;
			Logger.info("> E > is visible");
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			Logger.info("> E > is not visible");
		}
		return flag;
	}

	public boolean isDisplay() {
		boolean flag = false;
		if (countElement(xpathOrCssSelector) > 0) {
			flag = true;
			Logger.info("> E > is display");
		} else {
			Logger.info("> E > is not display");
		}
		return flag;
	}
}
