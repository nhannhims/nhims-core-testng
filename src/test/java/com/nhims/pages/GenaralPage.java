package com.nhims.pages;

import com.nhims.browsers.Browsers;
import com.nhims.controls.Textbox;

public class GenaralPage extends Browsers {
	private static Textbox txtSearch = new Textbox("//input[@id='js-searchKeywords']");

	public static void executeSearchProduct(String prodName) {
		txtSearch.type(prodName).enter();
	}
}
