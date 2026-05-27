package com.nhims.scripts;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nhims.browsers.Navigation;
import com.nhims.constants.Configs.EnvironmentConfig;
import com.nhims.listeners.TestListener;
import com.nhims.pages.CartPage;
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
 * Test class for Cart feature.
 * Verifies add to cart functionality.
 */
@Listeners(TestListener.class)
@Story("Cart")
public class CartTest {
	private static final int FIRST_PRODUCT_INDEX = 1;
	private static final int SECOND_PRODUCT_INDEX = 2;
	private static final String DEFAULT_QUANTITY = "1";
	private static final String TARGET_QUANTITY = "4";

	/**
	 * TC0012: Add Products in Cart.
	 * Navigates to the home page, clicks the Products button,
	 * adds two products to cart, verifies both products are in the cart
	 * with correct prices, quantity and total price.
	 */
	@Test(testName = "TC0012", description = "Test Case 12: Add Products in Cart")
	@Description("Verify that user can add multiple products to cart and verify their prices, quantity and total price")
	@Severity(SeverityLevel.CRITICAL)
	public void testAddProductsInCart() {
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

		Logger.info("3. Click 'Products' button");
		HomePage.clickProducts();

		// Bypass potential Google vignette ad redirect
		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to products page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/products");
		}

		// Verify ALL PRODUCTS page is visible
		Assert.assertTrue(ProductsPage.isAllProductsPageVisible(), "ALL PRODUCTS page is not visible!");

		// Capture first product details before adding to cart
		String firstProductName = ProductsPage.getProductNameByIndex(FIRST_PRODUCT_INDEX);
		String firstProductPrice = ProductsPage.getProductPriceByIndex(FIRST_PRODUCT_INDEX);

		Logger.info("4. Hover over first product and click 'Add to cart'");
		ProductsPage.hoverOverProductAndAddToCart(FIRST_PRODUCT_INDEX);

		Logger.info("5. Click 'Continue Shopping' button");
		ProductsPage.clickContinueShopping();

		// Capture second product details before adding to cart
		String secondProductName = ProductsPage.getProductNameByIndex(SECOND_PRODUCT_INDEX);
		String secondProductPrice = ProductsPage.getProductPriceByIndex(SECOND_PRODUCT_INDEX);

		Logger.info("6. Hover over second product and click 'Add to cart'");
		ProductsPage.hoverOverProductAndAddToCart(SECOND_PRODUCT_INDEX);

		Logger.info("7. Click 'View Cart' link");
		ProductsPage.clickViewCart();

		// Bypass potential Google vignette ad redirect
		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to cart page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/view_cart");
		}

		Logger.info("8. Verify both products are added to Cart");
		Assert.assertTrue(CartPage.isShoppingCartPageVisible(), "Shopping cart page is not visible!");

		// Verify first product is in cart
		String cartProduct1Name = CartPage.getCartProductName(FIRST_PRODUCT_INDEX);
		Assert.assertTrue(cartProduct1Name.toLowerCase().contains(firstProductName.toLowerCase()),
				"First product name in cart does not match! Expected: " + firstProductName + ", Actual: " + cartProduct1Name);

		// Verify second product is in cart
		String cartProduct2Name = CartPage.getCartProductName(SECOND_PRODUCT_INDEX);
		Assert.assertTrue(cartProduct2Name.toLowerCase().contains(secondProductName.toLowerCase()),
				"Second product name in cart does not match! Expected: " + secondProductName + ", Actual: " + cartProduct2Name);

		Logger.info("9. Verify their prices, quantity and total price");

		// Verify first product price, quantity and total
		String cartProduct1Price = CartPage.getCartProductPrice(FIRST_PRODUCT_INDEX);
		Assert.assertEquals(cartProduct1Price.toLowerCase(), firstProductPrice.toLowerCase(),
				"First product price in cart does not match!");

		String cartProduct1Quantity = CartPage.getCartProductQuantity(FIRST_PRODUCT_INDEX);
		Assert.assertEquals(cartProduct1Quantity, DEFAULT_QUANTITY, "First product quantity should be 1!");

		String cartProduct1Total = CartPage.getCartProductTotalPrice(FIRST_PRODUCT_INDEX);
		Assert.assertEquals(cartProduct1Total.toLowerCase(), firstProductPrice.toLowerCase(),
				"First product total price should equal unit price!");

		// Verify second product price, quantity and total
		String cartProduct2Price = CartPage.getCartProductPrice(SECOND_PRODUCT_INDEX);
		Assert.assertEquals(cartProduct2Price.toLowerCase(), secondProductPrice.toLowerCase(),
				"Second product price in cart does not match!");

		String cartProduct2Quantity = CartPage.getCartProductQuantity(SECOND_PRODUCT_INDEX);
		Assert.assertEquals(cartProduct2Quantity, DEFAULT_QUANTITY, "Second product quantity should be 1!");

		String cartProduct2Total = CartPage.getCartProductTotalPrice(SECOND_PRODUCT_INDEX);
		Assert.assertEquals(cartProduct2Total.toLowerCase(), secondProductPrice.toLowerCase(),
				"Second product total price should equal unit price!");
	}

	/**
	 * TC0013: Verify Product quantity in Cart.
	 * Navigates to the home page, clicks 'View Product' for a recommended product,
	 * increases quantity to 4, adds to cart, and verifies the product is displayed
	 * in the cart page with the exact quantity.
	 */
	@Test(testName = "TC0013", description = "Test Case 13: Verify Product quantity in Cart")
	@Description("Verify that user can change product quantity on product detail page and the correct quantity is shown in cart")
	@Severity(SeverityLevel.CRITICAL)
	public void testVerifyProductQuantityInCart() {
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

		Logger.info("3. Click 'View Product' for any product on home page");
		String productDetailUrl = HomePage.getFirstProductDetailUrl();
		HomePage.clickViewProductOnHomePage();

		// Bypass potential Google vignette ad redirect
		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to product details page");
			Navigation.navigateTo(productDetailUrl);
		}

		Logger.info("4. Verify product detail is opened");
		Assert.assertTrue(ProductDetailPage.isProductDetailVisible(), "Product detail page is not visible!");

		// Capture product name before adding to cart
		String productName = ProductDetailPage.getProductName();

		Logger.info("5. Increase quantity to 4");
		ProductDetailPage.setQuantity(TARGET_QUANTITY);

		Logger.info("6. Click 'Add to cart' button");
		ProductDetailPage.clickAddToCart();

		Logger.info("7. Click 'View Cart' button");
		ProductDetailPage.clickViewCart();

		// Bypass potential Google vignette ad redirect
		currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to cart page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl) + "/view_cart");
		}

		Logger.info("8. Verify that product is displayed in cart page with exact quantity");
		Assert.assertTrue(CartPage.isShoppingCartPageVisible(), "Shopping cart page is not visible!");

		String cartProductName = CartPage.getCartProductName(FIRST_PRODUCT_INDEX);
		Assert.assertEquals(cartProductName.toLowerCase(), productName.toLowerCase(),
				"Product name in cart does not match!");

		String cartProductQuantity = CartPage.getCartProductQuantity(FIRST_PRODUCT_INDEX);
		Assert.assertEquals(cartProductQuantity, TARGET_QUANTITY,
				"Product quantity in cart should be " + TARGET_QUANTITY + "!");
	}
}
