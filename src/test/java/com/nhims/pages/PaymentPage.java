package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Payment page.
 * Handles payment form entry and order confirmation.
 */
public class PaymentPage extends BasePage {
	private static final Control lblPaymentTitle = new Control(".heading");
	private static final Control txtNameOnCard = new Control("input[name='name_on_card']");
	private static final Control txtCardNumber = new Control("input[name='card_number']");
	private static final Control txtCvc = new Control("input[name='cvc']");
	private static final Control txtExpiryMonth = new Control("input[name='expiry_month']");
	private static final Control txtExpiryYear = new Control("input[name='expiry_year']");
	private static final Control btnPayAndConfirm = new Control("button#submit");
	private static final Control lblOrderPlacedHeading = new Control("h2[data-qa='order-placed']");
	private static final Control lblOrderPlacedSuccessMessage = new Control(
			"//h2[@data-qa='order-placed']/following-sibling::p");

	/**
	 * Checks if the Payment page is visible by verifying the heading.
	 *
	 * @return true if the payment heading is displayed
	 */
	@Step("Check if Payment page is visible")
	public static boolean isPaymentPageVisible() {
		Logger.info("Check if Payment page is visible");
		return lblPaymentTitle.isVisible();
	}

	/**
	 * Fills the payment form with card details.
	 *
	 * @param nameOnCard   the name on the card
	 * @param cardNumber   the card number
	 * @param cvc          the card CVC
	 * @param expiryMonth  the card expiration month
	 * @param expiryYear   the card expiration year
	 */
	@Step("Fill payment details")
	public static void fillPaymentDetails(String nameOnCard, String cardNumber, String cvc,
			String expiryMonth, String expiryYear) {
		Logger.info("Fill payment details");
		txtNameOnCard.get().type(nameOnCard);
		txtCardNumber.get().type(cardNumber);
		txtCvc.get().type(cvc);
		txtExpiryMonth.get().type(expiryMonth);
		txtExpiryYear.get().type(expiryYear);
	}

	/**
	 * Clicks the 'Pay and Confirm Order' button.
	 */
	@Step("Click 'Pay and Confirm Order' button")
	public static void clickPayAndConfirmOrder() {
		Logger.info("Click 'Pay and Confirm Order' button");
		btnPayAndConfirm.get().click();
	}

	/**
	 * Checks if the order placed success message is visible.
	 *
	 * @return true if the order success message is displayed
	 */
	@Step("Check if order placed success message is visible")
	public static boolean isOrderPlacedSuccessVisible() {
		Logger.info("Check if order placed success message is visible");
		return lblOrderPlacedHeading.isVisible();
	}

	/**
	 * Gets the order placed success message text.
	 *
	 * @return the success message text, or empty string if not visible
	 */
	@Step("Get the order placed success message text")
	public static String getOrderPlacedSuccessText() {
		Logger.info("Get order placed success message text");
		if (lblOrderPlacedSuccessMessage.isVisible()) {
			return lblOrderPlacedSuccessMessage.get().getText();
		}
		if (lblOrderPlacedHeading.isVisible()) {
			return lblOrderPlacedHeading.get().getText();
		}
		return "";
	}
}
