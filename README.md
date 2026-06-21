# Selenium Test Automation Framework (TAF)

<div align="center">

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-4.41.0-43B02A?style=for-the-badge&logo=selenium&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-7.11.0-FF6C37?style=for-the-badge)
![Allure](https://img.shields.io/badge/Allure-2.29.1-brightgreen?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

**An enterprise-grade, thread-safe UI test automation framework built on Selenium 4 + TestNG + Allure,
applied to [AutomationExercise.com](https://automationexercise.com/) — a real-world e-commerce web application.**

</div>

---

## 📋 Table of Contents

- [Overview](#overview)
- [Application Under Test](#application-under-test)
- [Key Features](#key-features)
- [Architecture & Design Patterns](#architecture--design-patterns)
- [Complete Project Structure](#complete-project-structure)
- [Technology Stack](#technology-stack)
- [Configuration Reference](#configuration-reference)
- [Prerequisites & Installation](#prerequisites--installation)
- [Running Tests](#running-tests)
- [Test Coverage](#test-coverage)
- [Example Test Cases](#example-test-cases)
- [Reporting & Artifacts](#reporting--artifacts)
- [Roadmap](#roadmap)

---

## Overview

TAF is a **production-quality Selenium framework** designed for scale, maintainability, and reliability. Rather than a bare-bones proof-of-concept, it solves real problems that emerge in enterprise automation:

- **Zombie driver processes** from aborted test runs — solved via JVM Shutdown Hooks
- **Cross-thread driver contamination** in parallel runs — solved via `ThreadLocal` + `ThreadGuard`
- **Lost failure context** (screenshots captured after driver quit) — solved via Allure `TestLifecycleListener`
- **Null recorder references** when video is disabled — solved via the Null Object Pattern
- **Flaky test noise** in CI pipelines — mitigated via configurable `RetryAnalyzer`

The framework strictly follows the **Page Object Model (POM)** with a **Fluent API**, making test code read like plain English while keeping all driver and locator logic hidden from the test layer.

---

## Application Under Test

Tests run against **[AutomationExercise.com](https://automationexercise.com/)** — a purpose-built e-commerce demo site. The functional test suites cover the full customer journey:

| Module | Tested Scenarios |
|--------|-----------------|
| **User Management** | Registration (valid/duplicate), Login (valid/invalid email/invalid password), Logout |
| **Product Browsing** | Search, Filter by category, Filter by brand, View product details |
| **Cart Management** | Add to cart, Remove from cart, Quantity updates |
| **Checkout** | Address verification, Order comment, Place order |
| **Payment** | Card payment, Order success validation |
| **End-to-End** | Full customer journey: login → browse → add to cart → checkout → pay |

---

## Key Features

### ✅ Core Automation

- **Page Object Model (POM)** — Clean separation of locators, page actions, and test logic.
- **Fluent / Chained API** — Page methods return the next page object, making tests read like user stories.
- **Component-Based Pages** — Reusable UI blocks (`NavigationBar`, `ProductGallery`, `CategoryAndBrandSidebar`, `ProductAddedToCartWindow`) are composed into pages via `BasePage`, not inherited.
- **Multi-Browser Support** — Chrome, Firefox, and Edge via enum-based `OptionsFactory` / `WebDriverFactory`.
- **Selenium Grid Ready** — Remote execution configured via `seleniumGrid.properties` (hub URL, port, path).
- **Headless Mode** — Toggle via `DriverOptions.properties` without any code changes.

### 📊 Reporting & Visualization

- **Allure Reports** — Interactive HTML reports with step-by-step execution breakdowns, severity levels, and epics/features/stories hierarchy.
- **Environment Card** — Automatically writes browser type, OS, and base URL to the Allure environment widget at suite start (via `AllureLifecycleListener` + `allure-environment-writer`).
- **Video Recording** — Desktop or application-window capture using FFmpeg via JAVE. Configurable resolution, frame rate, capture mode, and save-on-pass/fail policy.
- **Screenshot Capture** — Automatic screenshots on test failure, attached directly to the Allure report.
- **Log Attachments** — Log4j2 execution logs attached to each failing test result in Allure.
- **Artifact Repository** — `ArtifactRepository` maintains a centralized thread-safe registry of all produced screenshots and videos for the current run.

### 🔍 Logging & Diagnostics

- **Log4j2** — Structured logging to both console and rolling file appenders.
- **Execution ID** — A unique `yyyy-MM-dd_HH-mm-ss` timestamp is generated at suite start and injected as a JVM system property (`execution.id`), used to name log files, screenshot folders, and video files — eliminating overwrites across parallel runs.
- **Custom Listeners** — Three specialized TestNG listeners handle the full test lifecycle with zero boilerplate in test classes.

### 🎯 Advanced Capabilities

- **`@UITest` Annotation** — A custom marker interface that the `DriverLifecycleListener` checks at runtime. Only classes/methods annotated with `@UITest` trigger driver initialization, preventing unnecessary browser launches for future API or non-UI tests.
- **`@APITest` Annotation** — Companion annotation ready for the planned REST Assured integration.
- **Page Contracts / Interfaces** — `ISignupFlow` interface enforces a consistent signup flow API across pages that participate in the signup sequence.
- **Retry Analyzer** — `RetryAnalyzer` + `RetryTransformer` for configurable test retry on failure.
- **Allure Annotations** — `@Epic`, `@Feature`, `@Story`, `@Owner`, `@Severity`, `@Description`, `@Step` used throughout for maximum report expressiveness.

---

## Architecture & Design Patterns

This framework goes significantly beyond a standard POM setup by applying well-known software engineering patterns to solve automation-specific problems:

### 1. Invisible Driver Management
Test authors **never** instantiate or pass `WebDriver` anywhere. The `DriverLifecycleListener` detects `@UITest`-annotated test instances at `onTestStart` and automatically calls `DriverManager.initDriver()`. Page Objects retrieve the driver via `DriverManager.getDriver()` when they need it. Tests simply call page methods.

```
@UITest (on BaseTest) → DriverLifecycleListener.onTestStart() → DriverManager.initDriver()
                                                               → WebDriverFactory.createDriver()
                                                               → OptionsFactory.CHROME.createOptions()
```

### 2. ThreadLocal + ThreadGuard — Race-Condition-Proof Drivers
`DriverManager` stores each driver instance in a `ThreadLocal<WebDriver>`. Every stored driver is additionally wrapped with `ThreadGuard.protect()`, which throws an exception if a driver is accessed from a thread that didn't create it — making cross-thread contamination physically impossible.

```java
driverThreadLocal.set(ThreadGuard.protect(driver));  // stored per-thread + protected
```

### 3. Fail-Safe Shutdown Hook
Forceful test abortion (IDE stop button, CI kill signal) normally leaves `chromedriver.exe` processes running. The framework registers a JVM shutdown hook at `onExecutionStart` that iterates all `activeDrivers` and quits each one:

```java
Runtime.getRuntime().addShutdownHook(new Thread(DriverManager::stopAllDrivers));
```
`stopAllDrivers()` is also smart enough to unwrap `ThreadGuard` proxies before calling `.quit()`.

### 4. Null Object Pattern — Video Recording
When `video_recording=false`, `RecordingManager.startRecording()` doesn't store `null` — it stores a `NoOpVideoRecorder` instead. Every subsequent `stop()` / `forceStop()` call simply does nothing, eliminating all `if (recorder != null)` guards in the teardown path.

```java
// No null checks needed anywhere in teardown:
recorder.set(new NoOpVideoRecorder());
```

### 5. Context-Aware Allure Attachment (beforeTestStop Hook)
The most subtle pattern: screenshots and logs must be attached to Allure **before** the driver is quit, otherwise there is no browser context to capture from. `AllureLifecycleListener` implements Allure's `TestLifecycleListener` and overrides `beforeTestStop(TestResult result)`. This fires **before** the TestNG `@AfterMethod` that quits the driver, guaranteeing the capture window is always open.

### 6. Abstracted Actions Layer
Raw Selenium and `WebDriverWait` calls are **never** written directly in Page Objects. Instead, all element interactions are centralized in static utility classes:

| Class | Responsibility |
|-------|---------------|
| `ElementsActions` | click, sendText, getText, hover, scroll (JS + Selenium), upload, dropdown, isDisplayed |
| `BrowserActions` | navigate to URL |
| `AlertActions` | accept, dismiss, get alert text |

Every action internally uses a `WebDriverWait` configured from `waits.properties` (timeout, polling interval, ignored exceptions list), so flakiness from timing is handled in one place.

### 7. Enum-Based Driver Factory
Browser creation is delegated through two enum layers:

- `WebDriverFactory` (enum) — One constant per browser; each overrides `createDriver()` to return the appropriate `WebDriver` subtype.
- `OptionsFactory` (enum) — One constant per browser; each overrides `createOptions()` to build browser-specific `Options` objects, including headless mode from config.

Adding a new browser capability is a single-file change in the relevant `OptionsFactory` constant.

### 8. Guaranteed Unique Artifacts
`TimeStampCreator.getCurrentTime()` produces a `yyyyMMdd_HHmmssSSS` string that is used as:
- The registration email suffix in dynamic test data (prevents duplicate account errors)
- The filename prefix for screenshots and video recordings
- The Log4j2 `execution.id` system property, ensuring log files from different runs never collide

---

## Complete Project Structure

```text
TAF/
├── pom.xml                                    # Maven build — deps, Surefire + AspectJ config, Shade plugin
├── release_notes.md                           # Versioned changelog
│
└── src/
    ├── main/
    │   ├── java/org/blazedemo/
    │   │   ├── annotations/
    │   │   │   ├── UITest.java                # Runtime marker — triggers driver lifecycle
    │   │   │   └── APITest.java               # Runtime marker — future API test routing
    │   │   │
    │   │   ├── config/
    │   │   │   ├── Configuration.java         # Core properties loader (validated, throws on missing keys)
    │   │   │   ├── DriverConfiguration.java   # browser_type, headless_mode, execution_mode accessors
    │   │   │   ├── EnvironmentConfiguration.java # BaseURL, Port accessors
    │   │   │   ├── ScreenshotConfiguration.java  # Screenshot format config
    │   │   │   ├── VideoRecordingConfiguration.java # Video enable/save policy/resolution accessors
    │   │   │   └── WaitConfiguration.java     # Timeout, polling interval, ignored exceptions
    │   │   │
    │   │   ├── customlisteners/
    │   │   │   ├── DriverLifecycleListener.java  # ITestListener + IExecutionListener: init/quit + shutdown hook
    │   │   │   ├── AllureLifecycleListener.java  # TestLifecycleListener + ISuiteListener: env card + attachments
    │   │   │   ├── RecordingListener.java         # ITestListener: start/stop video per test
    │   │   │   ├── SuiteLevelListener.java        # ISuiteListener: cleanup recorders + auto-generate report
    │   │   │   ├── RetryAnalyzer.java             # IRetryAnalyzer: retry failed tests up to N times
    │   │   │   └── RetryTransformer.java          # IAnnotationTransformer: applies RetryAnalyzer globally
    │   │   │
    │   │   ├── drivers/
    │   │   │   ├── DriverManager.java         # ThreadLocal store + ThreadGuard + activeDrivers registry
    │   │   │   ├── WebDriverFactory.java      # Enum: CHROME/EDGE/FIREFOX driver creation
    │   │   │   ├── OptionsFactory.java        # Enum: CHROME/EDGE/FIREFOX options with headless support
    │   │   │   └── IOptionsFactory.java       # Interface: planned refactor target for OptionsFactory enum
    │   │   │
    │   │   ├── media/
    │   │   │   ├── MediaUtilities.java        # Base: unique file naming, directory creation helpers
    │   │   │   ├── ScreenshotManager.java     # Capture + save screenshots; attach to Allure
    │   │   │   └── videorecorder/
    │   │   │       ├── VideoRecorder.java     # Interface: start(path) / stop() contract
    │   │   │       ├── FfmpegVideoRecorder.java  # FFmpeg/JAVE implementation
    │   │   │       ├── NoOpVideoRecorder.java    # Null Object: safe no-op when recording disabled
    │   │   │       ├── RecorderType.java         # Enum: FFMPEG recorder types
    │   │   │       └── RecordingManager.java     # ThreadLocal recorder + policy (save on pass/fail)
    │   │   │
    │   │   ├── pages/
    │   │   │   ├── BasePage.java              # Abstract: exposes NavigationBar; declares navigate()
    │   │   │   ├── HomePage.java              # Home page PO — product gallery, cart interactions
    │   │   │   ├── SignUpAndLoginPage.java     # Login + initial signup form PO
    │   │   │   ├── SignUpPage.java             # Full registration form PO
    │   │   │   ├── ProdcutsPage.java          # Products listing PO — search, filter, add to cart
    │   │   │   ├── ProductDetails.java        # Product details PO
    │   │   │   ├── CartPage.java              # Cart PO — view, remove items, proceed to checkout
    │   │   │   ├── CheckoutPage.java          # Checkout PO — address verification, order comment
    │   │   │   ├── PaymentPage.java           # Payment form PO — card details, submit
    │   │   │   ├── ContactUSPage.java         # Contact Us stub PO
    │   │   │   ├── TestCasesPage.java         # Test Cases stub PO
    │   │   │   ├── APITestingPage.java        # API Testing stub PO
    │   │   │   ├── ExampleModel.java          # Template/example PO
    │   │   │   │
    │   │   │   ├── components/
    │   │   │   │   ├── NavigationBar.java             # Site-wide nav — all nav button actions + active-state check
    │   │   │   │   ├── ProductGallery.java            # Product card grid — add/view/search/filter
    │   │   │   │   ├── CategoryAndBrandSidebar.java   # Sidebar filter component
    │   │   │   │   └── ProductAddedToCartWindow.java  # Modal after adding product
    │   │   │   │
    │   │   │   ├── contracts/
    │   │   │   │   └── ISignupFlow.java       # Interface enforcing signup(RegistrationData) across pages
    │   │   │   │
    │   │   │   └── dto/
    │   │   │       ├── LoginCredentials.java  # DTO: email + password (Jackson + Lombok)
    │   │   │       └── RegistrationData.java  # DTO: all 19 registration fields (Jackson + Lombok)
    │   │   │
    │   │   └── utils/
    │   │       ├── TimeStampCreator.java      # Unique timestamp generator for artifact naming
    │   │       ├── FileUtilities.java         # File copy, delete, exists checks
    │   │       ├── LoggerManager.java         # Log4j2 logger factory wrapper
    │   │       ├── OSUtils.java               # OS detection (WINDOWS / LINUX / MAC)
    │   │       ├── TerminalUtils.java         # Shell command execution helper
    │   │       ├── TestCaseStatus.java        # Enum: PASSED / FAILED / SKIPPED
    │   │       │
    │   │       ├── actions/
    │   │       │   ├── BaseAction.java        # Builds WebDriverWait from WaitConfiguration
    │   │       │   ├── ElementsActions.java   # click, sendText, getText, hover, scroll, upload, dropdown
    │   │       │   ├── BrowserActions.java    # navigateTo(url)
    │   │       │   └── AlertActions.java      # accept, dismiss, getText for browser alerts
    │   │       │
    │   │       ├── assertions/
    │   │       │   └── HardAssertions.java    # TestNG Assert wrappers with logging
    │   │       │
    │   │       ├── datareaders/
    │   │       │   ├── JsonReader.java        # Jackson-based: deserialize JSON to DTO / get single property
    │   │       │   └── PropertiesReader.java  # Properties file reader with classpath support
    │   │       │
    │   │       └── reporting/
    │   │           ├── ArtifactRepository.java   # Thread-safe registry for produced video/screenshot paths
    │   │           └── ReportingManager.java      # Collects Allure attachments; triggers report generation
    │   │
    │   └── resources/
    │       ├── META-INF/services/             # SPI bindings for Allure listeners (auto-discovery)
    │       ├── log4j2.properties              # Console + file appenders; execution.id in filename
    │       └── config/
    │           ├── DriverOptions.properties   # browser_type, headless_mode, execution_mode, window_maximize
    │           ├── environment.properties     # BaseURL (automationexercise.com), Port, apiEndpoint
    │           ├── waits.properties           # timeout_seconds, polling_interval_millis, ignored_exceptions
    │           ├── video.properties           # recording toggle, output dir, resolution, capture mode, policy
    │           ├── screenshot.properties      # Screenshot output format
    │           ├── seleniumGrid.properties    # remoteHost, remotePort, hubPath for Grid execution
    │           └── db.properties             # Database connection placeholder (future use)
    │
    └── test/
        ├── java/org/blazedemo/tests/
        │   ├── basetest/
        │   │   └── BaseTest.java              # @UITest + sets execution.id; @AfterMethod cleanup
        │   ├── ui/
        │   │   ├── LoginTestcases.java        # 3 tests: valid login, invalid email, invalid password
        │   │   ├── RegistrationTestcases.java # 2 tests: valid registration, duplicate account
        │   │   ├── ProductsTestcases.java     # 11 tests: add, view, search, filter by category/brand
        │   │   ├── CartTestcases.java         # Cart-focused test cases
        │   │   ├── checkoutTestcases.java     # Checkout + address verification test
        │   │   ├── productDetailsTestcases.java # Product detail page tests
        │   │   └── E2ETests.java             # Full journey: login → cart → checkout → pay
        │   └── api/                           # Placeholder for future REST Assured API tests
        │
        └── resources/
            ├── allure.properties              # Allure results directory configuration
            └── testdata/
                ├── registered_user.json       # Existing user credentials + full profile data
                ├── valid_login_credentials.json # Login-only credentials
                ├── payment.json               # Card details for payment tests
                └── product_details.json       # Product data for detail page tests
```

---

## Technology Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 21 | Language — records, sealed classes, text blocks ready |
| **Selenium WebDriver** | 4.41.0 | Browser automation |
| **TestNG** | 7.11.0 | Test runner, parallel execution, listeners |
| **Allure TestNG** | 2.29.1 | Interactive HTML reporting |
| **Log4j2** | 2.25.4 | Structured logging (console + rolling file) |
| **Lombok** | 1.18.46 | `@Getter`, `@Setter`, `@Log4j2` to eliminate boilerplate |
| **Jackson Databind** | 2.17.0 | JSON test data deserialization to DTOs |
| **AspectJ Weaver** | 1.9.25.1 | AOP weaving required by Allure `@Step` instrumentation |
| **JAVE (ws.schild)** | 3.5.0 | FFmpeg wrapper for cross-platform video recording |
| **video-recorder-testng** | 2.0 | TestNG video recording support |
| **Commons IO** | 2.22.0 | File operations (copy, delete, path utilities) |
| **allure-environment-writer** | 1.0.0 | Write environment variables to Allure environment widget |
| **Maven Surefire** | 3.5.5 | Test execution plugin with AspectJ agent configuration |
| **Maven Shade** | 3.5.0 | Uber-JAR with `ServicesResourceTransformer` to merge SPI descriptors |

---

## Configuration Reference

All framework behavior is controlled through property files in `src/main/resources/config/`. No code changes are needed for environment or browser switching.

### `DriverOptions.properties`
```properties
browser_type=chrome          # chrome | edge | firefox
headless_mode=false          # true | false
execution_mode=local         # local | remote (Selenium Grid)
window_maximize=true         # maximize browser on start
url=http://localhost:4444/wd/hub  # Grid hub URL (used when execution_mode=remote)
block_notification_requests=true  # suppress browser push notification prompts
```

### `environment.properties`
```properties
environment=local
BaseURL=https://automationexercise.com/
Port=8080
apiEndpoint=http://localhost:8080/api
databaseUrl=jdbc:mysql://localhost:3306/mydb
```

### `waits.properties`
```properties
polling_interval_millis=500
timeout_seconds=10
ignored_exceptions=org.openqa.selenium.NoSuchElementException,\
                   org.openqa.selenium.StaleElementReferenceException,\
                   org.openqa.selenium.ElementClickInterceptedException
custom_timeout_message=Element was not found within the specified timeout.
```

### `video.properties`
```properties
video_recording=false        # true | false — master toggle
video_output_directory=videos
save_record_on_failures=true
save_record_on_success=true
delete_if_test_passed=true   # overrides save_record_on_success if true
capture_mode=desktop         # desktop | application
window_title="Google Chrome" # used when capture_mode=application
offset_x=0
offset_y=0
video_size=1920x1080
frame_rate=10
extension=.mp4
```

### `seleniumGrid.properties`
```properties
remoteHost=http://localhost
remotePort=4444
hubPath=/wd/hub
```

---

## Prerequisites & Installation

### Requirements
- **JDK 21+** — [Download from Adoptium](https://adoptium.net/)
- **Maven 3.8+** — must be on your system `PATH`
- **A browser installed** — Chrome, Edge, or Firefox (Selenium Manager handles driver binaries automatically in Selenium 4)
- **FFmpeg** — only required when `video_recording=true`

### Setup

```bash
# 1. Clone the repository
git clone <repository_url>
cd TAF

# 2. Install dependencies (skip tests on first install)
mvn clean install -DskipTests

# 3. Verify your browser choice in config
# src/main/resources/config/DriverOptions.properties
# browser_type=chrome
```

---

## Running Tests

### Full Suite
```bash
mvn clean test
```

### Single Test Class
```bash
mvn clean test -Dtest=LoginTestcases
mvn clean test -Dtest=ProductsTestcases
mvn clean test -Dtest=E2ETests
```

### Headless Mode (no browser window)
Edit `src/main/resources/config/DriverOptions.properties`:
```properties
headless_mode=true
```
Then run normally:
```bash
mvn clean test
```

### Remote Execution via Selenium Grid
```properties
# DriverOptions.properties
execution_mode=remote
url=http://<grid-hub-host>:4444/wd/hub
```
```bash
mvn clean test
```

### Generate & View Allure Report
```bash
# Serve the interactive report (starts a local server):
mvn allure:serve

# Or generate static HTML to allure-report/ directory:
mvn allure:report
```

---

## Test Coverage

| Test Class | # Tests | Feature Area |
|------------|---------|-------------|
| `LoginTestcases` | 3 | Valid login, invalid email, invalid password |
| `RegistrationTestcases` | 2 | New account creation, duplicate email rejection |
| `ProductsTestcases` | 11 | Add to cart, view, search, filter by category/brand (valid + invalid cases) |
| `CartTestcases` | — | Cart item management |
| `checkoutTestcases` | 1 | Full checkout with address data verification |
| `productDetailsTestcases` | — | Product details page assertions |
| `E2ETests` | 1 | Login → browse → add to cart → remove item → checkout → payment → success |
| **Total** | **~20** | **Full customer journey coverage** |

Each test class is annotated with `@Epic`, `@Feature`, `@Story`, and `@Severity`, giving the Allure report a full business-readable hierarchy.

---

## Example Test Cases

### Fluent E2E Test
The full customer journey in a single readable chain — no driver references, no explicit waits:

```java
@Epic("Automation Exercise")
@Feature("End-to-End Test")
@Story("System Test")
public class E2ETests {

    @Description("Testing Full E2E Scenario")
    @Test
    public void validE2EScenario() {
        LoginCredentials credentials = getTestDataFromClasspath(
                "testdata/registered_user.json", LoginCredentials.class);

        new SignUpAndLoginPage().navigate()
                .login(credentials)
                .validateLoginSuccessfulRedirection()
                .verifyOnHomePage()
                .addProductToCart("Blue Top")
                .validateProductAddedToCart()
                .continueShopping()
                .addProductToCart("Men Tshirt")
                .viewCart()
                .removeProductFromCart("Blue Top")
                .checkout()
                .writeCommentOnOrder("Order comment")
                .placeOrder()
                .pay(nameOnCard, cardNumber, cvc, expiryMonth, expiryYear)
                .validateOrderSuccessful()
                .continueToHomePage();
    }
}
```

### Data-Driven Registration with DTO
Dynamic test data built programmatically with a unique email, or loaded from JSON:

```java
// Inline construction with unique timestamp-based email
RegistrationData data = new RegistrationData(
        "Mr.", "Alaa",
        "user" + TimeStampCreator.getCurrentTime() + "@gmail.com",
        "password123", "19", "February", "2002",
        "Alaa", "Abdelmoneam", "Tesla",
        "123 Main St", "Suite 4", "India", "Mombay", "Mumbai",
        "400001", "9876543210", true, true
);

// Or loaded entirely from JSON:
RegistrationData data = getTestDataFromClasspath(
        "testdata/registered_user.json", RegistrationData.class);

new SignUpAndLoginPage().navigate()
        .signup(data)
        .validateSignupPageRedirection()
        .signup(data)
        .clickOnContinueButton()
        .verifyOnHomePage();
```

### Negative Test — Invalid Login
```java
@Test
public void invalidLoginPassword() {
    LoginCredentials credentials = getTestDataFromClasspath(
            "testdata/registered_user.json", LoginCredentials.class);

    credentials.setPassword("wrong" + TimeStampCreator.getCurrentTime());

    new SignUpAndLoginPage()
            .navigate()
            .login(credentials)
            .validateNoRedirectionHappened()
            .verifyInvalidLoginErrorAppears();
}
```

### Product Filtering with Category + Brand
```java
@Test
public void filterProductsByValidCategory() {
    new ProdcutsPage().navigate().filterByCategory("Men", "Tshirts");
}

@Test
public void filterProductsByValidBrand() {
    new ProdcutsPage().navigate().filterByBrand("Polo");
}
```

---

## Reporting & Artifacts

### Allure Report Structure
The Allure report is organized using a three-level hierarchy applied to all test classes:

```
Epic: Automation Exercise
├── Feature: User Management
│   ├── Story: User Login      → LoginTestcases
│   └── Story: User Registration → RegistrationTestcases
├── Feature: Cart Management
│   └── Story: Checkout        → checkoutTestcases
└── Feature: End-to-End Test
    └── Story: System Test     → E2ETests
```

### What's Attached to Each Test Result
- **Steps** — Every page method annotated with `@Step("...")` appears as a drillable step in Allure
- **Screenshot** — Captured on test failure via `AllureLifecycleListener.beforeTestStop()`
- **Log file** — The Log4j2 execution log for the current run ID
- **Video** — MP4 recording of the test (if `video_recording=true`)
- **Environment card** — Browser, OS, and target URL shown on the report overview page

### Artifact Lifecycle
```
Test Start   → RecordingListener starts FFmpeg recording
Test Running → AllureLifecycleListener.beforeTestStop() fires FIRST
             → Screenshots + logs attached to Allure result
             → DriverLifecycleListener.onTestFailure() quits driver AFTER
Test End     → RecordingListener stops recording
             → applyRecordingPolicy() decides to keep or delete video
             → ArtifactRepository registers final paths
Suite End    → SuiteLevelListener.onFinish() cleans up stray recorders
             → Optionally auto-generates the HTML report
```

---

## Roadmap

The following improvements are planned to advance this framework further:

1. **Driver Factory Refactoring**
   Replace the `OptionsFactory` enum with dedicated `IOptionsFactory` implementing classes (e.g., `ChromeOptionsFactory`). The `IOptionsFactory.java` interface stub already exists. This removes casting and makes each browser config independently testable.

2. **Selenium Grid / Docker Integration**
   Add a `docker-compose.yml` with a Selenium Grid (Hub + Chrome/Firefox Nodes) and integrate `Testcontainers` to spin up disposable browser containers dynamically — eliminating local browser installation requirements in CI.

3. **CI/CD Pipeline**
   Add `.github/workflows/maven.yml` to run the full suite on every Pull Request, publish Allure reports as GitHub Pages or a workflow artifact.

4. **Log4j2 MDC (Thread Context)**
   Implement Mapped Diagnostic Context so that parallel test logs are tagged with `[threadId]` and `[testName]`, enabling easy filtering of one test's log output from a mixed parallel execution log.

5. **API Testing Layer**
   Expand the `apis/` package using RestAssured. Support hybrid UI/API tests — e.g., seeding test data via API call before a UI test, or asserting back-end state after a UI action. The `@APITest` annotation is already in place.

6. **Database Assertions**
   Implement the `db.properties` connection to enable direct database validation — useful for verifying that form submissions actually persisted to the database, independent of the UI confirmation message.

7. **Visual Regression Testing**
   Integrate a visual comparison tool (e.g., Applitools Eyes or AShot) to detect pixel-level UI regressions automatically, complementing the existing functional coverage.

8. **Mobile Web Testing**
   Add Appium configuration to extend the same POM architecture to mobile browsers (Chrome on Android, Safari on iOS) without rewriting a single page object.

9. **Parameterized Cross-Browser Runs via TestNG XML**
   Add a `testng.xml` suite file that parameterizes `browser_type`, allowing a single `mvn test` invocation to run all tests on Chrome, Firefox, and Edge in parallel.

10. **Test Data Factory**
    Replace hardcoded `RegistrationData` constructor calls with a builder/factory pattern (e.g., using Java Faker for randomized realistic data), improving test isolation and removing data-coupling between test cases.

---

<div align="center">

*Built with ☕ and Selenium 4 — Alaa Abdelmoneam*

</div>
