package com.nhims.controls;

import org.openqa.selenium.Keys;

import com.nhims.utils.Logger;

public class Textbox extends Control {

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
	public void type(String text) {
		clear();
		get().clear();
		get().sendKeys(text);
		Logger.Info("> E > Type [" + text + "]");
	}

	public void clear() {
		get().sendKeys(Keys.END);
		get().sendKeys(Keys.SHIFT, Keys.HOME);
		get().sendKeys(Keys.DELETE);
		Logger.Info("> E > Clear input field by keyboard");
	}
}
