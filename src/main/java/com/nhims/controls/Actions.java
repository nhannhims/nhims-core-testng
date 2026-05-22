package com.nhims.controls;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.nhims.browsers.Browsers;
import com.nhims.browsers.BrowserExtensions;
import com.nhims.browsers.Navigation;
import com.nhims.constants.JavaScript;
import com.nhims.utils.Convert;
import com.nhims.utils.Logger;

public class Actions {
	private WebElement element;

	public Actions(WebElement element) {
		this.element = element;
	}

	private org.openqa.selenium.interactions.Actions useAction() {
		return new org.openqa.selenium.interactions.Actions(Browsers.browser());
	}

	public void click() {
		String url = Convert.formatStringToUTF8(Navigation.getCurrentUrl());
		try {
			element.click();
			Logger.info("> E > Click");
			String current = Convert.formatStringToUTF8(Navigation.getCurrentUrl());
			if (!url.equals(current)) {
				BrowserExtensions.waitPageLoading();
			}
		} catch (Exception e) {
			clickByJS();
			String current = Convert.formatStringToUTF8(Navigation.getCurrentUrl());
			if (!url.equals(current)) {
				BrowserExtensions.waitPageLoading();
			}
		}
	}

	private void clickByJS() {
		((JavascriptExecutor) Browsers.browser()).executeScript(JavaScript.ACTION_CLICK, element);
		Logger.info("> E > ClickJS");
	}

	public void rightClick() {
		useAction().contextClick(element).perform();
		Logger.info("> E > Right Click");
	}

	public void doubleClick() {
		useAction().doubleClick(element).perform();
		Logger.info("> E > Double Click");
	}

	public Actions type(String text) {
		element.sendKeys(Keys.END);
		element.sendKeys(Keys.SHIFT, Keys.HOME);
		element.sendKeys(text);
		Logger.info("> E > Input text [" + text + "]");
		return this;
	}

	public void selectFile(String path) {
		element.sendKeys(path);
		Logger.info("> E > Upload file has path [" + path + "]");
	}

	public Actions clear() {
		element.clear();
		if (getValue() == null || getValue().isEmpty()) {
			element.sendKeys(Keys.END);
			element.sendKeys(Keys.SHIFT, Keys.HOME);
			element.sendKeys(Keys.DELETE);
			Logger.info("> E > Clear input field by keyboard");
		} else {
			Logger.info("> E > Clear input field");
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
			Logger.info("> E > Check a Checkbox/Radio Button");
		}
	}

	public void unCheck() {
		if (element.isSelected()) {
			element.click();
			Logger.info("> E > Uncheck a Checkbox");
		}
	}

	private Select select() {
		Select sel = new Select(element);
		return sel;
	}

	public void selectOptionByText(String optText) {
		select().selectByVisibleText(optText);
		Logger.info("> E > Select Option has text [" + optText + "]");
	}

	/**
	 * @deprecated Use {@link #selectOptionByText(String)} instead, which uses the standard Select API.
	 * This manual loop version is kept for edge cases where the Select API does not work.
	 */
	@Deprecated
	public void selectOptionText(String text) {
		List<WebElement> options = element.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (option.getText().equals(text)) {
				option.click();
				break;
			}
		}
		Logger.info("> E > Select Option has text [" + text + "]");
	}

	public void deselectOptionByText(String optText) {
		select().deselectByVisibleText(optText);
		Logger.info("> E > Deselect Option has text [" + optText + "]");
	}

	/**
	 * Returns the number of currently selected options in this select element.
	 */
	public int getSelectedOptionCount() {
		return select().getAllSelectedOptions().size();
	}

	public String getAttr(String attr) {
		String val = element.getAttribute(attr);
		String text = val != null ? val : "";
		Logger.info("> E > Value of attribute [" + attr + "] is [" + text + "]");
		return text;
	}

	public String getText() {
		String rawText = element.getText();
		String text = rawText != null ? rawText : "";
		if (text.isEmpty()) {
			text = getAttr("innerHTML");
			if (text == null || text.isEmpty()) {
				text = getAttr("textContent");
			}
		} else {
			Logger.info("> E > The text is [" + text + "]");
		}
		return text;
	}

	public String getValue() {
		String text = element.getAttribute("value");
		return text;
	}
}
