package com.nhims.controls;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.nhims.browsers.BrowserExtensions;
import com.nhims.browsers.Navigation;
import com.nhims.constants.JavaScript;
import com.nhims.constants.TimeConst;
import com.nhims.utils.Convert;
import com.nhims.utils.HString;
import com.nhims.utils.Logger;

public class Control extends BaseControl {
	private String xpathOrCssSelector = null;
	private String timeout = null;
	private WebElement wElement = null;

	public Control(String xpathOrCssSelector) {
		// TODO Auto-generated constructor stub
		this.xpathOrCssSelector = xpathOrCssSelector;
		browser().switchTo().defaultContent();
		Logger.Info("-----(Switch) Default");
	}

	public Control(String xpathOrCssSelector, String iframe, String timeout) {
		// TODO Auto-generated constructor stub
		this.xpathOrCssSelector = xpathOrCssSelector;
		if (iframe != null) {
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

	protected WebElement get() {
		Logger.Info("(Find Element) Locator >> " + xpathOrCssSelector);
		WebElement element = null;
		if (timeout == null) {
			try {
				element = getElement(xpathOrCssSelector);
				Logger.Info("Element >> Success");
			} catch (Exception e) {
				// TODO: handle exception
				Logger.Error("Element >> Error");
				throw new RuntimeException("Can not find locator >> " + xpathOrCssSelector);
			}
		} else {
			for (int i = 0; i < Convert.stringToInt(timeout) - 1; i++) {
				if (i == 0) {
					if (isDisplay()) {
						element = getElement(xpathOrCssSelector);
						Logger.Info("Element >> Success");
						break;
					}
				} else {
					for (int j = 0; j < 9; j++) {
						if (j == 0) {
							waitByMiliSec(100);
						} else {
							if (isDisplay()) {
								element = getElement(xpathOrCssSelector);
								Logger.Info("Element >> Success");
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
			try {
				element = getElement(xpathOrCssSelector);
				Logger.Info("Element >> Success");
			} catch (Exception e) {
				// TODO: handle exception
				Logger.Error("Element >> Error");
				throw new RuntimeException("Can not find locator >> " + xpathOrCssSelector);
			}
		}
		focus(element);
		return element;
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

	/***
	 * Check the element is visible or not
	 * 
	 * @return true/false
	 */
	public boolean isVisible() {
		boolean flag = false;
		if(wElement == null) {
			wElement = get();
		}
		try {
			waitExplicit(TimeConst.SEC_NORMAL_WAIT).until(ExpectedConditions.visibilityOf(wElement));
			flag = true;
			Logger.Info("> E > is visible");
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			Logger.Info("> E > is not visible");
		}
		return flag;
	}

	/***
	 * Check the element is display in DOM
	 * 
	 * @return true/false
	 */
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

	/***
	 * Check the element focused or not
	 * 
	 * @return true/false
	 */
	public boolean isFocus() {
		boolean flag = false;
		if (wElement == null) {
			wElement = get();
		}
		if (browser().switchTo().activeElement().equals(wElement)) {
			flag = true;
			Logger.Info("> E > is focus");
		} else {
			Logger.Info("> E > is not focus");
		}
		return flag;
	}

	private boolean isFocus(WebElement element) {
		boolean flag = false;
		if (browser().switchTo().activeElement().equals(element)) {
			flag = true;
		}
		return flag;
	}

	/***
	 * Execute click on the element
	 */
	public void click() {
		String url = Convert.formatStringToUTF8(Navigation.getCurrentUrl());
		if (isVisible()) {
			try {
				wElement.click();
				Logger.Info("> E > Click");
				String current = Convert.formatStringToUTF8(Navigation.getCurrentUrl());
				if (!url.equals(current)) {
					BrowserExtensions.waitPageLoading();
				}
			} catch (Exception e) {
				// TODO: handle exception
				clickByJS();
				String current = Convert.formatStringToUTF8(Navigation.getCurrentUrl());
				if (!url.equals(current)) {
					BrowserExtensions.waitPageLoading();
				}
			}
		} else {
			clickByJS();
			String current = Convert.formatStringToUTF8(Navigation.getCurrentUrl());
			if (!url.equals(current)) {
				BrowserExtensions.waitPageLoading();
			}
		}
	}

	/***
	 * Execute click on the element by use JS
	 */
	public void clickByJS() {
		if (wElement == null) {
			wElement = get();
		}
		((JavascriptExecutor) browser()).executeScript(JavaScript.ACTION_CLICK, wElement);
		Logger.Info("> E > ClickJS");
	}

	/***
	 * Execute get text of the element
	 * 
	 * @return text of the element
	 */
	public String getText() {
		if (wElement == null) {
			wElement = get();
		}
		String text = wElement.getText().toString();
		if (text == null || text == "") {
			text = getAttr("innerHTML");
			if (text == null || text == "") {
				text = getAttr("textContent");
			}
		} else {
			Logger.Info("> E > The Text [" + text + "]");
		}
		return text;
	}

	/***
	 * Execute get the attribute of the element
	 * 
	 * @param attr : name of attribute
	 * @return text of attribute of the element
	 */
	public String getAttr(String attr) {
		if (wElement == null) {
			wElement = get();
		}
		String text = wElement.getAttribute(attr).toString();
		Logger.Info("> E > The Attribute [" + attr + "] [" + text + "]");
		return text;
	}

	/***
	 * Execute get value of input element
	 * 
	 * @return text of value of the input element
	 */
	public String getValue() {
		if (wElement == null) {
			wElement = get();
		}
		String text = wElement.getAttribute("value");
		if (text == null || text == "") {
			text = getAttr("innerHTML");
			if (text == null || text == "") {
				text = getAttr("textContent");
			}
		}
		return text;
	}
}
