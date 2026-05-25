# NHIMS Core TestNG — AI Test Script Generation Guide

## Purpose
This file is the single source of truth for AI agents to generate test scripts, page objects, data models, and API utilities that are consistent with the project's architecture and coding conventions.

---

## 1. Project Architecture

```
src/
├── main/java/com/nhims/
│   ├── browsers/          # WebDriver lifecycle, navigation, browser extensions
│   ├── constants/         # APIConst, Configs, TimeConst, JavaScript, FileConst, Constants
│   ├── controls/          # Control, Actions, BaseControl, Keyboards (custom element wrapper)
│   ├── drivers/           # BrowserFactory, DriverController (ThreadLocal WebDriver)
│   └── utils/             # Logger, HFile, HDate, HString, Convert, HFolder, RecordVideo
├── test/java/com/nhims/
│   ├── api/               # API utility classes for test data preparation/cleanup
│   ├── data/              # POJO data models (e.g. UserAccount)
│   ├── listeners/         # TestListener (manages driver lifecycle, screenshots, Allure)
│   ├── pages/             # Page Object Model classes
│   │   ├── components/    # Reusable UI components shared across pages
│   │   └── *.java         # Page classes extending BasePage
│   └── scripts/           # Test script classes (TestNG @Test methods)
└── test/resources/
    └── settings/
        ├── configs.properties        # Framework config (environment, driver, retry, etc.)
        ├── staging.properties        # Staging URLs (applicationUrl, apiBaseUrl)
        ├── production.properties     # Production URLs
        └── nightlight.properties     # Nightlight URLs
```

---

## 2. Technology Stack

| Component | Version/Details |
|---|---|
| Java | 11 |
| TestNG | 7.7.1 |
| Selenium | 4.18.1 |
| Allure | 2.25.0 |
| HTTP Client | java.net.http.HttpClient (Java 11 built-in) |
| Build | Maven |
| Reporting | Allure + @Step annotations |
| Logging | SLF4J 2.0.7 + Logback 1.4.8 |
| Parallel | ThreadLocal WebDriver via DriverController |

---

## 3. Coding Conventions

### 3.1 Naming Conventions

| Type | Convention | Example |
|---|---|---|
| Test class | `{Feature}Test` | `RegisterTest`, `LoginTest`, `CartTest` |
| Test method | `test{Verb}{Noun}` | `testRegisterUser`, `testLoginWithValidCredentials` |
| Page class | `{PageName}Page` | `HomePage`, `SignupLoginPage` |
| Component class | `{Name}Component` | `NotificationComponent` |
| Data model | `{Entity}` | `UserAccount`, `Product` |
| API class | `{Entity}API` | `AccountAPI` |
| Control field in page | Prefix by type | `btn` (button), `txt` (input), `lbl` (label), `dd` (dropdown), `rdo` (radio), `chk` (checkbox), `img` (image), `lnk` (link) |
| Constant | UPPER_SNAKE_CASE | `MAX_RETRY`, `SEC_NORMAL_WAIT` |
| Config key in properties | camelCase | `applicationUrl`, `maxRetry`, `apiBaseUrl` |

### 3.2 Import Order
```java
// 1. Java standard library
import java.time.Duration;
import java.util.List;

// 2. Third-party (Selenium, TestNG)
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

// 3. Project utilities and constants
import com.nhims.browsers.Navigation;
import com.nhims.constants.Configs.EnvironmentConfig;
import com.nhims.controls.Control;
import com.nhims.data.UserAccount;
import com.nhims.utils.Logger;

// 4. Allure annotations
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
```

### 3.3 Javadoc
Every class and public method MUST have Javadoc. Constructor Javadoc uses `@param`. Method Javadoc describes behavior and uses `@return` / `@param` where appropriate.

---

## 4. Test Script Template

This is the EXACT pattern to follow when generating test scripts:

