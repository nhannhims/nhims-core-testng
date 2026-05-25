package com.nhims.api;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nhims.constants.APIConst;
import com.nhims.constants.Configs.EnvironmentConfig;
import com.nhims.data.UserAccount;
import com.nhims.utils.HFile;
import com.nhims.utils.Logger;

public class AccountAPI {

	private static final String BASE_URL = HFile.getConfigEnvironment(EnvironmentConfig.apiBaseUrl);
	private static final Pattern RESPONSE_CODE_PATTERN = Pattern.compile("\"responseCode\"\\s*:\\s*(\\d+)");
	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(30))
			.build();

	private static int extractResponseCode(String body) {
		Matcher matcher = RESPONSE_CODE_PATTERN.matcher(body);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		}
		return -1;
	}

	/**
	 * Creates a user account via the AutomationExercise API.
	 * Use this as a test data preparation step to avoid UI-based registration.
	 *
	 * @param user the user account data (must include name and email)
	 * @return true if the account was created successfully (response code 201)
	 */
	public static boolean createAccount(UserAccount user) {
		try {
			String formBody = user.toFormUrlEncoded();
			Logger.info("API: Creating account via API for email: " + user.getEmail());

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL + APIConst.CREATE_ACCOUNT_ENDPOINT))
					.header("Content-Type", "application/x-www-form-urlencoded")
					.POST(HttpRequest.BodyPublishers.ofString(formBody))
					.timeout(Duration.ofSeconds(30))
					.build();

			HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
			int responseCode = extractResponseCode(response.body());

			Logger.info("API: HTTP status: " + response.statusCode());
			Logger.info("API: Response body: " + response.body());
			Logger.info("API: Extracted responseCode: " + responseCode);

			if (responseCode == APIConst.RESOURCE_CREATED) {
				Logger.info("API: Account created successfully for email: " + user.getEmail());
				return true;
			}

			Logger.error("API: Failed to create account. responseCode: " + responseCode + ", Body: " + response.body());
			return false;
		} catch (Exception e) {
			Logger.error("API: Exception while creating account: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Deletes a user account via the AutomationExercise API.
	 * Use this for cleanup when a test that created an account via API fails or is
	 * aborted.
	 *
	 * @param email    the email of the account to delete
	 * @param password the password of the account to delete
	 * @return true if the account was deleted successfully (response code 200)
	 */
	public static boolean deleteAccount(String email, String password) {
		try {
			String formBody = "email=" + URLEncoder.encode(email, StandardCharsets.UTF_8)
					+ "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8);
			Logger.info("API: Deleting account via API for email: " + email);

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL + APIConst.DELETE_ACCOUNT_ENDPOINT))
					.header("Content-Type", "application/x-www-form-urlencoded")
					.method("DELETE", HttpRequest.BodyPublishers.ofString(formBody))
					.timeout(Duration.ofSeconds(30))
					.build();

			HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
			int responseCode = extractResponseCode(response.body());

			Logger.info("API: HTTP status: " + response.statusCode());
			Logger.info("API: Response body: " + response.body());
			Logger.info("API: Extracted responseCode: " + responseCode);

			if (responseCode == APIConst.SUCCESS) {
				Logger.info("API: Account deleted successfully for email: " + email);
				return true;
			}

			Logger.error("API: Failed to delete account. responseCode: " + responseCode + ", Body: " + response.body());
			return false;
		} catch (Exception e) {
			Logger.error("API: Exception while deleting account: " + e.getMessage());
			return false;
		}
	}
}
