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
	private static final Control txtSearchProduct = new Control("#search_product");
	private static final Control btnSearchProduct = new Control("#submit_search");
	private static final Control lblSearchedProductsTitle = new Control("//div[@class='features_items']//h2[contains(text(),'Searched Products')]");
	private static final Control lblSearchedProductsList = new Control("//div[@class='features_items']//div[@class='product-image-wrapper']");

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

	/**
	 * Enters a product name into the search input and clicks the search button.
	 *
	 * @param productName the product name to search for
	 */
	@Step("Search for product: {0}")
	public static void searchProduct(String productName) {
		Logger.info("Enter product name in search input: " + productName);
		txtSearchProduct.get().type(productName);
		Logger.info("Click search button");
		btnSearchProduct.get().click();
	}

	/**
	 * Checks if the 'SEARCHED PRODUCTS' title is visible.
	 *
	 * @return true if the searched products title is displayed
	 */
	@Step("Check if 'SEARCHED PRODUCTS' is visible")
	public static boolean isSearchedProductsVisible() {
		Logger.info("Check if 'SEARCHED PRODUCTS' is visible");
		return lblSearchedProductsTitle.isVisible();
	}

	/**
	 * Gets the 'SEARCHED PRODUCTS' title text.
	 *
	 * @return the searched products title text, or empty string if not visible
	 */
	@Step("Get the 'SEARCHED PRODUCTS' title text")
	public static String getSearchedProductsTitleText() {
		Logger.info("Get 'SEARCHED PRODUCTS' title text");
		if (lblSearchedProductsTitle.isVisible()) {
			return lblSearchedProductsTitle.get().getText();
		}
		return "";
	}

	/**
	 * Checks if the searched products list is visible (i.e. products related to the search are displayed).
	 *
	 * @return true if at least one searched product item is visible
	 */
	@Step("Check if searched products are visible")
	public static boolean areSearchedProductsVisible() {
		Logger.info("Check if searched products are visible");
		return lblSearchedProductsList.isVisible();
	}

	/**
	 * Checks if any searched product name contains the given keyword.
	 *
	 * @param keyword the keyword to search for in product names
	 * @return true if at least one product name contains the keyword
	 */
	@Step("Check if any searched product name contains: {0}")
	public static boolean isAnySearchedProductNameContaining(String keyword) {
		Logger.info("Check if any searched product name contains: " + keyword);
		Control productNameWithKeyword = new Control(
				"//div[@class='features_items']//div[@class='productinfo text-center']//p[contains(.,'" + keyword + "')]");
		return productNameWithKeyword.isVisible();
	}
}
