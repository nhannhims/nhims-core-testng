package com.nhims.pages;

import com.nhims.browsers.BrowserExtensions;
import com.nhims.constants.Constants.AlertAction;
import com.nhims.constants.TimeConst;
import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Contact Us page.
 */
public class ContactUsPage extends BasePage {
	private static final Control lblGetInTouch = new Control("div.contact-form h2");
	private static final Control txtName = new Control("input[data-qa='name']");
	private static final Control txtEmail = new Control("input[data-qa='email']");
	private static final Control txtSubject = new Control("input[data-qa='subject']");
	private static final Control txtMessage = new Control("textarea[data-qa='message']");
	private static final Control btnUploadFile = new Control("input[name='upload_file']");
	private static final Control btnSubmit = new Control("input[data-qa='submit-button']");
	private static final Control lblSuccessMessage = new Control(
			"//div[contains(@class,'status') and contains(., 'Success')]");
	private static final Control btnHome = new Control("//a[contains(@href,'/') and contains(., 'Home')]");

	/**
	 * Checks if the 'GET IN TOUCH' heading is visible on the Contact Us page.
	 *
	 * @return true if the heading is visible, false otherwise
	 */
	@Step("Check if 'GET IN TOUCH' heading is visible")
	public static boolean isGetInTouchVisible() {
		Logger.info("Check if 'GET IN TOUCH' heading is visible");
		return lblGetInTouch.isVisible();
	}

	/**
	 * Gets the text of the 'GET IN TOUCH' heading.
	 *
	 * @return the heading text, or empty string if not visible
	 */
	@Step("Get the text of 'GET IN TOUCH' heading")
	public static String getGetInTouchText() {
		Logger.info("Get 'GET IN TOUCH' heading text");
		if (lblGetInTouch.isVisible()) {
			return lblGetInTouch.get().getText();
		}
		return "";
	}

	/**
	 * Fills the contact form with the provided details.
	 *
	 * @param name    the contact name
	 * @param email   the contact email
	 * @param subject the message subject
	 * @param message the message body
	 */
	@Step("Fill contact form with name, email, subject and message")
	public static void fillContactForm(String name, String email, String subject, String message) {
		Logger.info("Fill contact form details");
		txtName.get().type(name);
		txtEmail.get().type(email);
		txtSubject.get().type(subject);
		txtMessage.get().type(message);
	}

	/**
	 * Uploads a file to the contact form.
	 *
	 * @param filePath the absolute or relative path to the file to upload
	 */
	@Step("Upload file: {filePath}")
	public static void uploadFile(String filePath) {
		Logger.info("Upload file: " + filePath);
		btnUploadFile.get().selectFile(filePath);
	}

	/**
	 * Clicks the 'Submit' button on the contact form.
	 * Uses simpleClick to avoid triggering URL check which would auto-dismiss the confirm dialog.
	 */
	@Step("Click 'Submit' button")
	public static void clickSubmit() {
		Logger.info("Click 'Submit' button");
		btnSubmit.get().simpleClick();
	}

	/**
	 * Accepts the JavaScript confirm alert dialog after form submission.
	 * Waits explicitly for the alert to be present before accepting.
	 */
	@Step("Accept submit confirmation alert")
	public static void acceptAlert() {
		Logger.info("Accept submit confirmation alert");
		BrowserExtensions.handleAlert(TimeConst.SEC_SHORT_WAIT, AlertAction.ACCEPT);
	}

	/**
	 * Checks if the success message is visible after form submission.
	 *
	 * @return true if the success message is visible, false otherwise
	 */
	@Step("Check if success message is visible")
	public static boolean isSuccessMessageVisible() {
		Logger.info("Check if success message is visible");
		return lblSuccessMessage.isVisible();
	}

	/**
	 * Gets the text of the success message after form submission.
	 *
	 * @return the success message text, or empty string if not visible
	 */
	@Step("Get the success message text")
	public static String getSuccessMessageText() {
		Logger.info("Get success message text");
		if (lblSuccessMessage.isVisible()) {
			return lblSuccessMessage.get().getText();
		}
		return "";
	}

	/**
	 * Clicks the 'Home' button to navigate back to the home page.
	 */
	@Step("Click 'Home' button")
	public static void clickHome() {
		Logger.info("Click 'Home' button");
		btnHome.get().click();
	}
}
