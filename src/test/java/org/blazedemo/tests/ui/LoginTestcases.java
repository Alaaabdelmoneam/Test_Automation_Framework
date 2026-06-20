package org.blazedemo.tests.ui;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.pages.SignUpAndLoginPage;
import org.blazedemo.pages.dto.LoginCredentials;
import org.blazedemo.tests.basetest.BaseTest;
import org.blazedemo.utils.TimeStampCreator;
import org.testng.annotations.Test;

import static org.blazedemo.utils.datareaders.JsonReader.getTestDataFromClasspath;

@Log4j2
@Epic("Automation Exercise")
@Feature("User Management")
@Story("User Login")
@Owner("Alaa")
@Severity(SeverityLevel.BLOCKER)
public class LoginTestcases extends BaseTest {

    @Description("Verify User can Login with valid credentials")
    @Test
    public void TestValidLogin(){
        LoginCredentials credentials = getTestDataFromClasspath(
                "testdata/registered_user.json", LoginCredentials.class);
        new SignUpAndLoginPage().navigate().login(credentials)
                .validateLoginSuccessfulRedirection();
    }

    @Description("Verify User can't Login with invalid email")
    @Test
    public void invalidLoginEmail(){
        LoginCredentials credentials = getTestDataFromClasspath(
                "testdata/registered_user.json", LoginCredentials.class);

        credentials.setEmail("random" + TimeStampCreator.getCurrentTime() +"@random.com");

        new SignUpAndLoginPage()
                .navigate().login(credentials)
                .validateNoRedirectionHappened()
                .verifyInvalidLoginErrorAppears();
    }

    @Description("Verify User can't Login with invalid password")
    @Test
    public void invalidLoginPassword(){
        LoginCredentials credentials = getTestDataFromClasspath(
                "testdata/registered_user.json", LoginCredentials.class);

        credentials.setPassword("random" + TimeStampCreator.getCurrentTime() +"*__*");

        new SignUpAndLoginPage()
                .navigate().login(credentials)
                .validateNoRedirectionHappened()
                .verifyInvalidLoginErrorAppears();
    }
}
