package com.nhims.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.nhims.browsers.Browsers;

public class BaseControl extends Browsers {
	protected WebElement getElement(String xpathOrCssSeletor) {
		WebElement element = null;
		if (xpathOrCssSeletor.startsWith("/") || xpathOrCssSeletor.startsWith("(")) {
			element = browser().findElement(By.xpath(xpathOrCssSeletor));
		}

		if (xpathOrCssSeletor.startsWith(".") || xpathOrCssSeletor.startsWith("[a-zA-Z]+")) {
			element = browser().findElement(By.cssSelector(xpathOrCssSeletor));
		}
		return element;
	}

	protected int countElement(String xpathOrCssSeletor) {
		int count = 0;
		if (xpathOrCssSeletor.startsWith("/") || xpathOrCssSeletor.startsWith("(")) {
			count = browser().findElements(By.xpath(xpathOrCssSeletor)).size();
		}

		if (xpathOrCssSeletor.startsWith(".") || xpathOrCssSeletor.startsWith("[a-zA-Z]+")) {
			count = browser().findElements(By.cssSelector(xpathOrCssSeletor)).size();
		}
		return count;
	}
	
	protected Actions useAction() {
		Actions action = new Actions(browser());
		return action;
	}
}
