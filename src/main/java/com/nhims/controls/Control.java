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

	public Control() {
		// TODO Auto-generated constructor stub
	}

	public Control(String xpathOrCssSelector) {
		// TODO Auto-generated constructor stub
		this.xpathOrCssSelector = xpathOrCssSelector;
		if (iframe == null) {
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
		Control control = new Control();
		control.xpathOrCssSelector = HString.replace(this.xpathOrCssSelector, values);
		return control;
	}

	public String getLocator() {
		return xpathOrCssSelector;
	}

	protected WebElement get() {
		Logger.Info("-----(Find Element) Locator >> " + xpathOrCssSelector);
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
			((JavascriptExecutor) browser()).executeScript(JavaScript.SCROLL_TO_ELEMENT, element);
		}
	}

	/***
	 * Check the element is visible or not
	 * 
	 * @return true/false
	 */
	public boolean isVisible() {
		boolean flag = false;
		try {
			waitExplicit(TimeConst.SEC_NORMAL_WAIT).until(ExpectedConditions.visibilityOf(get()));
			flag = true;
			Logger.Info("> is visible");
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			Logger.Info("> is not visible");
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
			Logger.Info("> is display");
		} else {
			Logger.Info("> is not display");
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
		if (browser().switchTo().activeElement().equals(get())) {
			flag = true;
			Logger.Info("> is focus");
		} else {
			Logger.Info("> is not focus");
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
		if (isVisible()) {
			try {
				get().click();
				Logger.Info("> E > Click");
			} catch (Exception e) {
				// TODO: handle exception
				clickByJS();
			}
		} else {
			clickByJS();
		}
	}

	/***
	 * Execute click on the element by use JS
	 */
	public void clickByJS() {
		((JavascriptExecutor) browser()).executeScript(JavaScript.ACTION_CLICK, get());
		Logger.Info("> E > ClickJS");
	}

	/***
	 * Execute get text of the element
	 * 
	 * @return text of the element
	 */
	public String getText() {
		String text = get().getText().toString();
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
		String text = get().getAttribute(attr).toString();
		Logger.Info("> E > The Attribute [" + attr + "] [" + text + "]");
		return text;
	}

	/***
	 * Execute get value of input element
	 * 
	 * @return text of value of the input element
	 */
	public String getValue() {
		String text = get().getAttribute("value");
		if (text == null || text == "") {
			text = getAttr("innerHTML");
			if (text == null || text == "") {
				text = getAttr("textContent");
			}
		}
		return text;
	}
}
