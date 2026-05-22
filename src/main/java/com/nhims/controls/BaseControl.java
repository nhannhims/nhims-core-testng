package com.nhims.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.nhims.browsers.Browsers;
import com.nhims.utils.Logger;

public class BaseControl {
	/**
	 * Locates a WebElement using XPath (if it starts with '/' or '(') or CSS selector.
	 * Throws a RuntimeException if the element is not found.
	 *
	 * @param xpathOrCssSelector the locator string (XPath or CSS selector)
	 * @return the located WebElement
	 */
	protected WebElement getElement(String xpathOrCssSelector) {
		WebElement element = null;
		By by = (xpathOrCssSelector.startsWith("/") || xpathOrCssSelector.startsWith("("))
				? By.xpath(xpathOrCssSelector)
				: By.cssSelector(xpathOrCssSelector);
		try {
			element = Browsers.browser().findElement(by);
		} catch (Exception e) {
			Logger.error("Cannot find element: " + xpathOrCssSelector + " - " + e.getLocalizedMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		return element;
	}

	/**
	 * Counts the number of elements matching the locator.
	 * Returns 0 if an exception occurs.
	 *
	 * @param xpathOrCssSelector the locator string
	 * @return the number of matching elements
	 */
	protected int countElement(String xpathOrCssSelector) {
		int count = 0;
		By by = (xpathOrCssSelector.startsWith("/") || xpathOrCssSelector.startsWith("("))
				? By.xpath(xpathOrCssSelector)
				: By.cssSelector(xpathOrCssSelector);
		try {
			count = Browsers.browser().findElements(by).size();
		} catch (Exception e) {
			count = 0;
		}
		return count;
	}

	/**
	 * Creates an instance of Selenium Actions for advanced interactions.
	 *
	 * @return a Selenium Actions instance
	 */
	protected Actions useAction() {
		return new Actions(Browsers.browser());
	}
}
