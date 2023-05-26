package com.nhims.controls;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.nhims.browsers.BrowserExtensions;
import com.nhims.browsers.Navigation;
import com.nhims.utils.Logger;

public class Keyboards {
	private WebElement element;

	public Keyboards(WebElement element) {
		// TODO Auto-generated constructor stub
		this.element = element;
	}

	public void enter() {
		String url = Navigation.getCurrentUrl();
		element.sendKeys(Keys.ENTER);
		Logger.Info("> E > Press [Enter]");
		String current = Navigation.getCurrentUrl();
		if (!url.equals(current)) {
			BrowserExtensions.waitPageLoading();
		}
	}

}
