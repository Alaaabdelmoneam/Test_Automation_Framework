package org.blazedemo.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.blazedemo.pages.components.NavigationBar;
import org.blazedemo.pages.dto.LoginCredentials;
import org.blazedemo.pages.dto.RegistrationData;
import org.openqa.selenium.By;
import org.testng.Assert;

import static org.blazedemo.utils.actions.BrowserActions.navigateTo;
import static org.blazedemo.utils.actions.ElementsActions.*;

@Slf4j
public class SignUpAndLoginPage extends BasePage {

    /** Locators **/

    // Signup locators
    private static final By signupNameLocator  = By.cssSelector("input[data-qa='signup-name']");
    private static final By signupEmailLocator = By.cssSelector("input[data-qa='signup-email']");
    private static final By signupButtonSubmit = By.cssSelector("button[data-qa='signup-button']");
    private static final By signupErrorMessageLocator = By.cssSelector(".signup-form  p");
    private static final String signupErrorMessage = "Email Address already exist!";

    // Login locators
    private static final By loginEmailLocator    = By.cssSelector("input[data-qa='login-email']");
    private static final By loginPasswordLocator = By.cssSelector("input[data-qa='login-password']");
    private static final By loginButtonSubmit    = By.cssSelector("button[data-qa='login-button']");
    private static final By loginErrorMessageLocator = By.cssSelector(".login-form  p");
    private static final String loginErrorMessage = "Your email or password is incorrect!";


    /** Actions **/

    // navigation
    @Step("Navigating to Signup and Login Page")
    public SignUpAndLoginPage navigate(){
        navigateTo("https://automationexercise.com/login");
        return this;
    }

    @Step("Signing Up on SignUpAndLoginPage with data: {0}")
    public SignUpAndLoginPage signup(RegistrationData data) {
        // Signup initial form - just needs name and email to redirect to detail form
        sendText(signupNameLocator, data.getName());
        sendText(signupEmailLocator, data.getEmail());
        click(signupButtonSubmit);
        return this;
    }

    // Login Actions
    @Step("Logging in")
    public SignUpAndLoginPage login(LoginCredentials credentials){
        sendText(loginEmailLocator, credentials.getEmail());
        sendText(loginPasswordLocator, credentials.getPassword());
        click(loginButtonSubmit);
        return this;
    }

    /** Validations **/

    // Verify Redirection to Signup Page
    @Step("Validating if Successful initial signup & Redirection to Signup page")
    public SignUpPage validateSignupPageRedirection(){
        Assert.assertTrue(SignUpPage.isPageLoaded(),
                "Not redirected to Signup page");
        log.info("Redirection Successful to signup page");
        return new SignUpPage();
    }

    // Verify that you are still on the SignUpAndLoginPage
    @Step("Validating if Successful initial signup & Redirection to Signup page")
    public SignUpAndLoginPage validateNoRedirectionHappened(){
        Assert.assertTrue(isPageLoaded(),
                "not on Signup and login page, redirection happened!");
        log.info("still on Signup and Login Page");
        return this;
    }

    // Verify Redirection to Home Page
    @Step("Validating if Successful login & Redirection to Home Page")
    public HomePage validateLoginSuccessfulRedirection(){
        Assert.assertTrue(
                HomePage.isPageLoaded(),
                "login failed!"
        );
        log.info("Redirection Successful to Home page");
        return new HomePage();

    }

    @Step("verify error appears when trying to register with an already registered account")
    public SignUpAndLoginPage verifyAlreadyRegisteredErrorAppears(){
        Assert.assertEquals(getText(signupErrorMessageLocator),
                signupErrorMessage,
                "Account not registered before");
        return this;
    }

    @Step("verify error appears when trying to register with an already registered account")
    public SignUpAndLoginPage verifyInvalidLoginErrorAppears(){
        Assert.assertEquals(getText(loginErrorMessageLocator),
                loginErrorMessage,
                "Account not registered before");
        return this;
    }

    public static boolean  isPageLoaded(){
        log.info(String.valueOf(NavigationBar.checkButtonEnabled(NavigationBar.signUpAndLoginButton)));
        log.info(String.valueOf(ElementDisplayed(loginButtonSubmit)));
        log.info(String.valueOf(ElementDisplayed(signupButtonSubmit)));
        return NavigationBar.checkButtonEnabled(NavigationBar.signUpAndLoginButton)
                && ElementDisplayed(loginButtonSubmit)
                && ElementDisplayed(signupButtonSubmit);
    }

    public static boolean isSignupErrorDisplayed(){
        return ElementDisplayed(signupErrorMessageLocator);
    }

    public static boolean isLoginErrorDisplayed(){
        return ElementDisplayed(loginErrorMessageLocator);
    }
}
