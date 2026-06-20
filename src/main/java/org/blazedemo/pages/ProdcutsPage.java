package org.blazedemo.pages;

import org.blazedemo.pages.components.ProductGallery;
import org.openqa.selenium.By;

import static org.blazedemo.utils.actions.BrowserActions.navigateTo;
import static org.blazedemo.utils.actions.ElementsActions.*;

public class ProdcutsPage extends BasePage{

    private static final ProductGallery homeGallery = new ProductGallery();

    /** Locators **/
    private static final By saleImageLocator  = By.cssSelector("#advertisement #sale_image");
    private static final By searchInputField  = By.id("search_product");
    private static final By searchSubmit      = By.id("submit_search");

    /** Actions **/

    @Override
    public ProdcutsPage navigate() {
        navigateTo("https://automationexercise.com/products");
        return this;
    }

    public ProdcutsPage searchForProducts(String productName){
        sendText(searchInputField, productName);
        click(searchSubmit);
        return this;
    }

    public ProdcutsPage addProductToCart(String productName){
        homeGallery.addProductToCart(productName);
        return this;
    }

    public ProductDetails viewProduct(String productName){
        return homeGallery.viewProduct(productName);
    }

    public String getProductPrice(String productName){
        return homeGallery.getProductPrice(productName);
    }

    public ProdcutsPage filterByCategory(String category, String subCategory){
        homeGallery.filterByCategory(category,subCategory);
        return this;
    }

    public ProdcutsPage filterByBrand(String brandName){
        homeGallery.filterByBrand(brandName);
        return this;
    }

    public ProdcutsPage continueShopping(){
        homeGallery.continueShopping();
        return this;
    }

    public CartPage viewCart(){
        return homeGallery.viewCart();
    }


    /** Validations **/
    public static boolean isPageLoaded(){
        return ElementDisplayed(saleImageLocator)
                && ProductGallery.isGalleryLoaded();
    }

    private ProdcutsPage validateFilterByCategorySuccessful(String category, String subCategory) {
        homeGallery.validateFilterByCategorySuccessful(category,subCategory);
        return this;
    }

    public ProdcutsPage validateProductAddedToCart(){
        homeGallery.validateProductAddedToCart();
        return this;
    }

    public ProdcutsPage validatePriceEquals(String productName, int price){
        homeGallery.validatePriceEquals(productName, price);
        return this;
    }
}
