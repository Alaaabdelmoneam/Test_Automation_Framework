package org.blazedemo.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.pages.dto.RegistrationData;
import org.openqa.selenium.By;

import static org.blazedemo.utils.actions.BrowserActions.getCurrentUrl;
import static org.blazedemo.utils.actions.BrowserActions.navigateTo;
import static org.blazedemo.utils.actions.ElementsActions.*;
import static org.blazedemo.utils.assertions.HardAssertions.assertElementDisplayed;

@Log4j2
public class SignUpPage extends BasePage {

    public enum Title{
        MR,
        MRS;

        public static Title getTitle(String title){
            return Title.valueOf(title.toUpperCase());
        }
    }

    // title
    private static final By titleMrRadioButton  = By.id("id_gender1");
    private static final By titleMrsRadioButton = By.id("id_gender2");

    private static final By name                = By.id("name");
    private static final By email               = By.id("email");
    private static final By password            = By.id("password");

    // Date Of Birth
    private static final By day                 = By.id("days");
    private static final By month               = By.id("months");
    private static final By year                = By.id("years");

    // Subscriptions
    private static final By newsletterSignup    = By.id("newsletter");
    private static final By offersSignup        = By.id("optin");

    // Address Info
    private static final By firstName           = By.id("first_name");
    private static final By lastName            = By.id("last_name");
    private static final By company             = By.id("company");
    private static final By firstAddress        = By.id("address1");
    private static final By secondAddress       = By.id("address2");
    private static final By country             = By.id("country");
    private static final By state               = By.id("state");
    private static final By city                = By.id("city");
    private static final By zipcode             = By.id("zipcode");
    private static final By mobileNumber        = By.id("mobile_number");
    private static final By createAccountButton = By.cssSelector("button[data-qa='create-account']");
    private static final By accountCreatedLabel = By.cssSelector("h2>b");
    private static final By continueButton      = By.cssSelector("a[data-qa='continue-button']");


    public SignUpPage navigate() {
        navigateTo("https://automationexercise.com/signup");
        return this;
    }

    @Step("Performing signup on SignUpPage with data: {0}")
    public SignUpPage signup(RegistrationData data) {
        // Main signup implementation - accepts parameterized RegistrationData

        // Select title
        if ("Mrs".equalsIgnoreCase(data.getTitle())) {
            click(titleMrsRadioButton);
        } else {
            click(titleMrRadioButton);
        }

        // Account information
        // name & email already sent & saved from first SignUpAndLoginPage.signup
//        sendText(name, data.getName());
//        sendText(email, data.getEmail());
        sendText(password, data.getPassword());

        // Date of birth
        SelectElementFromDropdown(day, data.getBirthDay());
        SelectElementFromDropdown(month, data.getBirthMonth());
        SelectElementFromDropdown(year, data.getBirthYear());

        // Subscriptions
        if (data.isNewsletterSubscription()) {
            click(newsletterSignup);
        }
        if (data.isOffersSubscription()) {
            click(offersSignup);
        }

        // Address information
        sendText(firstName, data.getFirstName());
        sendText(lastName, data.getLastName());
        sendText(company, data.getCompany());
        sendText(firstAddress, data.getFirstAddress());
        sendText(secondAddress, data.getSecondAddress());
        SelectElementFromDropdown(country, data.getCountry());
        sendText(state, data.getState());
        sendText(city, data.getCity());
        sendText(zipcode, data.getZipcode());
        sendText(mobileNumber, data.getMobileNumber());

        // submit form
        click(createAccountButton);
        return this;
    }


    public static boolean isPageLoaded(){

        return getCurrentUrl().contains("/signup")
                && ElementDisplayed(name)
                && ElementDisplayed(titleMrRadioButton)
                && ElementDisplayed(month)
                && ElementDisplayed(newsletterSignup)
                && ElementDisplayed(firstName)
                && ElementDisplayed(firstAddress)
                && ElementDisplayed(country)
                && ElementDisplayed(zipcode)
                && ElementDisplayed(mobileNumber);
    }

    @Step("Assert that account created")
    public SignUpPage isAccountCreated(){
        assertElementDisplayed(accountCreatedLabel);
        return this;
    }

    @Step("Continue After Successful registration")
    public HomePage clickOnContinueButton(){
        click(continueButton);
        return new HomePage();
    }
}
