package com.nhims.controls;

import com.nhims.utils.Logger;

public class Button extends Control {

	public Button(String xpathOrCssSelector) {
		// TODO Auto-generated constructor stub
		super(xpathOrCssSelector);
	}

	public Button(String xpathOrCssSelector, String iframe, String timeout) {
		// TODO Auto-generated constructor stub
		super(xpathOrCssSelector, iframe, timeout);
	}

	/***
	 * Execute right click
	 */
	public void rightClick() {
		useAction().contextClick(get()).perform();
		Logger.Info("> E > Right Click");
	}

	/***
	 * Execute double click
	 */
	public void doubleClick() {
		useAction().doubleClick(get()).perform();
		Logger.Info("> E > Double Click");
	}
}
