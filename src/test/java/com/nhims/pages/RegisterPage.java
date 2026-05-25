package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the Account Registration page.
 */
public class RegisterPage extends BasePage {
	private static final Control lblEnterAccountInfo = new Control("div.login-form h2 b");
	private static final Control rdoGenderMr = new Control("#id_gender1");
	private static final Control rdoGenderMrs = new Control("#id_gender2");
	private static final Control txtName = new Control("#name");
	private static final Control txtEmail = new Control("#email");
	private static final Control txtPassword = new Control("#password");
	private static final Control ddDays = new Control("#days");
	private static final Control ddMonths = new Control("#months");
	private static final Control ddYears = new Control("#years");
	private static final Control chkNewsletter = new Control("#newsletter");
	private static final Control chkOptin = new Control("#optin");

	private static final Control txtFirstName = new Control("#first_name");
	private static final Control txtLastName = new Control("#last_name");
	private static final Control txtCompany = new Control("#company");
	private static final Control txtAddress1 = new Control("#address1");
	private static final Control txtAddress2 = new Control("#address2");
	private static final Control ddCountry = new Control("#country");
	private static final Control txtState = new Control("#state");
	private static final Control txtCity = new Control("#city");
	private static final Control txtZipcode = new Control("#zipcode");
	private static final Control txtMobileNumber = new Control("#mobile_number");
	private static final Control btnCreateAccount = new Control("button[data-qa='create-account']");

	/**
	 * Checks if the 'ENTER ACCOUNT INFORMATION' title is visible.
	 *
	 * @return true if the title is displayed
	 */
	@Step("Check if 'ENTER ACCOUNT INFORMATION' title is visible")
	public static boolean isEnterAccountInfoVisible() {
		Logger.info("Check if 'ENTER ACCOUNT INFORMATION' title is visible");
		return lblEnterAccountInfo.isVisible();
	}

	/**
	 * Gets the text of the 'ENTER ACCOUNT INFORMATION' title.
	 *
	 * @return the title text, or empty string if not visible
	 */
	@Step("Get the text of the 'ENTER ACCOUNT INFORMATION' title")
	public static String getEnterAccountInfoText() {
		Logger.info("Get 'ENTER ACCOUNT INFORMATION' text");
		if (lblEnterAccountInfo.isVisible()) {
			return lblEnterAccountInfo.get().getText();
		}
		return "";
	}

	/**
	 * Gets the prefilled name field value.
	 *
	 * @return the name value, or empty string if not visible
	 */
	@Step("Get the prefilled name field value")
	public static String getPrefilledName() {
		Logger.info("Get prefilled 'Name' value");
		if (txtName.isVisible()) {
			return txtName.get().getValue();
		}
		return "";
	}

	/**
	 * Gets the prefilled email field value.
	 *
	 * @return the email value, or empty string if not visible
	 */
	@Step("Get the prefilled email field value")
	public static String getPrefilledEmail() {
		Logger.info("Get prefilled 'Email' value");
		if (txtEmail.isVisible()) {
			return txtEmail.get().getValue();
		}
		return "";
	}

	/**
	 * Fills the account information section with title, password, and date of birth.
	 *
	 * @param title    the gender title (e.g. "Mr", "Mrs")
	 * @param password the account password
	 * @param day      the birth day
	 * @param month    the birth month
	 * @param year     the birth year
	 */
	@Step("Fill account information: Title='{title}', Password='{password}', DOB='{day}/{month}/{year}'")
	public static void fillAccountDetails(String title, String password, String day, String month, String year) {
		Logger.info("Fill Account Details: Title=" + title + ", Password=****" + ", DOB=" + day + "/" + month + "/" + year);
		if (title.equalsIgnoreCase("Mr") || title.equalsIgnoreCase("Mr.")) {
			rdoGenderMr.get().check();
		} else if (title.equalsIgnoreCase("Mrs") || title.equalsIgnoreCase("Mrs.") || title.equalsIgnoreCase("Ms")) {
			rdoGenderMrs.get().check();
		}

		txtPassword.get().type(password);
		ddDays.get().selectOptionByText(day);
		ddMonths.get().selectOptionByText(month);
		ddYears.get().selectOptionByText(year);
	}

	/**
	 * Selects the 'Sign up for our newsletter!' checkbox.
	 */
	@Step("Select 'Sign up for our newsletter!' checkbox")
	public static void selectNewsletter() {
		Logger.info("Select checkbox 'Sign up for our newsletter!'");
		chkNewsletter.get().check();
	}

	/**
	 * Selects the 'Receive special offers from our partners!' checkbox.
	 */
	@Step("Select 'Receive special offers from our partners!' checkbox")
	public static void selectSpecialOffers() {
		Logger.info("Select checkbox 'Receive special offers from our partners!'");
		chkOptin.get().check();
	}

	/**
	 * Fills the address information section with all required address fields.
	 *
	 * @param firstName    the first name
	 * @param lastName     the last name
	 * @param company      the company name
	 * @param address1     the primary address
	 * @param address2     the secondary address
	 * @param country      the country
	 * @param state        the state
	 * @param city         the city
	 * @param zipcode      the zip code
	 * @param mobileNumber the mobile number
	 */
	@Step("Fill detailed address information")
	public static void fillAddressDetails(String firstName, String lastName, String company, String address1, String address2,
										  String country, String state, String city, String zipcode, String mobileNumber) {
		Logger.info("Fill Address Details");
		txtFirstName.get().type(firstName);
		txtLastName.get().type(lastName);
		txtCompany.get().type(company);
		txtAddress1.get().type(address1);
		txtAddress2.get().type(address2);
		ddCountry.get().selectOptionByText(country);
		txtState.get().type(state);
		txtCity.get().type(city);
		txtZipcode.get().type(zipcode);
		txtMobileNumber.get().type(mobileNumber);
	}

	/**
	 * Clicks the 'Create Account' button to submit the registration form.
	 */
	@Step("Click 'Create Account' button")
	public static void clickCreateAccount() {
		Logger.info("Click 'Create Account' button");
		btnCreateAccount.get().click();
	}
}
