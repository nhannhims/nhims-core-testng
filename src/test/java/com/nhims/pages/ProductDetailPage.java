package com.nhims.pages;

import com.nhims.controls.Button;
import com.nhims.utils.Logger;

import junit.framework.Assert;

public class ProductDetailPage {
	private static Button btnFavourite = new Button("//div[@id='js-addFavorite']");

	public static void clickFavouriteButton() {
		btnFavourite.click();
	}

	public static void verifyFavouriteButtonChangeStatus() {
		Logger.Info("[Verify]-> Actual [" + btnFavourite.getAttr("class") + "] Expected [fav_icon _active]");
		Assert.assertEquals(btnFavourite.getAttr("class"), "fav_icon _active");
	}
}
