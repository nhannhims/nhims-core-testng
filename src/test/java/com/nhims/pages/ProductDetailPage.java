package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;

import junit.framework.Assert;

public class ProductDetailPage {
	private static Control btnFavourite = new Control("button.js-toggleFavorite");

	public static void clickFavouriteButton() {
		btnFavourite.get().click();
	}

	public static void verifyFavouriteButtonChangeStatus() {
		String actualStatus = btnFavourite.get().getAttr("class");
		Logger.Info("[Verify]-> Actual [" + actualStatus + "] Expected to contain [_active]");
		Assert.assertTrue(actualStatus.contains("_active"));
	}
}
