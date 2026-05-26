package com.nhims.scripts;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nhims.browsers.Navigation;
import com.nhims.constants.Configs.EnvironmentConfig;
import com.nhims.listeners.TestListener;
import com.nhims.pages.HomePage;
import com.nhims.pages.ProductDetailPage;
import com.nhims.pages.ProductsPage;
import com.nhims.utils.HFile;
import com.nhims.utils.Logger;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

/**
 * Test class for Products feature.
 * Verifies All Products page and product detail page.
 */
@Listeners(TestListener.class)
@Story("Products")
public class ProductsTest {

	/**
	 * TC0008: Verify All Products and product detail page.
	 * Navigates to the home page, clicks the Products button,
	 * verifies the ALL PRODUCTS page, clicks View Product on the first product,
	 * and verifies the product detail page displays all required information.
	 */
	@Test(testName = "TC0008", description = "Test Case 8: Verify All Products and product detail page")
	@Description("Verify that all products page is visible and product detail page displays product name, category, price, availability, condition, brand")
	@Severity(SeverityLevel.CRITICAL)
	public void testVerifyAllProductsAndProductDetail() {
		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		// Bypass potential Google vignette ad redirect
		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Click on 'Products' button");
		HomePage.clickProducts();

		// Bypass potential Google vignette ad redirect
		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to products page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/products");
		}

		Logger.info("4. Verify user is navigated to ALL PRODUCTS page successfully");
		Assert.assertTrue(ProductsPage.isAllProductsPageVisible(), "ALL PRODUCTS page is not visible!");
		String allProductsTitle = ProductsPage.getAllProductsTitleText();
		Assert.assertTrue(allProductsTitle.toLowerCase().contains("all products"),
				"Expected title to contain 'ALL PRODUCTS' but got: " + allProductsTitle);

		Logger.info("5. Verify the products list is visible");
		Assert.assertTrue(ProductsPage.isProductsListVisible(), "Products list is not visible!");

		Logger.info("6. Click on 'View Product' of first product");
		String productDetailUrl = ProductsPage.getFirstProductDetailUrl();
		ProductsPage.clickViewProductFirst();

		// Bypass potential Google vignette ad redirect
		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to product details page");
			Navigation.navigateTo(productDetailUrl);
		}

		Logger.info("7. Verify user is landed to product detail page");
		Assert.assertTrue(ProductDetailPage.isProductDetailVisible(), "Product detail page is not visible!");

		Logger.info("8. Verify that detail is visible: product name, category, price, availability, condition, brand");
		String productName = ProductDetailPage.getProductName();
		Assert.assertTrue(!productName.isEmpty(), "Product name is not visible!");

		Assert.assertTrue(ProductDetailPage.isCategoryVisible(), "Product category is not visible!");
		String category = ProductDetailPage.getCategory();
		Assert.assertTrue(!category.isEmpty(), "Product category text is empty!");
		Assert.assertTrue(category.toLowerCase().contains("category"),
				"Expected category text to contain 'Category' but got: " + category);

		Assert.assertTrue(ProductDetailPage.isPriceVisible(), "Product price is not visible!");
		String price = ProductDetailPage.getPrice();
		Assert.assertTrue(!price.isEmpty(), "Product price text is empty!");

		Assert.assertTrue(ProductDetailPage.isAvailabilityVisible(), "Product availability is not visible!");
		String availability = ProductDetailPage.getAvailability();
		Assert.assertTrue(availability.toLowerCase().contains("availability"),
				"Expected availability text to contain 'Availability' but got: " + availability);

		Assert.assertTrue(ProductDetailPage.isConditionVisible(), "Product condition is not visible!");
		String condition = ProductDetailPage.getCondition();
		Assert.assertTrue(condition.toLowerCase().contains("condition"),
				"Expected condition text to contain 'Condition' but got: " + condition);

		Assert.assertTrue(ProductDetailPage.isBrandVisible(), "Product brand is not visible!");
		String brand = ProductDetailPage.getBrand();
		Assert.assertTrue(brand.toLowerCase().contains("brand"),
				"Expected brand text to contain 'Brand' but got: " + brand);
	}

	/**
	 * TC0009: Search Product.
	 * Navigates to the home page, clicks the Products button,
	 * verifies the ALL PRODUCTS page, enters a product name in the search input,
	 * clicks search, and verifies that searched products are visible.
	 */
	@Test(testName = "TC0009", description = "Test Case 9: Search Product")
	@Description("Verify that the user can search for a product and all related search results are displayed")
	@Severity(SeverityLevel.CRITICAL)
	public void testSearchProduct() {
		String searchKeyword = "Top";

		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		// Bypass potential Google vignette ad redirect
		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Click on 'Products' button");
		HomePage.clickProducts();

		// Bypass potential Google vignette ad redirect
		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to products page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/products");
		}

		Logger.info("4. Verify user is navigated to ALL PRODUCTS page successfully");
		Assert.assertTrue(ProductsPage.isAllProductsPageVisible(), "ALL PRODUCTS page is not visible!");

		Logger.info("5. Enter product name in search input and click search button");
		ProductsPage.searchProduct(searchKeyword);

		Logger.info("6. Verify 'SEARCHED PRODUCTS' is visible");
		Assert.assertTrue(ProductsPage.isSearchedProductsVisible(), "'SEARCHED PRODUCTS' is not visible!");
		String searchedProductsTitle = ProductsPage.getSearchedProductsTitleText();
		Assert.assertTrue(searchedProductsTitle.toLowerCase().contains("searched products"),
				"Expected title to contain 'SEARCHED PRODUCTS' but got: " + searchedProductsTitle);

		Logger.info("7. Verify all the products related to search are visible");
		Assert.assertTrue(ProductsPage.areSearchedProductsVisible(), "Searched products are not visible!");
		Assert.assertTrue(ProductsPage.isAnySearchedProductNameContaining(searchKeyword),
				"No searched product name contains the keyword '" + searchKeyword + "'!");
	}
}
