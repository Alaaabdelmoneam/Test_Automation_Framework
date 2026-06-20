package org.blazedemo.tests.ui;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.pages.ProductDetails;
import org.blazedemo.utils.datareaders.JsonReader;
import org.testng.annotations.Test;

import java.util.Objects;

@Log4j2
@Epic("Automation Exercise")
@Feature("User Management")
@Story("Product Management")
@Owner("Alaa")
public class productDetailsTestcases {

    String validURL = JsonReader.getPropertyValue(
            "testdata/product_details.json",
            "valid_url");

    int validPrice = Integer.parseInt(Objects.requireNonNull(JsonReader.getPropertyValue(
            "testdata/product_details.json",
            "valid_price")));
    @Test
    @Description("Adding a Valid Product to cart")
    public void addValidProductToCart(){


        new ProductDetails().customNavigation(validURL)
                .addProductToCart(3)
                .validateProductAddedToCart()
                .validatePriceEquals(validPrice);
    }

    @Test
    @Description("Adding a review")
    public void leaveAValidReview(){
        new ProductDetails().customNavigation(validURL).addReview(
                "Alaa",
                "alaa@yahoo.com",
                "Very Good Product"
        ).validateReviewSubmitted();
    }
}
