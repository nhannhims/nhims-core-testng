package com.nhims.controls;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.nhims.browsers.BrowserExtensions;
import com.nhims.browsers.Navigation;
import com.nhims.constants.JavaScript;
import com.nhims.utils.Convert;
import com.nhims.utils.Logger;

public class Actions extends BaseControl {
	private WebElement element;

	public Actions(WebElement element) {
		// TODO Auto-generated constructor stub
		this.element = element;
	}

	public void click() {
		String url = Convert.formatStringToUTF8(Navigation.getCurrentUrl());
		try {
			element.click();
			Logger.Info("> E > Click");
			String current = Convert.formatStringToUTF8(Navigation.getCurrentUrl());
			if (!url.equals(current)) {
				BrowserExtensions.waitPageLoading();
			}
		} catch (Exception e) {
			// TODO: handle exception
			clickByJS();
			String current = Convert.formatStringToUTF8(Navigation.getCurrentUrl());
			if (!url.equals(current)) {
				BrowserExtensions.waitPageLoading();
			}
		}
	}

	private void clickByJS() {
		((JavascriptExecutor) browser()).executeScript(JavaScript.ACTION_CLICK, element);
		Logger.Info("> E > ClickJS");
	}

	public void rightClick() {
		useAction().contextClick(element).perform();
		Logger.Info("> E > Right Click");
	}

	public void doubleClick() {
		useAction().doubleClick(element).perform();
		Logger.Info("> E > Double Click");
	}

	public Actions type(String text) {
		element.sendKeys(Keys.END);
		element.sendKeys(Keys.SHIFT, Keys.HOME);
		element.sendKeys(text);
		Logger.Info("> E > Input text [" + text + "]");
		return this;
	}

	public void selectFile(String path) {
		element.sendKeys(path);
		Logger.Info("> E > Upload file has path [" + path + "]");
	}

	public Actions clear() {
		element.clear();
		if (getValue() == null || getValue() == "") {
			element.sendKeys(Keys.END);
			element.sendKeys(Keys.SHIFT, Keys.HOME);
			element.sendKeys(Keys.DELETE);
			Logger.Info("> E > Clear input field by keyboard");
		} else {
			Logger.Info("> E > Clear input field");
		}
		return this;
	}

	public Keyboards then() {
		Keyboards keyboard = new Keyboards(element);
		return keyboard;
	}

	public void check() {
		if (!element.isSelected()) {
			element.click();
			Logger.Info("> E > Check a Checkbox/Radio Button");
		}
	}

	public void unCheck() {
		if (element.isSelected()) {
			element.click();
			Logger.Info("> E > Uncheck a Checkbox");
		}
	}

	private Select select() {
		Select sel = new Select(element);
		return sel;
	}

	public void selectOptionByText(String optText) {
		select().selectByVisibleText(optText);
		Logger.Info("> E > Select Option has text [" + optText + "]");
	}

	public void selectOptionText(String text) {
		List<WebElement> options = element.findElements(By.tagName("option"));
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

	public String getAttr(String attr) {
		String text = element.getAttribute(attr).toString();
		Logger.Info("> E > Value of attribute [" + attr + "] is [" + text + "]");
		return text;
	}

	public String getText() {
		String text = element.getText().toString();
		if (text == null || text == "") {
			text = getAttr("innerHTML");
			if (text == null || text == "") {
				text = getAttr("textContent");
			}
		} else {
			Logger.Info("> E > The text is [" + text + "]");
		}
		return text;
	}

	public String getValue() {
		String text = element.getAttribute("value");
		return text;
	}
}
