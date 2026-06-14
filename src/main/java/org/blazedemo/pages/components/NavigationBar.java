package org.blazedemo.pages.components;

import io.qameta.allure.Step;
import org.blazedemo.drivers.DriverManager;
import org.blazedemo.pages.*;
import org.openqa.selenium.By;

import static org.blazedemo.config.EnvironmentConfiguration.getBaseURL;
import static org.blazedemo.utils.actions.BrowserActions.navigateTo;
import static org.blazedemo.utils.actions.ElementsActions.click;

public class NavigationBar {

    // locators
    private static final By homePageButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/\"]");
    private static final By productButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/products\"]");
    private static final By cartButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/view_cart\"]");
    private static final By signUpAndLoginButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/login\"]");
    private static final By testCasesButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/test_cases\"]");
    private static final By APITestingButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/https://www.youtube.com/c/AutomationExercise\"]");
    private static final By contactUsButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/contact_us\"]");

    // actions
    @Step("Navigate to Home page")
    public NavigationBar navigate(){
        navigateTo(getBaseURL());
        return this;
    }

    @Step("click on Home button")
    public HomePage clickOnHomeButton(){
        click(homePageButton);
        return new HomePage();
    }

    @Step("click on Products button")
    public ProdcutsPage clickOnProductsButton(){
        click(productButton);
        return new ProdcutsPage();
    }

    @Step("click on Cart button")
    public CartPage clickOnCartButton(){
        click(cartButton);
        return new CartPage();
    }

    @Step("click on Signup / Login button")
    public SignUpAndLoginPage clickOnSignUpAndLoginButtonButton(){
        click(signUpAndLoginButton);
        return new SignUpAndLoginPage();
    }
    @Step("click on TestCases button")
    public TestCasesPage clickOnTestCasesButton(){
        click(testCasesButton);
        return new TestCasesPage();
    }

    @Step("click on API Testing button")
    public APITestingPage clickOnApiTestButton(){
        click(APITestingButton);
        return new APITestingPage();
    }
    @Step("click on Contact us button")
    public ContactUSPage clickOnContactUsButton(){
        click(contactUsButton);
        return new ContactUSPage();
    }
}
