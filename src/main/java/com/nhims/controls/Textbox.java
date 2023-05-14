package com.nhims.controls;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.nhims.browsers.BrowserExtensions;
import com.nhims.browsers.Navigation;
import com.nhims.utils.Logger;

public class Textbox extends Control {
	private WebElement element = null;

	public Textbox(String xpathOrCssSelector) {
		// TODO Auto-generated constructor stub
		super(xpathOrCssSelector);
	}

	public Textbox(String xpathOrCssSelector, String iframe, String timeout) {
		// TODO Auto-generated constructor stub
		super(xpathOrCssSelector, iframe, timeout);
	}

	/***
	 * Execute type the text into input field
	 * 
	 * @param text
	 */
	public Textbox type(String text) {
		if (element == null) {
			element = get();
		}
		this.clear();
		this.element.clear();
		this.element.sendKeys(text);
		Logger.Info("> E > Type [" + text + "]");
		return this;
	}

	public void enter() {
		if (element == null) {
			element = get();
		}
		String url = Navigation.getCurrentUrl();
		element.sendKeys(Keys.ENTER);
		Logger.Info("> E > Press [Enter]");
		String current = Navigation.getCurrentUrl();
		if (!url.equals(current)) {
			BrowserExtensions.waitPageLoading();
		}
	}

	public void clear() {
		if (element == null) {
			element = get();
		}
		element.sendKeys(Keys.END);
		element.sendKeys(Keys.SHIFT, Keys.HOME);
		element.sendKeys(Keys.DELETE);
		Logger.Info("> E > Clear input field by keyboard");
	}
}