```java
package com.nhims.scripts;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nhims.api.AccountAPI;
import com.nhims.browsers.Navigation;
import com.nhims.constants.Configs.EnvironmentConfig;
import com.nhims.data.UserAccount;
import com.nhims.listeners.TestListener;
import com.nhims.pages.HomePage;
import com.nhims.utils.HDate;
import com.nhims.utils.HFile;
import com.nhims.utils.Logger;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(TestListener.class)
@Story("Feature Name")
public class FeatureTest {

	/** Tracks created account email for cleanup via API if test fails (thread-safe for parallel execution). */
	private final ThreadLocal<String> createdEmail = new ThreadLocal<>();
	/** Tracks created account password for cleanup via API if test fails (thread-safe for parallel execution). */
	private final ThreadLocal<String> createdPassword = new ThreadLocal<>();

	/**
	 * Cleanup method that runs after every test (even on failure).
	 * Deletes any account that was created during the test via API.
	 */
	@AfterMethod(alwaysRun = true)
	public void cleanupAccount() {
		String email = createdEmail.get();
		String password = createdPassword.get();
		if (email != null && password != null) {
			Logger.info("Cleanup: Attempting to delete account via API for email: " + email);
			AccountAPI.deleteAccount(email, password);
			createdEmail.remove();
			createdPassword.remove();
		}
	}

	@Test(testName = "TC0002", description = "Test Case 2: ...")
	@Description("Verify that ...")
	@Severity(SeverityLevel.BLOCKER)
	public void testMethodName() {
		// Get test data
		UserAccount user = UserAccount.getDefaultUser();
		String timestamp = HDate.uniqueTimestamp();
		String username = "TestUser_" + timestamp;
		String email = "testuser_" + timestamp + "@gmail.com";

		// Navigate
		Logger.info("1. Navigate to url");
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));

		// Action + Verify pattern
		Logger.info("2. Verify home page is visible");
		Assert.assertTrue(HomePage.isHomePageVisible(), "Home page is not visible!");

		// ... more steps ...

		// Track account for cleanup (set AFTER account creation succeeds)
		createdEmail.set(email);
		createdPassword.set(user.getPassword());

		// ... more steps ...

		// Clear tracking after successful delete via UI
		createdEmail.remove();
		createdPassword.remove();
	}
}
```

### 4.1 Test Script Rules

1. **Always add** `@Listeners(TestListener.class)` at class level
2. **Always add** `@Story("Feature Name")` at class level
3. **Every @Test method** must have: `testName`, `description`, `@Description`, `@Severity`
4. **Every step** must be logged with `Logger.info("N. Description")` where N is the step number. Do NOT add redundant `// Step N: description` comments above `Logger.info()` calls.
5. **Assertions** use TestNG `Assert.assertTrue()` / `Assert.assertEquals()` with descriptive failure messages
6. **Data generation**: Use `HDate.uniqueTimestamp()` for unique timestamps (thread-safe for parallel execution). NEVER use `HDate.formatDate("yyyyMMddHHmmss")` — it only has second-level precision and will cause email collisions when tests run in parallel.
7. **Navigation**: Always use `HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl)` — never hardcode URLs
8. **Cleanup**: If test creates an account, use `@AfterMethod(alwaysRun = true)` with `AccountAPI.deleteAccount()`
9. **Account tracking**: Use `ThreadLocal<String>` for `createdEmail`/`createdPassword` (NOT plain `String`). Set with `.set()` after account creation, clear with `.remove()` after successful UI delete. This is MANDATORY because TestNG `parallel="methods"` creates a single test class instance shared across concurrent threads — plain fields cause race conditions in `@AfterMethod` cleanup.
10. **Google vignette bypass**: After clicking Continue on result pages, check for vignette redirect:
    ```java
    String currentUrl = Navigation.getCurrentUrl();
    if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
        Logger.info("Bypassing Google vignette ad by navigating to the home page");
        Navigation.navigateTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
    }
    ```

---

## 5. Page Object Template

```java
package com.nhims.pages;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Page object for the {Page Name} page.
 */
public class XxxPage extends BasePage {
	// Locators - private static final Control fields at the top
	private static final Control lblTitle = new Control("css-selector");
	private static final Control txtInputField = new Control("#input-id");
	private static final Control btnAction = new Control("button[data-qa='action-btn']");

	// Visibility check - returns boolean
	@Step("Check if {element} is visible")
	public static boolean isTitleVisible() {
		Logger.info("Check if {element} is visible");
		return lblTitle.isVisible();
	}

	// Get text - returns String, guards with isVisible()
	@Step("Get the text of {element}")
	public static String getTitleText() {
		Logger.info("Get {element} text");
		if (lblTitle.isVisible()) {
			return lblTitle.get().getText();
		}
		return "";
	}

	// Action - void, logs action
	@Step("Click '{button name}' button")
	public static void clickAction() {
		Logger.info("Click '{button name}' button");
		btnAction.get().click();
	}

	// Fill form - groups multiple fields
	@Step("Fill {form name} details")
	public static void fillDetails(String field1, String field2) {
		Logger.info("Fill {form name} details");
		txtInputField.get().type(field1);
	}
}
```

