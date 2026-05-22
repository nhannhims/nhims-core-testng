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

	/**
	 * Constructor to initialize Actions wrapper with a WebElement.
	 *
	 * @param element the WebElement to perform actions on
	 */
	public Actions(WebElement element) {
		this.element = element;
	}

	/**
	 * Creates an instance of Selenium Actions for advanced interactions.
	 *
	 * @return a Selenium Actions instance
	 */
	private org.openqa.selenium.interactions.Actions useAction() {
		return new org.openqa.selenium.interactions.Actions(Browsers.browser());
	}

	/**
	 * Performs a click action on the element. Fallbacks to JavaScript click if standard click fails.
	 */
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

	/**
	 * Performs a click action via JavaScript executor.
	 */
	private void clickByJS() {
		((JavascriptExecutor) Browsers.browser()).executeScript(JavaScript.ACTION_CLICK, element);
		Logger.info("> E > ClickJS");
	}

	/**
	 * Performs a right click (context click) on the element.
	 */
	public void rightClick() {
		useAction().contextClick(element).perform();
		Logger.info("> E > Right Click");
	}

	/**
	 * Performs a double click on the element.
	 */
	public void doubleClick() {
		useAction().doubleClick(element).perform();
		Logger.info("> E > Double Click");
	}

	/**
	 * Types the given text into the element after clearing existing text.
	 *
	 * @param text the text to input
	 * @return this Actions instance for chaining
	 */
	public Actions type(String text) {
		element.sendKeys(Keys.END);
		element.sendKeys(Keys.SHIFT, Keys.HOME);
		element.sendKeys(text);
		Logger.info("> E > Input text [" + text + "]");
		return this;
	}

	/**
	 * Sets the file path for file upload elements.
	 *
	 * @param path the local path of the file to upload
	 */
	public void selectFile(String path) {
		element.sendKeys(path);
		Logger.info("> E > Upload file has path [" + path + "]");
	}

	/**
	 * Clears the input field.
	 *
	 * @return this Actions instance for chaining
	 */
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

	/**
	 * Gets a Keyboard helper to chain keyboard events.
	 *
	 * @return a Keyboards instance
	 */
	public Keyboards then() {
		Keyboards keyboard = new Keyboards(element);
		return keyboard;
	}

	/**
	 * Checks/selects a checkbox or radio button if not already selected.
	 */
	public void check() {
		if (!element.isSelected()) {
			element.click();
			Logger.info("> E > Check a Checkbox/Radio Button");
		}
	}

	/**
	 * Unchecks/deselects a checkbox if selected.
	 */
	public void unCheck() {
		if (element.isSelected()) {
			element.click();
			Logger.info("> E > Uncheck a Checkbox");
		}
	}

	/**
	 * Instantiates a Selenium Select wrapper for select elements.
	 *
	 * @return the Selenium Select wrapper
	 */
	private Select select() {
		Select sel = new Select(element);
		return sel;
	}

	/**
	 * Selects an option in a drop-down list by its visible text.
	 *
	 * @param optText the visible text of the option to select
	 */
	public void selectOptionByText(String optText) {
		select().selectByVisibleText(optText);
		Logger.info("> E > Select Option has text [" + optText + "]");
	}

	/**
	 * Selects an option in a drop-down list by looping through options.
	 *
	 * @param text the visible text of the option to select
	 * @deprecated Use {@link #selectOptionByText(String)} instead.
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

	/**
	 * Deselects an option in a drop-down list by its visible text.
	 *
	 * @param optText the visible text of the option to deselect
	 */
	public void deselectOptionByText(String optText) {
		select().deselectByVisibleText(optText);
		Logger.info("> E > Deselect Option has text [" + optText + "]");
	}

	/**
	 * Returns the number of currently selected options in this select element.
	 *
	 * @return count of selected options
	 */
	public int getSelectedOptionCount() {
		return select().getAllSelectedOptions().size();
	}

	/**
	 * Gets the value of the specified HTML attribute.
	 *
	 * @param attr the attribute name
	 * @return the attribute value, or an empty string if null
	 */
	public String getAttr(String attr) {
		String val = element.getAttribute(attr);
		String text = val != null ? val : "";
		Logger.info("> E > Value of attribute [" + attr + "] is [" + text + "]");
		return text;
	}

	/**
	 * Gets the visible inner text of the element. Fallbacks to innerHTML or textContent if empty.
	 *
	 * @return the element's text content
	 */
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

	/**
	 * Gets the value attribute of the element.
	 *
	 * @return the value attribute string
	 */
	public String getValue() {
		String text = element.getAttribute("value");
		return text;
	}
}
