package com.nhims.controls;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nhims.browsers.Browsers;
import com.nhims.constants.JavaScript;
import com.nhims.constants.TimeConst;
import com.nhims.utils.Convert;
import com.nhims.utils.HString;
import com.nhims.utils.Logger;

public class Control extends BaseControl {
	private final String xpathOrCssSelector;
	private final String iframe;
	private final String timeout;

	public Control(String xpathOrCssSelector) {
		this.xpathOrCssSelector = xpathOrCssSelector;
		this.iframe = null;
		this.timeout = null;
	}

	public Control(String xpathOrCssSelector, String iframe, String timeout) {
		this.xpathOrCssSelector = xpathOrCssSelector;
		this.iframe = iframe;
		this.timeout = timeout;
	}

	public Control setDynamicLocator(Object... values) {
		Logger.info("(Set Dynamic Value) >> " + xpathOrCssSelector);
		String formattedSelector = HString.format(this.xpathOrCssSelector, values);
		return new Control(formattedSelector, this.iframe, this.timeout);
	}

	public String getLocator() {
		return xpathOrCssSelector;
	}

	private WebElement find() {
		Logger.info("(Find Element) >> " + xpathOrCssSelector);
		int sec = (timeout == null) ? TimeConst.SEC_NORMAL_WAIT : Convert.stringToInt(timeout);
		WebDriverWait wait = new WebDriverWait(Browsers.browser(), Duration.ofSeconds(sec));

		if (iframe != null) {
			By frameLocator = iframe.startsWith("/") || iframe.startsWith("(") 
					? By.xpath(iframe) 
					: By.cssSelector(iframe);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		} else {
			Browsers.browser().switchTo().defaultContent();
		}

		By elementLocator = xpathOrCssSelector.startsWith("/") || xpathOrCssSelector.startsWith("(")
				? By.xpath(xpathOrCssSelector)
				: By.cssSelector(xpathOrCssSelector);

		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
		focus(el);
		return el;
	}

	public Actions get() {
		WebElement el = find();
		return new Actions(el);
	}

	private void focus(WebElement el) {
		useAction().moveToElement(el).perform();
		if (!isFocus(el)) {
			useAction().scrollToElement(el).perform();
			if (!isFocus(el)) {
				((JavascriptExecutor) Browsers.browser()).executeScript(JavaScript.SCROLL_TO_ELEMENT, el);
			}
		}
	}

	private boolean isFocus(WebElement el) {
		boolean flag = false;
		try {
			if (Browsers.browser().switchTo().activeElement().equals(el)) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean isVisible() {
		try {
			WebElement el = find();
			return el.isDisplayed();
		} catch (Exception e) {
			Logger.info("> E > is not visible: " + e.getMessage());
			return false;
		}
	}

	public boolean isDisplay() {
		boolean flag = countElement(xpathOrCssSelector) > 0;
		if (flag) {
			Logger.info("> E > is display");
		} else {
			Logger.info("> E > is not display");
		}
		return flag;
	}
}