### 5.1 Page Object Rules

1. **Extends** `BasePage`
2. **All fields and methods are `static`** — no instantiation needed
3. **Locators** are `private static final Control` at the top of the class
4. **Every method** has `@Step` annotation for Allure reporting
5. **Every method** starts with `Logger.info(...)`
6. **Get text methods** guard with `isVisible()` check, return `""` if not visible
7. **Element interaction** chain: `control.get().action()` where action is `click()`, `type(text)`, `check()`, `selectOptionByText(text)`, `getValue()`, `getText()`
8. **Locator auto-detection**: If locator starts with `/` or `(`, it's XPath; otherwise CSS selector
9. **Never use raw Selenium** — always go through `Control` → `Actions`
10. **Reusable UI blocks** (title + continue pattern shared by result pages): delegate to `NotificationComponent` instead of duplicating controls

### 5.2 Available Actions on Control Elements

| Method | Usage | Description |
|---|---|---|
| `click()` | `btn.get().click()` | Click with JS fallback, auto-waits for page load |
| `type(text)` | `txt.get().type("hello")` | Types text (selects existing + overwrites) |
| `clear()` | `txt.get().clear()` | Clears input field |
| `check()` | `chk.get().check()` | Checks checkbox/radio if not already selected |
| `unCheck()` | `chk.get().unCheck()` | Unchecks checkbox if selected |
| `selectOptionByText(t)` | `dd.get().selectOptionByText("May")` | Selects dropdown option by visible text |
| `getText()` | `lbl.get().getText()` | Gets visible text |
| `getValue()` | `txt.get().getValue()` | Gets input value attribute |
| `getAttr(attr)` | `el.get().getAttr("href")` | Gets any HTML attribute |
| `selectFile(path)` | `el.get().selectFile("path")` | File upload |
| `rightClick()` | `el.get().rightClick()` | Right-click |
| `doubleClick()` | `el.get().doubleClick()` | Double-click |
| `then()` | `txt.get().clear().then().enter()` | Chain keyboard events |
| `isVisible()` | `lbl.isVisible()` | Check visibility (boolean, no exception) |
| `isDisplay()` | `lbl.isDisplay()` | Check if element exists in DOM |
| `setDynamicLocator(args)` | `item.setDynamicLocator("val")` | Parameterize locators |

### 5.3 Stale Element Retry
`Control.find()` automatically retries up to `MAX_RETRY` times on `StaleElementReferenceException`. You do not need to handle this manually in page objects.

---

## 6. Component Template

For reusable UI blocks shared across multiple pages:

```java
package com.nhims.pages.components;

import com.nhims.controls.Control;
import com.nhims.utils.Logger;
import io.qameta.allure.Step;

/**
 * Reusable component for {purpose}.
 */
public class XxxComponent {
	private final Control lblTitle;
	private final Control btnAction;

	/**
	 * Creates component with the given locators.
	 *
	 * @param titleLocator   CSS/XPath for the title element
	 * @param actionLocator  CSS/XPath for the action element
	 */
	public XxxComponent(String titleLocator, String actionLocator) {
		this.lblTitle = new Control(titleLocator);
		this.btnAction = new Control(actionLocator);
	}

	@Step("Check if {element} is visible")
	public boolean isTitleVisible() {
		Logger.info("Check if {element} is visible");
		return lblTitle.isVisible();
	}

	@Step("Get the text of {element}")
	public String getTitleText() {
		Logger.info("Get {element} text");
		if (lblTitle.isVisible()) {
			return lblTitle.get().getText();
		}
		return "";
	}

	@Step("Click action button")
	public void clickAction() {
		Logger.info("Click action button");
		btnAction.get().click();
	}
}
```

