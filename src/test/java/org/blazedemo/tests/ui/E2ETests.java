package org.blazedemo.tests.ui;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.pages.SignUpAndLoginPage;
import org.blazedemo.pages.dto.LoginCredentials;
import org.blazedemo.utils.datareaders.JsonReader;
import org.testng.annotations.Test;

import static org.blazedemo.utils.datareaders.JsonReader.getTestDataFromClasspath;
@Log4j2
@Epic("Automation Exercise")
@Feature("End-to-End Test")
@Story("System Test")
@Owner("Alaa")
public class E2ETests {

    @Description("Testing Full E2E Scenario")
    @Test
    public void validE2EScenario(){
        LoginCredentials credentials = getTestDataFromClasspath(
                "testdata/registered_user.json", LoginCredentials.class);

        new SignUpAndLoginPage().navigate()
                .login(credentials)
                .validateLoginSuccessfulRedirection()
                .verifyOnHomePage()
                .addProductToCart("Blue Top")
                .validateProductAddedToCart()
                .continueShopping()
                .addProductToCart("Blue Top")
                .continueShopping()
                .addProductToCart("Men Tshirt")
                .validateProductAddedToCart()
                .viewCart()
                .removeProductFromCart("Blue Top")
                .checkout()
                .writeCommentOnOrder("7elw")
                .placeOrder()
                .pay(
                        JsonReader.getPropertyValue("testdata/payment.json", "nameOnCard"),
                        JsonReader.getPropertyValue("testdata/payment.json", "cardNumber"),
                        JsonReader.getPropertyValue("testdata/payment.json", "CVC"),
                        JsonReader.getPropertyValue("testdata/payment.json", "expiryMonth"),
                        JsonReader.getPropertyValue("testdata/payment.json", "expiryYear")
                )
                .validateOrderSuccessful()
                .continueToHomePage()
        ;
    }
}
