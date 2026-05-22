package com.nhims.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.nhims.browsers.Browsers;
import com.nhims.utils.Logger;

public class BaseControl {
	protected WebElement getElement(String xpathOrCssSeletor) {
		WebElement element = null;
		if (xpathOrCssSeletor.startsWith("/") || xpathOrCssSeletor.startsWith("(")) {
			try {
				element = Browsers.browser().findElement(By.xpath(xpathOrCssSeletor));
			} catch (Exception e) {
				// TODO: handle exception
				Logger.Error(e.getLocalizedMessage());
				throw new RuntimeException(e.getMessage());
			}

		} else {
			try {
				element = Browsers.browser().findElement(By.cssSelector(xpathOrCssSeletor));
			} catch (Exception e) {
				// TODO: handle exception
				Logger.Error(e.getLocalizedMessage());
				throw new RuntimeException(e.getMessage());
			}
		}
		return element;
	}

	protected int countElement(String xpathOrCssSeletor) {
		int count = 0;
		if (xpathOrCssSeletor.startsWith("/") || xpathOrCssSeletor.startsWith("(")) {
			count = Browsers.browser().findElements(By.xpath(xpathOrCssSeletor)).size();
		} else {
			count = Browsers.browser().findElements(By.cssSelector(xpathOrCssSeletor)).size();
		}
		return count;
	}

	protected Actions useAction() {
		Actions action = new Actions(Browsers.browser());
		return action;
	}
}