### 6.1 Component Rules
1. Placed in `pages/components/` package
2. **Instance-based** (not static) — takes locators via constructor
3. Used by page classes as `private static final XxxComponent` field
4. Page class delegates to component but keeps **static API** for backward compatibility
5. Existing component: `NotificationComponent` — used for success/deleted pages that share a title label + continue button pattern

---

## 7. Data Model Template

```java
package com.nhims.data;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Data model representing {entity} for automation testing.
 */
public class Entity {
	private String name;
	private String email;
	private String field1;

	/** Default constructor. */
	public Entity() {
	}

	/**
	 * Gets a pre-configured default instance.
	 *
	 * @return a pre-populated Entity instance
	 */
	public static Entity getDefault() {
		Entity entity = new Entity();
		entity.name = "Default Name";
		entity.email = "default@example.com";
		entity.field1 = "default value";
		return entity;
	}

	/**
	 * Converts this entity into a map of API parameters.
	 *
	 * @return ordered map of parameter names to values
	 */
	public Map<String, String> toApiParams() {
		Map<String, String> params = new LinkedHashMap<>();
		putIfNotNull(params, "name", name);
		putIfNotNull(params, "email", email);
		putIfNotNull(params, "field1", field1);
		return params;
	}

	/**
	 * Encodes the API parameters as application/x-www-form-urlencoded body.
	 *
	 * @return URL-encoded form body string
	 */
	public String toFormUrlEncoded() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : toApiParams().entrySet()) {
			if (sb.length() > 0) sb.append("&");
			sb.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
			sb.append("=");
			sb.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
		}
		return sb.toString();
	}

	private void putIfNotNull(Map<String, String> params, String key, String value) {
		if (value != null) params.put(key, value);
	}

	// Getters and setters with Javadoc for each
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getField1() { return field1; }
	public void setField1(String field1) { this.field1 = field1; }
}
```

### 7.1 Data Model Rules
1. POJO pattern with private fields, getters, setters
2. Static factory method `getDefault()` or `getDefaultUser()` for default test data
3. If used for API, include `toApiParams()` and `toFormUrlEncoded()` methods
4. `UserAccount` fields: `name`, `email`, `title`, `password`, `birthDay`, `birthMonth`, `birthYear`, `firstName`, `lastName`, `company`, `address1`, `address2`, `country`, `state`, `city`, `zipcode`, `mobileNumber`
5. No Lombok — hand-written getters/setters with Javadoc

---

## 8. API Utility Template

```java
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
import com.nhims.utils.HFile;
import com.nhims.utils.Logger;

/**
 * API utility for {entity} operations.
 */
public class EntityAPI {
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
	 * {Action} via API.
	 *
	 * @param param description
	 * @return true if successful
	 */
	public static boolean doAction(String param) {
		try {
			Logger.info("API: Action for: " + param);
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL + APIConst.ENDPOINT))
					.header("Content-Type", "application/x-www-form-urlencoded")
					.POST(HttpRequest.BodyPublishers.ofString("param=" + URLEncoder.encode(param, StandardCharsets.UTF_8)))
					.timeout(Duration.ofSeconds(30))
					.build();

			HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
			int responseCode = extractResponseCode(response.body());

			Logger.info("API: HTTP status: " + response.statusCode());
			Logger.info("API: Response body: " + response.body());
			Logger.info("API: Extracted responseCode: " + responseCode);

			if (responseCode == APIConst.SUCCESS) {
				Logger.info("API: Action succeeded");
				return true;
			}
			Logger.error("API: Action failed. responseCode: " + responseCode);
			return false;
		} catch (Exception e) {
			Logger.error("API: Exception: " + e.getMessage());
			return false;
		}
	}
}
```

### 8.1 API Utility Rules
1. **All methods are `static`** — no instantiation
2. **BASE_URL** from `HFile.getConfigEnvironment(EnvironmentConfig.apiBaseUrl)` — never hardcode
3. **Endpoints** from `APIConst` constants
4. **Response code** extracted from JSON body via regex (API always returns HTTP 200, real code is in `responseCode` field)
5. **DELETE with body**: Use `.method("DELETE", HttpRequest.BodyPublishers.ofString(body))` — Java 11 `.DELETE()` takes no arguments and cannot carry a body
6. All API calls wrapped in try-catch, return `boolean`
7. Every action logged with `Logger.info/error`

