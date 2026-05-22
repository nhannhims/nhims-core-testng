package com.nhims.controls;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.nhims.browsers.BrowserExtensions;
import com.nhims.browsers.Navigation;
import com.nhims.utils.Logger;

public class Keyboards {
	private WebElement element;

	/**
	 * Constructor to initialize Keyboards wrapper with a WebElement.
	 *
	 * @param element the WebElement to perform keyboard actions on
	 */
	public Keyboards(WebElement element) {
		this.element = element;
	}

	/**
	 * Sends the ENTER key to the element. Waits for page loading if the URL changes.
	 */
	public void enter() {
		String url = Navigation.getCurrentUrl();
		element.sendKeys(Keys.ENTER);
		Logger.info("> E > Press [Enter]");
		String current = Navigation.getCurrentUrl();
		if (!url.equals(current)) {
			BrowserExtensions.waitPageLoading();
		}
	}

}
