package com.nhims.controls;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
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

	/**
	 * Constructor with element locator.
	 *
	 * @param xpathOrCssSelector the element locator string (XPath or CSS selector)
	 */
	public Control(String xpathOrCssSelector) {
		this.xpathOrCssSelector = xpathOrCssSelector;
		this.iframe = null;
		this.timeout = null;
	}

	/**
	 * Constructor with element locator, iframe locator, and timeout override.
	 *
	 * @param xpathOrCssSelector the element locator string
	 * @param iframe             the iframe locator string
	 * @param timeout            the wait timeout string in seconds
	 */
	public Control(String xpathOrCssSelector, String iframe, String timeout) {
		this.xpathOrCssSelector = xpathOrCssSelector;
		this.iframe = iframe;
		this.timeout = timeout;
	}

	/**
	 * Formats a dynamic locator string by replacing placeholders with values.
	 *
	 * @param values replacement values for formatting
	 * @return a new Control instance with formatted locator
	 */
	public Control setDynamicLocator(Object... values) {
		Logger.info("(Set Dynamic Value) >> " + xpathOrCssSelector);
		String formattedSelector = HString.format(this.xpathOrCssSelector, values);
		return new Control(formattedSelector, this.iframe, this.timeout);
	}

	/**
	 * Gets the current raw locator string.
	 *
	 * @return the locator string
	 */
	public String getLocator() {
		return xpathOrCssSelector;
	}

	/**
	 * Finds the element on the page, switching to iframe if specified, and waiting for visibility.
	 * Includes retry mechanism for StaleElementReferenceException and transient DOM changes.
	 *
	 * @return the located WebElement
	 */
	private WebElement find() {
		int attempt = 0;
		while (true) {
			try {
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
			} catch (StaleElementReferenceException e) {
				attempt++;
				if (attempt > TimeConst.MAX_RETRY) {
					Logger.error("(Retry Exhausted) StaleElementReferenceException for: " + xpathOrCssSelector);
					throw e;
				}
				Logger.warning("(Retry " + attempt + "/" + TimeConst.MAX_RETRY + ") StaleElementReferenceException >> " + xpathOrCssSelector);
			}
		}
	}

	/**
	 * Finds the element and returns an Actions wrapper for interactive commands.
	 *
	 * @return an Actions wrapper for the element
	 */
	public Actions get() {
		WebElement el = find();
		return new Actions(el, this);
	}

	/**
	 * Re-finds the element (used internally by Actions for retry on stale element).
	 *
	 * @return the re-located WebElement
	 */
	WebElement reFind() {
		return find();
	}

	/**
	 * Focuses the element by moving the mouse cursor to it or scrolling it into view.
	 *
	 * @param el the element to focus
	 */
	private void focus(WebElement el) {
		try {
			useAction().moveToElement(el).perform();
			if (!isFocus(el)) {
				useAction().scrollToElement(el).perform();
				if (!isFocus(el)) {
					((JavascriptExecutor) Browsers.browser()).executeScript(JavaScript.SCROLL_TO_ELEMENT, el);
				}
			}
		} catch (Exception e) {
			Logger.warning("Unable to focus element: " + e.getMessage());
		}
	}

	/**
	 * Checks if the element is currently the active (focused) element in the browser.
	 *
	 * @param el the element to check
	 * @return true if focused, false otherwise
	 */
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

	/**
	 * Checks if the element is visible on the page (displays and is searchable).
	 *
	 * @return true if visible, false otherwise
	 */
	public boolean isVisible() {
		try {
			WebElement el = find();
			return el.isDisplayed();
		} catch (Exception e) {
			Logger.info("> E > is not visible: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Checks if the element exists in the DOM.
	 *
	 * @return true if at least one matching element exists in DOM, false otherwise
	 */
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
