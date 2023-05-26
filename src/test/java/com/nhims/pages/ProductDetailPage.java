package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;

import junit.framework.Assert;

public class ProductDetailPage {
	private static Control btnFavourite = new Control("//div[@id='js-addFavorite']");

	public static void clickFavouriteButton() {
		btnFavourite.get().click();
	}

	public static void verifyFavouriteButtonChangeStatus() {
		String expectedStatus = btnFavourite.get().getAttr("class");
		Logger.Info("[Verify]-> Actual [" + expectedStatus + "] Expected [fav_icon _active]");
		Assert.assertEquals(expectedStatus, "fav_icon _active");
	}
}
