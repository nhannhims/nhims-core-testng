package com.nhims.controls;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.nhims.utils.Logger;

public class Combobox extends Control {

	public Combobox(String xpathOrCssSelector) {
		// TODO Auto-generated constructor stub
		super(xpathOrCssSelector);
	}

	public Combobox(String xpathOrCssSelector, String iframe, String timeout) {
		// TODO Auto-generated constructor stub
		super(xpathOrCssSelector, iframe, timeout);
	}

	private Select select() {
		Select sel = new Select(get());
		return sel;
	}

	public void selectOptionByText(String optText) {
		select().selectByVisibleText(optText);
		Logger.Info("> E > Select Option has text [" + optText + "]");
	}

	public void selectOptionText(String text) {
		List<WebElement> options = get().findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (option.getText().equals(text)) {
				option.click();
				break;
			}
		}
		Logger.Info("> E > Select Option has text [" + text + "]");
	}

	public void deselectOptionByText(String optText) {
		select().deselectByVisibleText(optText);
		Logger.Info("> E > Deselect Option has text [" + optText + "]");
	}

	public int getAllOption() {
		return select().getAllSelectedOptions().size();
	}
}
