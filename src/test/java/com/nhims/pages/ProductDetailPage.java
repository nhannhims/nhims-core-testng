package com.nhims.pages;

import com.nhims.controls.Control;

import org.testng.Assert;

/**
 * Page Object for the product detail page.
 */
public class ProductDetailPage extends BasePage {
	private static final Control btnFavourite = new Control("button.js-toggleFavorite");

	public static void clickFavouriteButton() {
		btnFavourite.get().click();
	}

	public static void verifyFavouriteButtonChangeStatus() {
		String actualStatus = btnFavourite.get().getAttr("class");
		logVerify("Favourite button class", actualStatus, "contains [_active]");
		Assert.assertTrue(actualStatus.contains("_active"),
				"Expected favourite button to contain '_active' but got: " + actualStatus);
	}
}
