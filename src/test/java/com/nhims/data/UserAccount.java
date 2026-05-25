package com.nhims.data;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Data model representing a user account profile for automation testing.
 * This class isolates test data from the test logic.
 */
public class UserAccount {
	private String name;
	private String email;
	private String title;
	private String password;
	private String birthDay;
	private String birthMonth;
	private String birthYear;
	private String firstName;
	private String lastName;
	private String company;
	private String address1;
	private String address2;
	private String country;
	private String state;
	private String city;
	private String zipcode;
	private String mobileNumber;

	/**
	 * Default constructor.
	 */
	public UserAccount() {
	}

	/**
	 * Gets the full display name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the full display name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the email address.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address.
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the pre-configured default user account data.
	 *
	 * @return a pre-populated UserAccount instance
	 */
	public static UserAccount getDefaultUser() {
		UserAccount user = new UserAccount();
		user.title = "Mr";
		user.password = "SecurePassword123";
		user.birthDay = "20";
		user.birthMonth = "May";
		user.birthYear = "1995";
		user.firstName = "John";
		user.lastName = "Doe";
		user.company = "Test Company";
		user.address1 = "123 Main Street";
		user.address2 = "Suite 450";
		user.country = "United States";
		user.state = "New York";
		user.city = "New York City";
		user.zipcode = "10001";
		user.mobileNumber = "1234567890";
		return user;
	}

	/**
	 * Converts this user account into a map of API parameters
	 * matching the createAccount endpoint field names.
	 *
	 * @return ordered map of parameter names to values
	 */
	public Map<String, String> toApiParams() {
		Map<String, String> params = new LinkedHashMap<>();
		putIfNotNull(params, "name", name);
		putIfNotNull(params, "email", email);
		putIfNotNull(params, "password", password);
		putIfNotNull(params, "title", title);
		putIfNotNull(params, "birth_date", birthDay);
		putIfNotNull(params, "birth_month", birthMonth);
		putIfNotNull(params, "birth_year", birthYear);
		putIfNotNull(params, "firstname", firstName);
		putIfNotNull(params, "lastname", lastName);
		putIfNotNull(params, "company", company);
		putIfNotNull(params, "address1", address1);
		putIfNotNull(params, "address2", address2);
		putIfNotNull(params, "country", country);
		putIfNotNull(params, "zipcode", zipcode);
		putIfNotNull(params, "state", state);
		putIfNotNull(params, "city", city);
		putIfNotNull(params, "mobile_number", mobileNumber);
		return params;
	}

	/**
	 * Encodes the API parameters as an application/x-www-form-urlencoded body.
	 *
	 * @return URL-encoded form body string
	 */
	public String toFormUrlEncoded() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : toApiParams().entrySet()) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
			sb.append("=");
			sb.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
		}
		return sb.toString();
	}

	private void putIfNotNull(Map<String, String> params, String key, String value) {
		if (value != null) {
			params.put(key, value);
		}
	}

	/**
	 * Gets the title prefix (e.g. Mr, Mrs, etc.).
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title prefix.
	 *
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the day of birth.
	 *
	 * @return the birth day
	 */
	public String getBirthDay() {
		return birthDay;
	}

	/**
	 * Sets the day of birth.
	 *
	 * @param birthDay the birth day to set
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * Gets the month of birth.
	 *
	 * @return the birth month
	 */
	public String getBirthMonth() {
		return birthMonth;
	}

	/**
	 * Sets the month of birth.
	 *
	 * @param birthMonth the birth month to set
	 */
	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	/**
	 * Gets the year of birth.
	 *
	 * @return the birth year
	 */
	public String getBirthYear() {
		return birthYear;
	}

	/**
	 * Sets the year of birth.
	 *
	 * @param birthYear the birth year to set
	 */
	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * Sets the company name.
	 *
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * Gets the primary address line.
	 *
	 * @return the primary address
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * Sets the primary address line.
	 *
	 * @param address1 the address line to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * Gets the secondary address line.
	 *
	 * @return the secondary address
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * Sets the secondary address line.
	 *
	 * @param address2 the address line to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the state or region.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state or region.
	 *
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the postal or zip code.
	 *
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * Sets the postal or zip code.
	 *
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * Gets the mobile phone number.
	 *
	 * @return the mobile number
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * Sets the mobile phone number.
	 *
	 * @param mobileNumber the mobile number to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
}
