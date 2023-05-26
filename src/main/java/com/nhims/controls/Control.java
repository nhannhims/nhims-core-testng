package com.nhims.controls;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
			browser().switchTo().defaultContent();
			Logger.Info("-----(Switch) Default");
		}

	}

	public Control(String xpathOrCssSelector, String iframe, String timeout) {
		// TODO Auto-generated constructor stub
		this.xpathOrCssSelector = xpathOrCssSelector;
		if (iframe != null) {
			this.iframe = iframe;
			browser().switchTo().frame(getElement(iframe));
			Logger.Info("-----(Switch) IFrame");
		}
		this.timeout = timeout;
	}

	public Control setDynamicLocator(Object... values) {
		Logger.Info("(Set Dynamic Value) >> " + xpathOrCssSelector);
		this.xpathOrCssSelector = HString.replace(this.xpathOrCssSelector, values);
		return this;
	}

	public String getLocator() {
		return xpathOrCssSelector;
	}

	private void find() {
		Logger.Info("(Find Element) >> " + xpathOrCssSelector);
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
							waitByMiliSec(100);
						} else {
							if (isDisplay()) {
								element = getElement(xpathOrCssSelector);
								break;
							} else {
								waitByMiliSec(100);
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
				((JavascriptExecutor) browser()).executeScript(JavaScript.SCROLL_TO_ELEMENT, element);
			}
		}
	}

	private boolean isFocus(WebElement element) {
		boolean flag = false;
		if (browser().switchTo().activeElement().equals(element)) {
			flag = true;
		}
		return flag;
	}

	public boolean isVisible() {
		boolean flag = false;
		find();
		try {
			waitExplicit(TimeConst.SEC_NORMAL_WAIT).until(ExpectedConditions.visibilityOf(element));
			flag = true;
			Logger.Info("> E > is visible");
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			Logger.Info("> E > is not visible");
		}
		return flag;
	}

	public boolean isDisplay() {
		boolean flag = false;
		if (countElement(xpathOrCssSelector) > 0) {
			flag = true;
			Logger.Info("> E > is display");
		} else {
			Logger.Info("> E > is not display");
		}
		return flag;
	}
}
