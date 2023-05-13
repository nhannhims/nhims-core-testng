package com.nhims.controls;

import com.nhims.utils.Logger;

public class Checkbox extends Control {

	public Checkbox(String xpathOrCssSelector) {
		// TODO Auto-generated constructor stub
		super(xpathOrCssSelector);
	}

	public Checkbox(String xpathOrCssSelector, String iframe, String timeout) {
		// TODO Auto-generated constructor stub
		super(xpathOrCssSelector, iframe, timeout);
	}

	/***
	 * Execute check box with element input
	 */
	public void check() {
		if (!get().isSelected()) {
			get().click();
			Logger.Info("> E > Check a Checkbox");
		}
	}

	/***
	 * Execute uncheck box with element input
	 */
	public void unCheck() {
		if (get().isSelected()) {
			get().click();
			Logger.Info("> E > Uncheck a Checkbox");
		}
	}
}
