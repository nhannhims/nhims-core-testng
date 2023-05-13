package com.nhims.controls;

import com.nhims.utils.Logger;

public class RadioButton extends Control {

	public RadioButton(String xpathOrCssSelector) {
		// TODO Auto-generated constructor stub
		super(xpathOrCssSelector);
	}

	public RadioButton(String xpathOrCssSelector, String iframe, String timeout) {
		// TODO Auto-generated constructor stub
		super(xpathOrCssSelector, iframe, timeout);
	}

	public void check() {
		if (!get().isSelected()) {
			get().click();
			Logger.Info("> E > Check a radio button");
		}
	}
}
