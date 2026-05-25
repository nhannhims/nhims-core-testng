# NHIMS Core TestNG — Automation Framework

This automation testing project is built using a combination of **Java**, **Selenium WebDriver**, **TestNG**, and dependency management via **Maven**. The framework implements the **Page Object Model (POM)** design pattern combined with custom controls and utilities to optimize performance and ensure thread-safe stability during parallel execution.

---

## Tech Stack

| Component | Version | Purpose |
|:---|:---:|:---|
| Java | 11+ | Programming Language |
| Selenium WebDriver | 4.18.1 | Browser Control |
| TestNG | 7.7.1 | Testing Framework & Parallel Execution |
| Allure Report | 2.25.0 | High-quality HTML Reports |
| SLF4J + Logback | 2.0.7 / 1.4.8 | Thread-safe Asynchronous Logging |
| Monte Screen Recorder | 0.7.7.0 | Screen Recording for Test Execution |
| java.net.http.HttpClient | 11 (built-in) | API calls for test data preparation/cleanup |
| Maven | 3.x | Build and Project Management |

---

## Project Structure

```text
nhims-core-testng/
├── src/
├── main/
│   ├── java/com/nhims/
│   │   ├── browsers/      # BrowserExtensions, Navigation, Browsers
│   │   ├── constants/     # APIConst, Configs, FileConst, JavaScript, TimeConst
│   │   ├── controls/      # Control (with retry), Actions, BaseControl, Keyboards
│   │   ├── drivers/       # BrowserFactory, DriverController, DriverExtensions
│   │   └── utils/         # Logger (SLF4J), RecordVideo, HFile, HDate, HFolder, Convert
│   └── resources/
│       └── logback.xml    # Logging Configuration (Console + RollingFile)
└── test/
    ├── java/com/nhims/
    │   ├── api/            # API utilities for test data preparation/cleanup (AccountAPI)
    │   ├── data/           # Test Data Models (e.g., UserAccount)
    │   ├── listeners/      # TestListener — lifecycle hooks (screenshot, video, driver)
    │   ├── pages/          # Page Objects
    │   │   ├── components/ # Reusable UI components (NotificationComponent)
    │   │   └── *.java      # Page classes: BasePage, HomePage, SignupLoginPage, etc.
    │   └── scripts/        # Test scripts: RegisterTest
    └── resources/settings/
        ├── configs.properties     # Core configuration (driver, environment, video, capture, retry...)
        ├── staging.properties     # Staging Environment URLs (applicationUrl, apiBaseUrl)
        ├── production.properties  # Production Environment URL
        └── nightlight.properties  # Nightlight Environment URL
├── .kilo/
│   └── agent/
│       └── test-generation-guide.md   # AI prompt guide for generating test scripts
├── testng.xml                 # Test Suite Configuration (parallel, thread-count)
├── pom.xml                    # Maven dependencies & plugins
└── README.md
```

---

## Key Features

1. **Thread-safe Parallel Execution:** The infrastructure uses `ThreadLocal` for WebDriver instance management, ScreenRecorder, and stateless element searches, ensuring parallel execution without race conditions.
2. **BrowserFactory:** Supports Chrome, Firefox, and Edge via a simple change of `driver=` in the configuration file.
3. **Explicit Wait:** The `Control` class utilizes `WebDriverWait` and `ExpectedConditions` instead of brittle `Thread.sleep` calls.
4. **Stale Element Retry:** `Control.find()` and all `Actions` methods automatically retry on `StaleElementReferenceException` (configurable via `maxRetry` in config).
5. **SLF4J + Logback:** Asynchronous logging with daily rolling files (30-day retention) and standard log formatting.
6. **Allure Report:** Rich HTML report generation out-of-the-box via `mvn allure:report`.
7. **Auto Screenshot & Video:** Automatically captures screenshots and records execution videos on test failures, with independent video capture per thread.
8. **Multi-environment Support:** Pre-configured environments for Staging, Production, and Nightlight, switchable via the `environment` parameter in configurations.
9. **Isolated Test Data Models:** Dedicated data model classes (e.g., `UserAccount`) decouple test data from test scripts. Models include `toFormUrlEncoded()` for API integration.
10. **API Utilities:** `AccountAPI` provides `createAccount()` and `deleteAccount()` for test data preparation and cleanup via API, avoiding slow UI-based setup.
11. **Auto Cleanup:** Tests that create accounts use `@AfterMethod(alwaysRun = true)` to automatically clean up via API if the test fails mid-way.
12. **Component Pattern:** Reusable UI components (e.g., `NotificationComponent`) eliminate duplicate code across similar pages.

