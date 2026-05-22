package com.nhims.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.nhims.browsers.Browsers;
import com.nhims.utils.Logger;

public class BaseControl {
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

	protected Actions useAction() {
		return new Actions(Browsers.browser());
	}
}
