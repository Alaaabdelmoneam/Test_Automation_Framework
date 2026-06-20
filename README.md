# Test Automation Framework

A comprehensive, enterprise-grade **Java-based UI automation framework** built on Selenium, TestNG, and Allure for testing the Automation Exercise application. This framework follows best practices in test automation architecture, reporting, and maintainability.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Project Structure](#project-structure)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Test Organization](#test-organization)
- [Reporting](#reporting)
- [Best Practices Implemented](#best-practices-implemented)
- [Planned Improvements](#planned-improvements)
- [Contributing](#contributing)

---

## Overview

This framework automates end-to-end UI testing for e-commerce applications. It provides a robust foundation for building scalable, maintainable test suites with comprehensive reporting, video recording, and screenshot capabilities.

**Target Application**: Automation Exercise (e-commerce platform)
**Primary Language**: Java
**Test Framework**: TestNG
**Web Driver Library**: Selenium WebDriver 4.41.0

---

## Key Features

### ✅ Core Automation Features

- **Page Object Model (POM)**: Clean separation of page elements and test logic
- **Multi-browser Support**: Chrome, Firefox, and Edge drivers with factory pattern
- **WebDriver Management**: Automatic driver initialization and cleanup via lifecycle listeners
- **Data-Driven Testing**: JSON-based test data readers with support for credentials and registration data

### 📊 Reporting & Visualization

- **Allure Reporting**: Beautiful, interactive test reports with detailed step-by-step execution
- **Video Recording**: Automatic video capture of test execution for failure analysis
- **Screenshot Capture**: Automatic screenshots on test failures with page title context
- **Artifact Management**: Centralized management of test artifacts (logs, screenshots, videos)
- **Environment Information**: Captures browser version, OS, and URL in reports

### 🔍 Logging & Diagnostics

- **Log4j2 Integration**: Comprehensive logging with configurable levels
- **Test Execution ID**: Unique execution tracking for troubleshooting
- **Custom Listeners**: Test lifecycle listeners for setup, teardown, and reporting

### 🎯 Advanced Capabilities

- **Custom Annotations**: `@UITest` marker for UI test identification
- **Allure Annotations**: Epic, Feature, Story, Owner, Severity levels for test organization
- **Fluent API**: Chainable methods for readable test code
- **Component-Based Architecture**: Reusable navigation bar and page components

---

## Project Structure

```
Test_Automation_Framework/
├── src/
│   ├── main/
│   │   └── java/org/blazedemo/
│   │       ├── annotations/
│   │       │   └── UITest.java                    # Custom test marker
│   │       ├── config/
│   │       │   ├── Configuration.java             # Property file loader
│   │       │   └── DriverConfiguration.java       # WebDriver settings
│   │       ├── customlisteners/
│   │       │   ├── AllureLifecycleListener.java   # Allure test lifecycle
│   │       │   └── DriverLifecycleListener.java   # WebDriver lifecycle
│   │       ├── drivers/
│   │       │   ├── DriverManager.java             # Driver pool management
│   │       │   ├── DriverFactory.java             # Driver factory pattern
│   │       │   └── WebDriverFactory.java          # Browser-specific drivers
│   │       ├── media/
│   │       │   ├── ScreenshotManager.java         # Screenshot utilities
│   │       │   ├── videorecorder/
│   │       │   │   └── RecordingManager.java      # Video recording
│   │       │   └── ArtifactRepository.java        # Artifact tracking
│   │       ├── pages/
│   │       │   ├── BasePage.java                  # Base page class
│   │       │   ├── HomePage.java                  # Home page object
│   │       │   ├── SignUpAndLoginPage.java        # Auth page object
│   │       │   ├── CheckoutPage.java              # Checkout page object
│   │       │   ├── ProdcutsPage.java              # Products page object
│   │       │   ├── CartPage.java                  # Shopping cart page
│   │       │   ├── TestCasesPage.java             # Test cases page
│   │       │   ├── ContactUSPage.java             # Contact page
│   │       │   ├── components/
│   │       │   │   ├── NavigationBar.java         # Shared nav component
│   │       │   │   └── ProductGallery.java        # Product display component
│   │       │   ├── contracts/
│   │       │   │   └── ISignupFlow.java           # Signup behavior contract
│   │       │   └── dto/
│   │       │       ├── LoginCredentials.java      # Login data model
│   │       │       └── RegistrationData.java      # Registration data model
│   │       ├── utils/
│   │       │   ├── actions/
│   │       │   │   ├── BrowserActions.java        # Browser-level actions
│   │       │   │   └── ElementsActions.java       # Element-level actions
│   │       │   ├── datareaders/
│   │       │   │   └── JsonReader.java            # JSON test data loader
│   │       │   ├── reporting/
│   │       │   │   └── ReportingManager.java      # Report generation
│   │       │   ├── TimeStampCreator.java          # Timestamp utilities
│   │       │   └── OSUtils.java                   # OS detection
│   │       └── media/
│   │           └── ScreenshotManager.java
│   │
│   └── test/
│       ├── java/org/blazedemo/
│       │   └── tests/
│       │       ├── basetest/
│       │       │   └── BaseTest.java              # Test base class
│       │       └── ui/
│       │           ├── RegistrationTestcases.java # Registration tests
│       │           ├── LoginTestcases.java        # Login tests
│       │           ├── ProductsTestcases.java     # Product tests
│       │           ├── checkoutTestcases.java     # Checkout tests
│       │           └── E2ETests.java              # End-to-end scenarios
│       └── resources/
│           ├── testdata/
│           │   ├── login_data.json               # Login test data
│           │   └── registration_data.json        # Registration test data
│           └── config/
│               └── environment.properties         # Environment config
├── config/
│   └── environment.properties                    # Configuration file
├── test-output/                                  # Test execution output
├── allure-results/                               # Allure report data
├── pom.xml                                       # Maven configuration
└── README.md                                     # This file
```

---

## Technology Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 21 | Programming language |
| **Selenium WebDriver** | 4.41.0 | Browser automation |
| **TestNG** | 7.11.0 | Test framework & execution |
| **Allure** | 2.29.1 | Reporting & visualization |
| **Log4j2** | 2.25.4 | Logging framework |
| **Lombok** | 1.18.46 | Code generation (annotations) |
| **Jackson** | 2.17.0 | JSON processing |
| **AspectJ** | 1.9.25.1 | AOP weaving for Allure |
| **JAVE** | 3.5.0 | Video processing |
| **Commons IO** | 2.22.0 | File utilities |

---

## Prerequisites

Before setting up the framework, ensure you have:

- **Java Development Kit (JDK)** 21 or higher installed
- **Maven** 3.8.0 or higher
- **Git** for version control
- One or more web browsers:
  - Google Chrome (latest)
  - Firefox (latest)
  - Microsoft Edge (latest)
- **WebDriver binaries** (automatically managed by Selenium 4)

### Verify Installation

```bash
java -version
mvn -version
```

---

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/Alaaabdelmoneam/Test_Automation_Framework.git
cd Test_Automation_Framework
```

### 2. Install Dependencies

```bash
mvn clean install
```

Maven will automatically download all required dependencies defined in `pom.xml`.

### 3. Verify Installation

```bash
mvn verify
```

### 4. Optional: IDE Setup (IntelliJ IDEA or Eclipse)

**IntelliJ IDEA:**
1. Open the project folder
2. Go to `File → Open` and select the project
3. IDE will automatically detect Maven structure
4. Go to `File → Project Structure → Project → SDK` and select Java 21

**Eclipse:**
1. Right-click → `Import → Maven → Existing Maven Projects`
2. Select the project folder
3. Eclipse will configure the project automatically

---

## Configuration

### Environment Configuration

Edit `config/environment.properties` to configure your test environment:

```properties
# Base URL
BaseURL=https://automationexercise.com

# Browser Configuration
browser=Chrome              # Options: Chrome, Firefox, Edge
headless=false             # Run in headless mode: true/false
implicitWait=10            # Implicit wait in seconds
pageLoadTimeout=30         # Page load timeout in seconds

# Allure Reporting
allure.results.directory=allure-results
allure.report.directory=allure-report
```

### Driver Configuration

Browser-specific options are managed in `WebDriverFactory.java`:

- **Chrome**: Customizable options including headless mode, user data directory
- **Firefox**: Profile-based configuration with custom preferences
- **Edge**: Similar to Chrome with Chromium-based options

### Log Configuration

Logging is configured via Log4j2 properties. Logs include:
- Console output for immediate feedback
- File-based logs in the `logs/` directory for detailed analysis
- Test execution ID for correlation

---

## Running Tests

### Run All Tests

```bash
mvn clean test
```

### Run Specific Test Class

```bash
mvn clean test -Dtest=RegistrationTestcases
```

### Run Tests by Tag/Feature

```bash
mvn clean test -Dgroups=smoke
```

### Run Tests with Specific Browser

```bash
mvn clean test -Dbrowser=Firefox
```

### Run Tests in Headless Mode

```bash
mvn clean test -Dheadless=true
```

### Run Tests in Parallel

Configure in `pom.xml` surefire plugin:
```xml
<parallel>methods</parallel>
<threadCount>4</threadCount>
```

---

## Test Organization

### Test Classes

| Class | Scope | Tests |
|-------|-------|-------|
| **RegistrationTestcases** | User registration flow | Valid/Invalid registration |
| **LoginTestcases** | User authentication | Valid/Invalid credentials |
| **ProductsTestcases** | Product management | Browse, search, filter |
| **checkoutTestcases** | E-commerce checkout | Cart, payment, order |
| **E2ETests** | End-to-end scenarios | Complete user journeys |

### Test Annotations

All tests use Allure annotations for categorization:

```java
@Epic("Automation Exercise")      // High-level feature group
@Feature("User Management")        // Feature category
@Story("User Login")               // Specific scenario
@Owner("Alaa")                     // Test owner
@Severity(SeverityLevel.BLOCKER)  // Test criticality
@Description("Verify user can login") // Detailed description
@Test
public void testValidLogin() { ... }
```

### Page Object Pattern

Example page class structure:

```java
@Log4j2
public class HomePage extends BasePage {
    
    // Locators
    private static final By PRODUCT_NAME = By.xpath("//p[text()='%s']");
    private static final By ADD_TO_CART = By.xpath("//button[contains(@class, 'add-to-cart')]");
    
    // Page Actions
    @Step("Navigate to home page")
    public HomePage navigate() { ... }
    
    @Step("Add {productName} to cart")
    public HomePage addProductToCart(String productName) { ... }
    
    // Validations
    @Step("Verify on home page")
    public HomePage verifyOnHomePage() { ... }
}
```

---

## Reporting

### Allure Reports

#### Generate Report

```bash
mvn allure:report
```

#### Open Report

```bash
mvn allure:serve
```

This opens an interactive HTML report in your default browser showing:
- Test execution timeline
- Pass/fail statistics
- Detailed test steps
- Screenshots and videos on failures
- Environment information
- Severity and category breakdowns

### Report Contents

- **Test Results**: Pass/fail status with duration
- **Steps**: Detailed step-by-step execution trace
- **Attachments**: Screenshots, videos, logs
- **History**: Trend analysis across multiple runs
- **Categories**: Tests grouped by Epic, Feature, Story

### Video Recording

Videos are automatically captured during test execution in:
```
target/video-recordings/
```

Successful video files are attached to Allure reports on test failures.

### Artifact Management

Screenshots and logs are managed through `ArtifactRepository.java`:
- Named with test name and page title
- Timestamped for uniqueness
- Organized in dedicated directories
- Automatically attached to Allure reports

---

## Best Practices Implemented

### 1. **Page Object Model (POM)**
   - Encapsulates page elements and interactions
   - Reduces code duplication
   - Improves maintainability

### 2. **Separation of Concerns**
   - Page classes handle UI interactions
   - Action utility classes handle common operations
   - Data readers manage test data
   - Listeners handle lifecycle events

### 3. **DRY Principle (Don't Repeat Yourself)**
   - Shared components (NavigationBar, ProductGallery)
   - Common actions in utility classes
   - Base test class for setup/teardown

### 4. **Fluent API**
   - Chainable methods for readable tests
   - Example: `page.navigate().addProduct().verifyCart()`

### 5. **Comprehensive Logging**
   - Log4j2 for structured logging
   - Each test has unique execution ID
   - Detailed step-level logging

### 6. **Exception Handling**
   - Custom exceptions for test-specific errors
   - Graceful failure handling with informative messages
   - Automatic cleanup on failures

### 7. **Test Data Management**
   - JSON-based external test data
   - DTOs for type-safe data handling
   - Support for complex registration flows

### 8. **Listener-Based Lifecycle**
   - TestNG listeners for test lifecycle events
   - Automatic WebDriver initialization/cleanup
   - Automatic screenshot/video on failures

### 9. **Cross-Browser Compatibility**
   - Factory pattern for driver instantiation
   - Consistent behavior across browsers
   - Easy to add new browsers

### 10. **Allure Integration**
   - Rich test categorization
   - Detailed step tracking
   - Visual reporting with artifacts

---

## Planned Improvements

### 🚀 Architecture Enhancements

1. **Driver Factory Refactoring**
   - Replace `WebDriverFactory` enum with separate `IOptionsFactory` implementations
   - Each browser type gets dedicated option factory class
   - Removes explicit casting and improves extensibility
   - **Status**: Noted in Readme.md (To-Do)

### 📈 Framework Expansion

2. **API Testing Support**
   - Integration with REST Assured for API testing
   - Hybrid UI + API automation capabilities
   - Request/response logging in reports

3. **Mobile Automation**
   - Appium integration for mobile app testing
   - Support for iOS and Android platforms

4. **Advanced Data Management**
   - Database integration for test data setup/cleanup
   - Environment-specific data configurations

### 🔧 Quality Improvements

5. **Extended Reporting**
   - Custom Allure plugins for additional metrics
   - Test execution trend analysis
   - Performance benchmarking

6. **Performance Optimization**
   - Parallel test execution enhancements
   - Test result caching mechanisms
   - Smart retry logic for flaky tests

7. **Documentation**
   - Detailed step-by-step tutorials
   - Video walkthroughs
   - Test case catalog

### 🛡️ Stability & Reliability

8. **Resilience Features**
   - Automatic retry logic for transient failures
   - Element wait strategies (explicit waits)
   - Exception recovery mechanisms

---

## Example Test Case

```java
@Log4j2
@Epic("Automation Exercise")
@Feature("User Management")
@Story("User Registration")
@Owner("Alaa")
public class RegistrationTestcases extends BaseTest {

    @Description("Verify user can register with new account")
    @Test
    public void testValidRegistration() {
        // Arrange
        RegistrationData registrationData = new RegistrationData(
            "Mr.",
            "Alaa",
            "Alaa" + TimeStampCreator.getCurrentTime() + "@gmail.com",
            "password123",
            // ... other fields
        );

        // Act & Assert
        new SignUpAndLoginPage()
            .navigate()
            .signup(registrationData)
            .validateSignupPageRedirection()
            .clickOnContinueButton()
            .verifyOnHomePage();
    }
}
```

---

## Troubleshooting

### Common Issues

| Issue | Solution |
|-------|----------|
| WebDriver not found | Ensure Java 21 is installed; run `mvn clean install` |
| Browser not launching | Check browser installation; verify browser path in config |
| Allure reports empty | Ensure `aspectjweaver` is properly loaded; check pom.xml |
| Screenshot/Video missing | Verify disk space; check artifact directory permissions |
| Tests timeout | Increase wait times in `environment.properties` |

### Debug Mode

Enable detailed logging by modifying Log4j2 configuration:

```xml
<Logger name="org.blazedemo" level="DEBUG" />
```

---

## Contributing

1. **Create a Feature Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Follow Code Standards**
   - Use meaningful variable names
   - Add Allure annotations to tests
   - Include Javadoc for public methods
   - Follow existing code style

3. **Test Your Changes**
   ```bash
   mvn clean test
   ```

4. **Commit with Clear Messages**
   ```bash
   git commit -m "Add new test for user profile feature"
   ```

5. **Push and Create Pull Request**
   ```bash
   git push origin feature/your-feature-name
   ```

---

## License

This project is part of the Automation Exercise and follows its licensing terms.

---

## Contact & Support

- **Author**: Alaa Abdelmoneam
- **Repository**: [GitHub - Test_Automation_Framework](https://github.com/Alaaabdelmoneam/Test_Automation_Framework)
- **Issues**: Report via GitHub Issues

---

## Additional Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Documentation](https://testng.org/doc/)
- [Allure Documentation](https://docs.qameta.io/allure/)
- [Log4j2 Guide](https://logging.apache.org/log4j/2.x/)
- [Automation Exercise Website](https://www.automationexercise.com/)

---

**Last Updated**: June 20, 2026
**Framework Version**: 1.0-SNAPSHOT

