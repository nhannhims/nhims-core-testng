package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the All Products page.
 */
public class ProductsPage extends BasePage {
	private static final Control lblAllProductsTitle = new Control("div.features_items h2");
	private static final Control lblProductsList = new Control("div.features_items");
	private static final Control lnkViewProductFirst = new Control("//div[@class='features_items']//div[@class='choose']//a[contains(text(),'View Product')][1]");

	/**
	 * Checks if the ALL PRODUCTS page is visible by verifying the title element.
	 *
	 * @return true if the ALL PRODUCTS title is displayed
	 */
	@Step("Check if ALL PRODUCTS page is visible")
	public static boolean isAllProductsPageVisible() {
		Logger.info("Check if ALL PRODUCTS page is visible");
		return lblAllProductsTitle.isVisible();
	}

	/**
	 * Gets the ALL PRODUCTS title text.
	 *
	 * @return the title text, or empty string if not visible
	 */
	@Step("Get the ALL PRODUCTS title text")
	public static String getAllProductsTitleText() {
		Logger.info("Get ALL PRODUCTS title text");
		if (lblAllProductsTitle.isVisible()) {
			return lblAllProductsTitle.get().getText();
		}
		return "";
	}

	/**
	 * Checks if the products list is visible.
	 *
	 * @return true if the products list container is displayed
	 */
	@Step("Check if the products list is visible")
	public static boolean isProductsListVisible() {
		Logger.info("Check if the products list is visible");
		return lblProductsList.isVisible();
	}

	/**
	 * Clicks the 'View Product' link of the first product.
	 */
	@Step("Click 'View Product' of first product")
	public static void clickViewProductFirst() {
		Logger.info("Click 'View Product' of first product");
		lnkViewProductFirst.get().click();
	}

	/**
	 * Gets the href attribute of the first 'View Product' link.
	 *
	 * @return the href attribute value, or empty string if not visible
	 */
	@Step("Get 'View Product' href of first product")
	public static String getFirstProductDetailUrl() {
		Logger.info("Get 'View Product' href of first product");
		if (lnkViewProductFirst.isVisible()) {
			return lnkViewProductFirst.get().getAttr("href");
		}
		return "";
	}
}