---

## Installation & Getting Started

### Step 1: System Requirements & Prerequisites

#### 1. Java Development Kit (JDK 11)
The framework is optimized for **Java 11**.
- **Download:** Get JDK 11 from [Eclipse Temurin](https://adoptium.net/temurin/releases/?version=11) or [Azul Zulu](https://www.azul.com/downloads/?package=jdk#zulu).
- **Environment Variables Configuration (Windows):**
  1. Open *Environment Variables* on Windows.
  2. Create a new system variable named `JAVA_HOME` pointing to the JDK installation directory (e.g., `C:\Program Files\Eclipse Adoptium\jdk-11.x.x`).
  3. Locate the `Path` variable in the system variables list, click *Edit*, select *New*, and add: `%JAVA_HOME%\bin`.
- **Verify Installation:** Open your Terminal/Command Prompt and run:
  ```bash
  java -version
  ```
  *(It should display Java version 11.x)*

#### 2. Apache Maven
- **Download:** Download the binary zip archive from the [Apache Maven website](https://maven.apache.org/download.cgi).
- **Environment Variables Configuration (Windows):**
  1. Extract the downloaded zip archive to a permanent directory (e.g., `C:\maven`).
  2. Create a new system variable named `MAVEN_HOME` with the path to the extracted folder (`C:\maven`).
  3. Add `%MAVEN_HOME%\bin` to the `Path` system variable.
- **Verify Installation:** Open Terminal/Command Prompt and run:
  ```bash
  mvn -version
  ```

#### 3. Allure Commandline
To open and view the generated reports locally, you need the Allure CLI installed.
- **Windows (Recommended using Scoop):**
  ```powershell
  scoop install allure
  ```
  *Manual Installation:* Download the latest Allure zip release from [Allure GitHub Releases](https://github.com/allure-framework/allure2/releases), extract it, and add the path to the `bin` directory to your system `Path`.
- **macOS (Using Homebrew):**
  ```bash
  brew install allure
  ```
- **Verify Installation:**
  ```bash
  allure --version
  ```

---

### Step 2: Clone and Open Project in IDE

1. **Clone or download the project** source code to your machine.
2. **Open with IntelliJ IDEA (Recommended):**
   - Click **Open** -> Select the project root folder containing the `pom.xml` file.
   - The IDE will auto-detect the Maven project and download dependencies. This may take a few minutes on the first run.
   - Set the project SDK to Java 11 (Go to *File* -> *Project Structure* -> *Project* -> Select Java 11 SDK).

---

### Step 3: Test Configuration

Open the `src/test/resources/settings/configs.properties` file to adjust the run settings:

```properties
# 1. Environment: staging | production | nightlight
environment = staging

# 2. Browser: chrome | firefox | edge
driver = chrome

# 3. Enable screenshot capture on test completion/fail: true | false
capture = true

# 4. Enable video recording of test runs: true | false (only supported on Windows)
video = false

# 5. Output detailed logs to console and log files: true | false
logger = true

# 6. Maximum retry attempts for stale element exceptions (default: 2)
maxRetry = 2
```

*Note:* URLs for each environment are defined in `staging.properties`, `production.properties`, and `nightlight.properties`. Each environment file must define both `applicationUrl` and `apiBaseUrl`.

---

### Step 4: Running Tests

You can execute the test suites via Command Line / Terminal or directly within the IDE:

#### Method 1: Using Maven in Command Line
Open a terminal in the root folder of the project and execute the relevant command:

```bash
# Run the complete test suite (testng.xml)
mvn clean test

# Run a specific test class
mvn test -Dtest=RegisterTest

# Run a specific test method
mvn test -Dtest=RegisterTest#testRegisterUser
```

#### Method 2: Running via IntelliJ IDEA
- Right-click the test runner suite XML configuration file (e.g., `testng.xml`) and select **Run**.
- Or open the test script class (e.g., `RegisterTest.java`) and click the **green play button** next to the class or method definition.

---

### Step 5: Test Reports & Logs

After the test run completes, output files are located in the following directories:
1. **Application Logs:** Saved at `test-reports/logs/app.log`, capturing detailed framework operations (clicks, key entry, page loads, step events).
2. **Failure Screenshots:** Automatically captured and stored under `test-reports/screenshots/<timestamp>/` if configured.
3. **Execution Videos:** Recorded and saved under `test-reports/videos/<timestamp>/` (if `video=true`).
4. **Allure HTML Report:**
   *Note:* Due to browser CORS policies, opening the static `index.html` file in `target/site/allure-maven-plugin` directly might result in a blank page. Open the report using one of the following commands:
   
   - **Method 1: Using Maven Allure Serve (Recommended)**
     Launches a local web server to serve the report and opens it in your default browser:
     ```bash
     mvn allure:serve
     ```
   - **Method 2: Using Allure CLI**
     After generating the report with `mvn allure:report`, run:
     ```bash
     allure open target/site/allure-maven-plugin
     ```

---

## Parallel Execution

By default, the test suite in `testng.xml` is configured to run tests in parallel using TestNG methods mode (`parallel="methods"`) with a thread count of 4:

```xml
<suite name="NHIMS Regression Suite" parallel="methods" thread-count="4">
```
Supported parallel modes: `methods` | `classes` | `tests` | `none`.

---

## Advanced Configuration & Extension

### Adding a New Test Environment
1. Create a properties file at `src/test/resources/settings/<new_env>.properties` defining `applicationUrl` and `apiBaseUrl`.
2. Define the environment type in the `Configs.Environment` enum.
3. Add mapping condition inside `HFile.getConfigEnvironment()`.

### Supporting a New Browser
Open `BrowserFactory.java` and add your implementation:
```java
case "safari":
    return new SafariDriver();
```

### Adding a New Page Object Class
```java
public class MyNewPage extends BasePage {
    private static final Control myElement = new Control("//div[@id='my-element']");

    @Step("Perform action on my element")
    public static void doSomething() {
        Logger.info("Perform action on my element");
        myElement.get().click();
    }
}
```

### Adding a New API Endpoint
1. Add endpoint constant to `APIConst.java`:
   ```java
   public static final String NEW_ENDPOINT = "/newEndpoint";
   ```
2. Add response code constant if needed:
   ```java
   public static final int SOME_STATUS = 300;
   ```
3. Create the API method in the appropriate API utility class (e.g., `AccountAPI`).

### Adding a New Config Key
1. Add key to `configs.properties` with default value
2. Add enum value to `Configs.ConfigFile` or `Configs.EnvironmentConfig`
3. Access via `HFile.getConfig(ConfigFile.keyName)` or `HFile.getConfigEnvironment(EnvironmentConfig.keyName)`

---

## Existing Page Objects Reference

| Page Class | Key Methods |
|---|---|
| `HomePage` | `isHomePageVisible()`, `clickSignupLogin()`, `clickDeleteAccount()`, `getLoggedInUserText()` |
| `SignupLoginPage` | `isNewUserSignupVisible()`, `getNewUserSignupText()`, `enterSignupNameAndEmail(name, email)`, `clickSignup()` |
| `RegisterPage` | `isEnterAccountInfoVisible()`, `getEnterAccountInfoText()`, `getPrefilledName()`, `getPrefilledEmail()`, `fillAccountDetails(...)`, `selectNewsletter()`, `selectSpecialOffers()`, `fillAddressDetails(...)`, `clickCreateAccount()` |
| `AccountCreatedPage` | `isAccountCreatedVisible()`, `getAccountCreatedText()`, `clickContinue()` |
| `AccountDeletedPage` | `isAccountDeletedVisible()`, `getAccountDeletedText()`, `clickContinue()` |

### Existing API Utilities

| Class | Methods |
|---|---|
| `AccountAPI` | `createAccount(UserAccount)` — POST create account, `deleteAccount(email, password)` — DELETE account |

### Existing Components

| Component | Description |
|---|---|
| `NotificationComponent` | Reusable title label + continue button pattern (used by AccountCreatedPage and AccountDeletedPage) |

---

## AI-Assisted Test Script Generation

This project includes a detailed AI generation guide at `.kilo/agent/test-generation-guide.md` that enables AI tools (like Kilo, ChatGPT, Claude) to generate test scripts that perfectly match the project's architecture and coding conventions.

### How to Use

When you want AI to generate a new test script from a test case, use the following prompt template:

```
Dựa vào file .kilo/agent/test-generation-guide.md, hãy tạo test script cho test case sau:

**Test Case ID:** TC000X
**Test Case Name:** [Tên test case]
**Test Steps:**
1. [Step 1 description]
2. [Step 2 description]
3. ...

**Expected Results:**
1. [Expected result 1]
2. [Expected result 2]
```

### Prompt Examples

#### Example 1: Tạo test script đơn giản

```
Dựa vào file .kilo/agent/test-generation-guide.md, hãy tạo test script cho test case sau:

**Test Case ID:** TC0002
**Test Case Name:** Login User with correct email and password
**Precondition:** User already registered via API
**Test Steps:**
1. Launch browser
2. Navigate to url 'http://automationexercise.com'
3. Verify that home page is visible successfully
4. Click on 'Signup / Login' button
5. Verify 'Login to your account' is visible
6. Enter correct email address and password
7. Click 'login' button
8. Verify that 'Logged in as username' is visible

**Expected Results:**
- Home page visible
- Login form visible
- User logged in successfully
```

#### Example 2: Tạo test script có prepare data qua API

```
Dựa vào file .kilo/agent/test-generation-guide.md, hãy tạo test script cho test case sau:

**Test Case ID:** TC0003
**Test Case Name:** Login User with incorrect email and password
**Precondition:** Create a user account via API before test
**Test Steps:**
1. Launch browser
2. Navigate to url 'http://automationexercise.com'
3. Verify that home page is visible successfully
4. Click on 'Signup / Login' button
5. Verify 'Login to your account' is visible
6. Enter incorrect email address and password
7. Click 'login' button
8. Verify error 'Your email or password is incorrect!' is visible

**Expected Results:**
- Error message displayed for wrong credentials
```

#### Example 3: Tạo test script + page object mới

```
Dựa vào file .kilo/agent/test-generation-guide.md, hãy tạo test script VÀ page object cho test case sau:

**Test Case ID:** TC0005
**Test Case Name:** Register User with existing email
**Test Steps:**
1. Launch browser
2. Navigate to url 'http://automationexercise.com'
3. Verify that home page is visible successfully
4. Click on 'Signup / Login' button
5. Verify 'New User Signup!' is visible
6. Enter name and already registered email
7. Click 'Signup' button
8. Verify error 'Email Address already exist!' is visible

**Notes:** Tạo thêm LoginPage nếu chưa có, thêm method getSignupErrorMessage() vào SignupLoginPage
```

#### Example 4: Tạo nhiều test case cùng lúc

```
Dựa vào file .kilo/agent/test-generation-guide.md, hãy tạo test scripts cho các test cases sau, gộp chung vào 1 class nếu cùng feature:

**Feature:** Contact Us Form

**TC0015:** Contact Us Form
- Navigate to home page
- Click 'Contact Us' button
- Verify 'GET IN TOUCH' is visible
- Enter name, email, subject, message and upload file
- Click 'Submit' button
- Click OK on alert
- Verify success message 'Success! Your details have been submitted successfully.' is visible
- Click 'Home' button
- Verify home page visible

**TC0016:** Verify error when submit contact form without required fields
- Navigate to home page
- Click 'Contact Us' button
- Click 'Submit' button without filling fields
- Verify error message is displayed
```

### Tips for Best Results

1. **Always reference the guide file** — Start every prompt with `Dựa vào file .kilo/agent/test-generation-guide.md`
2. **Provide clear test steps** — Numbered steps with expected results produce the most accurate scripts
3. **Mention prerequisites** — If a test needs a pre-created user, mention `Precondition: Create a user account via API before test` so AI includes `AccountAPI.createAccount()` in the setup and `@AfterMethod` cleanup
4. **Specify new pages needed** — If the test navigates to a page not in the existing page objects, mention it explicitly
5. **Keep test cases focused** — One test case per `@Test` method; AI will group related tests in the same class with shared `@Story`
6. **Use Vietnamese or English** — The AI understands both, but test case steps in English produce more consistent code

---

## AutomationExercise API Reference

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
**Important:** HTTP status is always 200. The actual response code is in the JSON `responseCode` field.
