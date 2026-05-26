package com.nhims.scripts;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nhims.browsers.Navigation;
import com.nhims.constants.Configs.EnvironmentConfig;
import com.nhims.constants.FileConst;
import com.nhims.listeners.TestListener;
import com.nhims.pages.ContactUsPage;
import com.nhims.pages.HomePage;
import com.nhims.utils.HDate;
import com.nhims.utils.HFile;
import com.nhims.utils.Logger;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

/**
 * Test class for Contact Us feature.
 * Verifies the Contact Us form submission and success flow.
 */
@Listeners(TestListener.class)
@Story("Contact Us")
public class ContactUsTest {

	/**
	 * TC0006: Contact Us Form.
	 * Verifies that a user can submit the Contact Us form successfully
	 * with name, email, subject, message and file upload.
	 */
	@Test(testName = "TC0006", description = "Test Case 6: Contact Us Form")
	@Description("Verify that the Contact Us form can be submitted successfully with valid details and file upload")
	@Severity(SeverityLevel.CRITICAL)
	public void testContactUsForm() {
		String timestamp = HDate.uniqueTimestamp();
		String name = "Contact User_" + timestamp;
		String email = "contactuser_" + timestamp + "@gmail.com";
		String subject = "Test Subject_" + timestamp;
		String message = "This is a test message from automation testing. Timestamp: " + timestamp;
		String uploadFilePath = Paths.get(FileConst.MAIN_PATH, "src", "test", "resources", "testdata", "upload_sample.txt").toString();

		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		Logger.info("2. Verify that home page is visible successfully");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		Logger.info("3. Click on 'Contact Us' button");
		HomePage.clickContactUs();

		Logger.info("4. Verify 'GET IN TOUCH' is visible");
		Assert.assertTrue(ContactUsPage.isGetInTouchVisible(), "'GET IN TOUCH' heading is not visible!");
		String getInTouchText = ContactUsPage.getGetInTouchText();
		Assert.assertTrue(getInTouchText.toLowerCase().contains("get in touch"),
				"Expected 'GET IN TOUCH' text but got: " + getInTouchText);

		Logger.info("5. Enter name, email, subject and message");
		ContactUsPage.fillContactForm(name, email, subject, message);

		Logger.info("6. Upload file");
		ContactUsPage.uploadFile(uploadFilePath);

		Logger.info("7. Click 'Submit' button");
		ContactUsPage.clickSubmit();

		Logger.info("8. Click OK button");
		ContactUsPage.acceptAlert();

		Logger.info("9. Verify success message 'Success! Your details have been submitted successfully.' is visible");
		Assert.assertTrue(ContactUsPage.isSuccessMessageVisible(), "Success message is not visible!");
		String successMessage = ContactUsPage.getSuccessMessageText();
		Assert.assertTrue(successMessage.toLowerCase().contains("success! your details have been submitted successfully."),
				"Expected success message containing 'Success! Your details have been submitted successfully.' but got: " + successMessage);

		Logger.info("10. Click 'Home' button and verify that landed to home page successfully");
		ContactUsPage.clickHome();

		// Bypass potential Google vignette ad redirect
		String currentUrl = Navigation.getCurrentUrl();
		if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
			Logger.info("Bypassing Google vignette ad by navigating to the home page");
			Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		}

		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible after clicking 'Home' button!");
	}
}
