package org.blazedemo.pages;

import lombok.extern.log4j.Log4j2;
import org.blazedemo.pages.components.NavigationBar;
import org.blazedemo.pages.components.ProductGallery;
import org.openqa.selenium.By;
import org.testng.Assert;

import static org.blazedemo.utils.actions.BrowserActions.getCurrentUrl;
import static org.blazedemo.utils.actions.BrowserActions.navigateTo;
import static org.blazedemo.utils.actions.ElementsActions.ElementDisplayed;

@Log4j2
public class HomePage extends BasePage{

    public final static ProductGallery homeGallery = new ProductGallery();

    private static final By AutomationLabel =
            By.xpath("//h1 //span[text()=\"Automation\"]");
    private static final By FeaturedItemsLabel =
            By.xpath("//h2[text()=\"Features Items\"]");

    public HomePage navigate() {
        navigateTo("https://automationexercise.com");
        return this;
    }

    public HomePage addProductToCart(String productName){
        homeGallery.addProductToCart(productName);
        return this;
    }

    public ProductDetails viewProduct(String productName){
        return homeGallery.viewProduct(productName);
    }

    public String getProductPrice(String productName){
        return homeGallery.getProductPrice(productName);
    }

    public HomePage filterByCategory(String category, String subCategory){
        homeGallery.filterByCategory(category,subCategory);
        return this;
    }

    public HomePage filterByBrand(String brandName){
        homeGallery.filterByBrand(brandName);
        return this;
    }

    public HomePage continueShopping(){
        homeGallery.continueShopping();
        return this;
    }

    public CartPage viewCart(){
        return homeGallery.viewCart();
    }


    /** Validations **/

    public HomePage verifyOnHomePage(){
        Assert.assertTrue(HomePage.isPageLoaded());
        return this;
    }

    public static boolean  isPageLoaded(){
        log.debug("Checking if page is loaded");
        log.debug("Current URL: " + getCurrentUrl());
        log.debug("Home Page Button: " + NavigationBar.checkButtonEnabled(NavigationBar.homePageButton));
        log.debug("Automation Label: " + ElementDisplayed(AutomationLabel));
        log.debug("Featured Items Label: " + ElementDisplayed(FeaturedItemsLabel));
        return NavigationBar.checkButtonEnabled(NavigationBar.homePageButton)
                && ElementDisplayed(AutomationLabel)
                && ElementDisplayed(FeaturedItemsLabel);
    }
    private HomePage validateFilterByCategorySuccessful(String category, String subCategory) {
        homeGallery.validateFilterByCategorySuccessful(category,subCategory);
        return this;
    }

    public HomePage validateProductAddedToCart(){
        homeGallery.validateProductAddedToCart();
        return this;
    }

    public HomePage validatePriceEquals(String productName, int price){
        homeGallery.validatePriceEquals(productName, price);
        return this;
    }
}
