package org.blazedemo.tests.ui;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.pages.CheckoutPage;
import org.blazedemo.pages.SignUpAndLoginPage;
import org.blazedemo.pages.dto.LoginCredentials;
import org.blazedemo.pages.dto.RegistrationData;
import org.blazedemo.utils.TimeStampCreator;
import org.blazedemo.utils.datareaders.JsonReader;
import org.testng.annotations.Test;

import java.util.Objects;

import static org.blazedemo.utils.datareaders.JsonReader.getTestDataFromClasspath;

@Log4j2
@Epic("Automation Exercise")
@Feature("Cart Management")
@Story("Checkout")
@Owner("Alaa")
@Severity(SeverityLevel.BLOCKER)
public class checkoutTestcases {

    @Description("Verify checkout successfully")
    @Test
    public void addToCartValid(){
        RegistrationData registrationData =
                getTestDataFromClasspath("testdata/registered_user.json", RegistrationData.class);

        registrationData.setEmail("asd" + TimeStampCreator.getCurrentTime() + "@gmail.com");
        new SignUpAndLoginPage().navigate()
                .signup(registrationData)
                .validateSignupPageRedirection()
                .signup(registrationData)
                .clickOnContinueButton()
                .verifyOnHomePage()
                .addProductToCart("Men Tshirt")
                .validateProductAddedToCart()
                .continueShopping()
                .navigationBar.clickOnProductsButton()
                .addProductToCart("Blue Top")
                .continueShopping()
                .addProductToCart("Little Girls Mr. Panda Shirt")
                .viewCart()
                .checkout()
                .verifyAddressData(
                        CheckoutPage.AddressCard.ADDRESS_DELIVERY,
                        JsonReader.getPropertyValue("testdata/registered_user.json", "title"),
                        JsonReader.getPropertyValue("testdata/registered_user.json", "firstName"),
                        JsonReader.getPropertyValue("testdata/registered_user.json", "lastName"),
                        JsonReader.getPropertyValue("testdata/registered_user.json", "company"),
                        JsonReader.getPropertyValue("testdata/registered_user.json", "firstAddress"),
                        JsonReader.getPropertyValue("testdata/registered_user.json", "secondAddress"),
                        JsonReader.getPropertyValue("testdata/registered_user.json", "city"),
                        JsonReader.getPropertyValue("testdata/registered_user.json", "state"),
                        Integer.parseInt(Objects.requireNonNull(JsonReader.getPropertyValue("testdata/registered_user.json", "zipcode"))),
                        JsonReader.getPropertyValue("testdata/registered_user.json", "country"),
                        JsonReader.getPropertyValue("testdata/registered_user.json", "mobileNumber")
                )
                .writeCommentOnOrder("Gamed ya 7amed")
                .placeOrder()
        ;
    }

}
