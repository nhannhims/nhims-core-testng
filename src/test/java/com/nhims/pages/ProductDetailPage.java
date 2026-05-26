package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Product Detail page.
 */
public class ProductDetailPage extends BasePage {
	private static final Control lblProductName = new Control("div.product-information h2");
	private static final Control lblCategory = new Control("//div[@class='product-information']/p[contains(text(),'Category')]");
	private static final Control lblPrice = new Control("div.product-information span span");
	private static final Control lblAvailability = new Control("//div[@class='product-information']//p[b[contains(text(),'Availability')]]");
	private static final Control lblCondition = new Control("//div[@class='product-information']//p[b[contains(text(),'Condition')]]");
	private static final Control lblBrand = new Control("//div[@class='product-information']//p[b[contains(text(),'Brand')]]");

	/**
	 * Checks if the product detail page is visible by verifying the product name element.
	 *
	 * @return true if the product name is displayed
	 */
	@Step("Check if product detail page is visible")
	public static boolean isProductDetailVisible() {
		Logger.info("Check if product detail page is visible");
		return lblProductName.isVisible();
	}

	/**
	 * Gets the product name text.
	 *
	 * @return the product name, or empty string if not visible
	 */
	@Step("Get the product name")
	public static String getProductName() {
		Logger.info("Get product name");
		if (lblProductName.isVisible()) {
			return lblProductName.get().getText();
		}
		return "";
	}

	/**
	 * Gets the product category text.
	 *
	 * @return the category text, or empty string if not visible
	 */
	@Step("Get the product category")
	public static String getCategory() {
		Logger.info("Get product category");
		if (lblCategory.isVisible()) {
			return lblCategory.get().getText();
		}
		return "";
	}

	/**
	 * Checks if the product category is visible.
	 *
	 * @return true if the category element is displayed
	 */
	@Step("Check if product category is visible")
	public static boolean isCategoryVisible() {
		Logger.info("Check if product category is visible");
		return lblCategory.isVisible();
	}

	/**
	 * Gets the product price text.
	 *
	 * @return the price text, or empty string if not visible
	 */
	@Step("Get the product price")
	public static String getPrice() {
		Logger.info("Get product price");
		if (lblPrice.isVisible()) {
			return lblPrice.get().getText();
		}
		return "";
	}

	/**
	 * Checks if the product price is visible.
	 *
	 * @return true if the price element is displayed
	 */
	@Step("Check if product price is visible")
	public static boolean isPriceVisible() {
		Logger.info("Check if product price is visible");
		return lblPrice.isVisible();
	}

	/**
	 * Checks if the product availability is visible.
	 *
	 * @return true if the availability info is displayed
	 */
	@Step("Check if product availability is visible")
	public static boolean isAvailabilityVisible() {
		Logger.info("Check if product availability is visible");
		return lblAvailability.isVisible();
	}

	/**
	 * Gets the product availability text.
	 *
	 * @return the availability text, or empty string if not visible
	 */
	@Step("Get the product availability")
	public static String getAvailability() {
		Logger.info("Get product availability");
		if (lblAvailability.isVisible()) {
			return lblAvailability.get().getText();
		}
		return "";
	}

	/**
	 * Checks if the product condition is visible.
	 *
	 * @return true if the condition info is displayed
	 */
	@Step("Check if product condition is visible")
	public static boolean isConditionVisible() {
		Logger.info("Check if product condition is visible");
		return lblCondition.isVisible();
	}

	/**
	 * Gets the product condition text.
	 *
	 * @return the condition text, or empty string if not visible
	 */
	@Step("Get the product condition")
	public static String getCondition() {
		Logger.info("Get product condition");
		if (lblCondition.isVisible()) {
			return lblCondition.get().getText();
		}
		return "";
	}

	/**
	 * Checks if the product brand is visible.
	 *
	 * @return true if the brand info is displayed
	 */
	@Step("Check if product brand is visible")
	public static boolean isBrandVisible() {
		Logger.info("Check if product brand is visible");
		return lblBrand.isVisible();
	}

	/**
	 * Gets the product brand text.
	 *
	 * @return the brand text, or empty string if not visible
	 */
	@Step("Get the product brand")
	public static String getBrand() {
		Logger.info("Get product brand");
		if (lblBrand.isVisible()) {
			return lblBrand.get().getText();
		}
		return "";
	}
}
