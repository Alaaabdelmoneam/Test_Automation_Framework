package org.blazedemo.tests.ui;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.pages.SignUpAndLoginPage;
import org.blazedemo.pages.dto.LoginCredentials;
import org.blazedemo.tests.basetest.BaseTest;
import org.blazedemo.utils.datareaders.JsonReader;
import org.testng.annotations.Test;

@Log4j2
@Epic("Automation Exercise")
@Feature("Cart Management")
@Story("Cart Management")
@Owner("Alaa")
@Severity(SeverityLevel.CRITICAL)
public class CartTestcases extends BaseTest {

    @Description("Verify Item Added To Cart")
    @Test
    public void addToCartValid(){
        LoginCredentials credentials = JsonReader.getTestDataFromClasspath(
                "testdata/valid_login_credentials.json",
                LoginCredentials.class);

        new SignUpAndLoginPage().navigate()
                .login(credentials)
                .validateLoginSuccessfulRedirection()
                .verifyOnHomePage()
                .addProductToCart("Men Tshirt")
                .validateProductAddedToCart()
                .continueShopping()
                .addProductToCart("Blue Top")
                .continueShopping()
                .addProductToCart("Little Girls Mr. Panda Shirt")
                .viewCart()
                .checkout()
                .writeCommentOnOrder("Gamedd")
                .placeOrder();
    }
}
