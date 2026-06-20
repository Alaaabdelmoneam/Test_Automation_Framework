package org.blazedemo.pages;

import io.qameta.allure.Step;
import org.blazedemo.pages.components.CategoryAndBrandSidebar;
import org.blazedemo.pages.components.ProductAddedToCartWindow;
import org.blazedemo.pages.components.ProductGallery;
import org.openqa.selenium.By;
import org.testng.Assert;

import static org.blazedemo.utils.actions.BrowserActions.navigateTo;
import static org.blazedemo.utils.actions.ElementsActions.*;

public class ProductDetails {

    private static final CategoryAndBrandSidebar categoryBrandSidebar = new CategoryAndBrandSidebar();

    public ProductDetails customNavigation(String productDetailsURL){
        navigateTo(productDetailsURL);
        return this;
    }

    /** Locators **/
    // Product Info
    private static final By productName     = By.cssSelector(".product-information > h2");
    private static final By productCategory = By.cssSelector(".product-information > p");
    private static final By productPrice    = By.cssSelector(".product-information > span > span");
    private static final By productQuantity = By.id("quantity");
    private static final By addToCart       = By.cssSelector(".product-information button");

    // Review Section
    private static final By reviewerName    = By.id("name");
    private static final By reviewerEmail   = By.id("email");
    private static final By review          = By.id("review");
    private static final By reviewSubmit    = By.id("button-review");
    private static final By reviewSubmitMsg = By.cssSelector("#review-section span");
    private static final String successfulreviewSubmitMsg = "Thank you for your review.";


    /** Actions **/
    @Step("Add a Review")
    public ProductDetails addReview(String name, String email, String reviewMessage){
        sendText(reviewerName, name);
        sendText(reviewerEmail, email);
        sendText(review, reviewMessage);
        click(reviewSubmit);
        return this;
    }

    public static String getProductPrice(){
        return getText(productPrice);
    }

    public ProductDetails addProductToCart(int quantity){
        sendText(productQuantity, String.valueOf(quantity));
        click(addToCart);
        return this;
    }

    public CartPage viewCart(){
        return ProductAddedToCartWindow.viewCart();
    }

    public ProductDetails continueShopping(){
        ProductAddedToCartWindow.continueShopping();
        return this;
    }

    public ProductGallery filterByCategory(String category, String subCategory){
        categoryBrandSidebar
                .filterByCategory(category, subCategory)
                .validateFilterByCategorySuccessful(category, subCategory);
        return new ProductGallery();
    }

    public ProductGallery filterByBrand(String brandName){
        categoryBrandSidebar
                .filterByBrand(brandName)
                .validateFilterByBrandSuccessful(brandName);
        return new ProductGallery();
    }

    /** Validations **/
    public ProductDetails validateReviewSubmitted(){
        Assert.assertTrue(ElementDisplayed(reviewSubmitMsg) &&
                getText(reviewSubmitMsg).equalsIgnoreCase(successfulreviewSubmitMsg));
        return this;
    }


    public ProductDetails validateProductAddedToCart(){
        ProductAddedToCartWindow.validateProductAddedToCart();
        return this;
    }

    public ProductDetails validatePriceEquals(int price){
        Assert.assertEquals(getProductPrice().substring(4), Integer.toString(price));
        return this;
    }
}
