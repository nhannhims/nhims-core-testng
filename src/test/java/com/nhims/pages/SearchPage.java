package com.nhims.pages;

import com.nhims.controls.Control;

public class SearchPage extends GenaralPage {
	private static Control btnProduct = new Control("//div[@class='search_result_area']//div[@class='item_name' and contains(text(),'%s')]");

	public static void selectProduct(String prodName) {
		btnProduct.setDynamicLocator(prodName).get().click();
	}
}
