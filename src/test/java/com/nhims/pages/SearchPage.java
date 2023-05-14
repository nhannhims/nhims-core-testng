package com.nhims.pages;

import com.nhims.controls.Button;

public class SearchPage extends GenaralPage {
	private static Button btnProduct = new Button(
			"//div[@class='search_result_area']//div[@class='item_name' and contains(text(),'%s')]");

	public static void selectProduct(String prodName) {
		btnProduct.setDynamicLocator(prodName).click();
	}
}
