package org.blazedemo.tests.ui;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.pages.SignUpAndLoginPage;
import org.blazedemo.pages.dto.RegistrationData;
import org.blazedemo.tests.basetest.BaseTest;
import org.blazedemo.utils.TimeStampCreator;
import org.testng.annotations.Test;

import static org.blazedemo.utils.datareaders.JsonReader.getTestDataFromClasspath;

@Log4j2
@Epic("Automation Exercise")
@Feature("User Management")
@Story("User Registeration")
@Owner("Alaa")
public class RegistrationTestcases extends BaseTest {

    @Description("Verify User can Register with new random account")
    @Test
    public void TestValidRegister(){
        RegistrationData registrationData = new RegistrationData(
                "Mr.",
                "Alaa",
                "Alaa" + TimeStampCreator.getCurrentTime() + "@gmail.com",
                "1234",
                "19",
                "February",
                "2002",
                "Alaa",
                "Abdelmoneam",
                "Tesla",
                "None of your business",
                "None of your business",
                "India",
                "Mombay",
                "Mombar",
                "35464",
                "12345",
        true,
        true
        );

        new SignUpAndLoginPage().navigate().signup(registrationData)
                .validateSignupPageRedirection()
                .signup(registrationData)
                .clickOnContinueButton()
                .verifyOnHomePage();
    }

    @Description("Verify User can't Register with old registered account")
    @Test
    public void TestInvalidRegister(){
        RegistrationData registrationData =
                getTestDataFromClasspath("testdata/registered_user.json", RegistrationData.class);

        new SignUpAndLoginPage().navigate().signup(registrationData)
                .validateNoRedirectionHappened().verifyAlreadyRegisteredErrorAppears();
    }
}

