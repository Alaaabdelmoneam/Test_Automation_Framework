package org.blazedemo.pages.components;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.drivers.DriverManager;
import org.blazedemo.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.devtools.v143.dom.model.RGBA;


import static org.blazedemo.config.EnvironmentConfiguration.getBaseURL;
import static org.blazedemo.utils.actions.BrowserActions.navigateTo;
import static org.blazedemo.utils.actions.ElementsActions.click;
import static org.blazedemo.utils.actions.ElementsActions.getText;
import static org.testng.Assert.assertEquals;

@Log4j2
public class NavigationBar {

    // locators
    public static final By homePageButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/\"]");
    public static final By productButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/products\"]");
    public static final By cartButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/view_cart\"]");
    public static final By signUpAndLoginButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/login\"]");
    public static final By testCasesButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/test_cases\"]");
    public static final By APITestingButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/https://www.youtube.com/c/AutomationExercise\"]");
    public static final By contactUsButton =
            By.cssSelector(".nav.navbar-nav a[href=\"/contact_us\"]");
    public static final By loggedInUserName =
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

    /** Validations **/
    @Step("Verify user is logged in")
    public NavigationBar verifyUserLoggedIn(String expectedName){
        String actualName = getText(loggedInUserName);
        assertEquals(actualName ,expectedName);
        return this;
    }

    // verify Button color change to indicate which page you are on
    public static boolean checkButtonEnabled(By by){
        return DriverManager.getDriver().findElement(by).
                getCssValue("color").equalsIgnoreCase("rgba(255, 165, 0, 1)");
    }
}