---

## 9. Configuration Convention

### Adding new config keys:
1. Add key to `configs.properties` with default value
2. Add enum value to `Configs.ConfigFile` or `Configs.EnvironmentConfig`
3. Access via `HFile.getConfig(ConfigFile.keyName)` or `HFile.getConfigEnvironment(EnvironmentConfig.keyName)`

### Adding API endpoints:
1. Add constant to `APIConst.java` in `src/main/java/com/nhims/constants/`

### Adding response codes:
1. Add constant to `APIConst.java`

### Environment properties files:
Each environment file (e.g. `staging.properties`) must define both:
```properties
applicationUrl = http://automationexercise.com
apiBaseUrl = https://automationexercise.com/api
```

---

## 10. AutomationExercise API Reference

### Base URL
```
https://automationexercise.com/api
```

### Available Endpoints

| # | Method | Endpoint | Parameters | Response Code | Response Message |
|---|---|---|---|---|---|
| 1 | POST | /createAccount | name, email, password, title, birth_date, birth_month, birth_year, firstname, lastname, company, address1, address2, country, zipcode, state, city, mobile_number | 201 | User created! |
| 2 | DELETE | /deleteAccount | email, password | 200 | Account deleted! |

### API Response Format
```json
{"responseCode": 201, "message": "User created!"}
```
**Important**: HTTP status is always 200. The actual response code is in the JSON `responseCode` field.

---

## 11. Page Objects Reference (Existing)

| Page Class | Key Methods |
|---|---|
| `HomePage` | `isHomePageVisible()`, `clickSignupLogin()`, `clickDeleteAccount()`, `getLoggedInUserText()` |
| `SignupLoginPage` | `isNewUserSignupVisible()`, `getNewUserSignupText()`, `enterSignupNameAndEmail(name, email)`, `clickSignup()` |
| `RegisterPage` | `isEnterAccountInfoVisible()`, `getEnterAccountInfoText()`, `getPrefilledName()`, `getPrefilledEmail()`, `fillAccountDetails(title, password, day, month, year)`, `selectNewsletter()`, `selectSpecialOffers()`, `fillAddressDetails(firstName, lastName, company, address1, address2, country, state, city, zipcode, mobileNumber)`, `clickCreateAccount()` |
| `AccountCreatedPage` | `isAccountCreatedVisible()`, `getAccountCreatedText()`, `clickContinue()` |
| `AccountDeletedPage` | `isAccountDeletedVisible()`, `getAccountDeletedText()`, `clickContinue()` |

When generating test scripts, reuse these existing page methods. Only create new page objects when navigating to pages not yet covered.

---

## 12. Checklist Before Generating Code

- [ ] Test class has `@Listeners(TestListener.class)` and `@Story`
- [ ] Test method has `testName`, `description`, `@Description`, `@Severity`
- [ ] Every step has `Logger.info("N. Step description")` (no redundant `// Step N:` comments above them)
- [ ] Navigation uses `HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl)`
- [ ] URLs are never hardcoded — use config or `APIConst`
- [ ] Account creation tracked for cleanup with `@AfterMethod(alwaysRun = true)`
- [ ] `createdEmail`/`createdPassword` are `ThreadLocal<String>` (NOT plain `String`), set with `.set()` after creation, cleared with `.remove()` after successful UI delete
- [ ] Dynamic data uses `HDate.uniqueTimestamp()` (NOT `HDate.formatDate("yyyyMMddHHmmss")`) to prevent email collisions in parallel execution
- [ ] Page methods are `static`, have `@Step` and `Logger.info()`
- [ ] Get-text methods guard with `isVisible()` check
- [ ] API methods parse `responseCode` from JSON body, not HTTP status
- [ ] DELETE requests use `.method("DELETE", BodyPublishers.ofString(body))`
- [ ] All new constants go to the appropriate constants class
- [ ] All new config keys added to both `configs.properties`, environment `.properties` files, and `Configs` enum
- [ ] `toFormUrlEncoded()` used when sending data model as API request body
- [ ] Reusable UI patterns (notification title + continue) use `NotificationComponent`
- [ ] Javadoc on all classes and public methods
