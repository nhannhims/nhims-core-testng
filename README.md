# NHIMS Core TestNG — Automation Framework

This automation testing project is built using a combination of **Java**, **Selenium WebDriver**, **TestNG**, and dependency management via **Maven**. The framework implements the **Page Object Model (POM)** design pattern combined with custom controls and utilities to optimize performance and ensure thread-safe stability during parallel execution.

---

## 🛠️ Tech Stack

| Component | Version | Purpose |
|:---|:---:|:---|
| Java | 11+ | Programming Language |
| Selenium WebDriver | 4.18.1 | Browser Control |
| TestNG | 7.7.1 | Testing Framework & Parallel Execution |
| Allure Report | 2.25.0 | High-quality HTML Reports |
| SLF4J + Logback | 2.0.7 / 1.4.8 | Thread-safe Asynchronous Logging |
| Monte Screen Recorder | 0.7.7.0 | Screen Recording for Test Execution |
| Maven | 3.x | Build and Project Management |

---

## 📂 Project Structure

```text
nhims-core-testng/
├── src/
├── main/
│   ├── java/com/nhims/
│   │   ├── browsers/      # BrowserExtensions, Navigation, Browsers
│   │   ├── constants/     # Configs, FileConst, JavaScript, TimeConst
│   │   ├── controls/      # Control (stateless), Actions, BaseControl, Keyboards
│   │   ├── drivers/       # BrowserFactory, DriverController, DriverExtensions
│   │   └── utils/         # Logger (SLF4J), RecordVideo, HFile, HDate, HFolder, Convert
│   └── resources/
│       └── logback.xml    # Logging Configuration (Console + RollingFile)
└── test/
    ├── java/com/nhims/
    │   ├── data/          # Test Data Constants
    │   ├── listeners/     # TestListener — lifecycle hooks (screenshot, video, driver)
    │   ├── pages/         # Page Objects: BasePage, HomePage, SignupLoginPage, RegisterPage, etc.
    │   └── scripts/       # Test scripts: RegisterTest
    └── resources/settings/
        ├── configs.properties     # Core configuration (driver, environment, video, capture...)
        ├── staging.properties     # Staging Environment URL
        ├── production.properties  # Production Environment URL
        └── nightlight.properties  # Nightlight Environment URL
├── testng.xml                 # Test Suite Configuration (parallel, thread-count)
├── pom.xml                    # Maven dependencies & plugins
└── README.md
```

---

## 🚀 Key Features

1. **Thread-safe Parallel Execution:** The infrastructure uses `ThreadLocal` for WebDriver instance management, ScreenRecorder, and stateless element searches, ensuring parallel execution without race conditions.
2. **BrowserFactory:** Supports Chrome, Firefox, and Edge via a simple change of `driver=` in the configuration file.
3. **Explicit Wait:** The `Control` class utilizes `WebDriverWait` and `ExpectedConditions` instead of brittle `Thread.sleep` calls.
4. **SLF4J + Logback:** Asynchronous logging with daily rolling files (30-day retention) and standard log formatting.
5. **Allure Report:** Rich HTML report generation out-of-the-box via `mvn allure:report`.
6. **Auto Screenshot & Video:** Automatically captures screenshots and records execution videos on test failures, with independent video capture per thread.
7. **Multi-environment Support:** Pre-configured environments for Staging, Production, and Nightlight, switchable via the `environment` parameter in configurations.

---

## 💻 Installation & Getting Started

Follow these steps to set up and run the tests locally:

### 📑 Step 1: System Requirements & Prerequisites

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

### 📥 Step 2: Clone and Open Project in IDE

1. **Clone or download the project** source code to your machine.
2. **Open with IntelliJ IDEA (Recommended):**
   - Click **Open** -> Select the project root folder containing the `pom.xml` file.
   - The IDE will auto-detect the Maven project and download dependencies. This may take a few minutes on the first run.
   - Set the project SDK to Java 11 (Go to *File* -> *Project Structure* -> *Project* -> Select Java 11 SDK).

---

### ⚙️ Step 3: Test Configuration

Open the [configs.properties](file:///c:/Users/nhan.vuong/Desktop/Course/example/nhims-core/nhims-core-testng/src/test/resources/settings/configs.properties) file to adjust the run settings:

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
```

*Note:* URLs for each environment are defined in [staging.properties](file:///c:/Users/nhan.vuong/Desktop/Course/example/nhims-core/nhims-core-testng/src/test/resources/settings/staging.properties), [production.properties](file:///c:/Users/nhan.vuong/Desktop/Course/example/nhims-core/nhims-core-testng/src/test/resources/settings/production.properties), and [nightlight.properties](file:///c:/Users/nhan.vuong/Desktop/Course/example/nhims-core/nhims-core-testng/src/test/resources/settings/nightlight.properties).

---

### 🚀 Step 4: Running Tests

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
- Right-click the test runner suite XML configuration file (e.g., [testng.xml](file:///c:/Users/nhan.vuong/Desktop/Course/example/nhims-core/nhims-core-testng/testng.xml)) and select **Run**.
- Or open the test script class (e.g., [RegisterTest.java](file:///c:/Users/nhan.vuong/Desktop/Course/example/nhims-core/nhims-core-testng/src/test/java/com/nhims/scripts/RegisterTest.java)) and click the **green play button** next to the class or method definition.

---

### 📊 Step 5: Test Reports & Logs

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

## 👥 Parallel Execution

By default, the test suite in `testng.xml` is configured to run tests in parallel using TestNG methods mode (`parallel="methods"`) with a thread count of 4:

```xml
<suite name="NHIMS Regression Suite" parallel="methods" thread-count="4">
```
Supported parallel modes: `methods` | `classes` | `tests` | `none`.

---

## ⚙️ Advanced Configuration & Extension

### Adding a New Test Environment
1. Create a properties file at `src/test/resources/settings/<new_env>.properties` defining `applicationUrl = <url>`.
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
        myElement.get().click();
    }
}
```
