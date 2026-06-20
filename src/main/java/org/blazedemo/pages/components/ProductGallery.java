package org.blazedemo.pages.components;

import org.blazedemo.pages.CartPage;
import org.blazedemo.pages.ProdcutsPage;
import org.blazedemo.pages.ProductDetails;
import org.openqa.selenium.By;
import org.testng.Assert;

import static org.blazedemo.utils.actions.ElementsActions.*;
import static org.blazedemo.utils.actions.ElementsActions.ElementDisplayed;
import static org.blazedemo.utils.actions.ElementsActions.click;
import static org.blazedemo.utils.actions.ElementsActions.getText;
import static org.blazedemo.utils.actions.ElementsActions.hover;

public class ProductGallery {

    private static final CategoryAndBrandSidebar categoryBrandSidebar = new CategoryAndBrandSidebar();

    private static final By AllProductsHeader = By.cssSelector(".features_items > h2");
    // dynamic locators
    private static By addToCartButton(String productName){
        return By.xpath(
                "//div[contains(@class,'overlay-content')]//p[normalize-space()='"
                        + productName +"']/following-sibling::a");
    }
    private static By priceLabel(String productName){
        return By.xpath(
                "//div[contains(@class,'overlay-content')]//p[normalize-space()='"+
                        productName +"']/preceding-sibling::h2[1]\n");
    }

    private static By getViewProductButton(String productName){
        return By.xpath("//div[contains(@class,'overlay-content')]//p[normalize-space()='" +
                productName +
                "']/../../../following-sibling::div[@class='choose']//a\n");
    }

    private static By getHoveringElement(String productName){
        return By.xpath("//p[.=\"" + productName + "\"]/..");
    }

    /** Actions **/

    public ProductGallery addProductToCart(String productName){
        By productLocator = getHoveringElement(productName);
        scrollIntoElementSelenium(productLocator);
        hover(productLocator);
        click(addToCartButton(productName));
        return this;
    }

    public ProductDetails viewProduct(String productName){
        hover(getHoveringElement(productName));
        click(getViewProductButton(productName));
        return new ProductDetails();
    }

    public String getProductPrice(String productName){
        hover(getHoveringElement(productName));
        return getText(priceLabel(productName));
    }

    public ProductGallery filterByCategory(String category, String subCategory){
        categoryBrandSidebar
                .filterByCategory(category, subCategory)
                .validateFilterByCategorySuccessful(category, subCategory);
        return this;
    }

    public ProductGallery filterByBrand(String brandName){
        categoryBrandSidebar
                .filterByBrand(brandName)
                .validateFilterByBrandSuccessful(brandName);
        return this;
    }

    public ProductGallery continueShopping(){
        ProductAddedToCartWindow.continueShopping();
        return this;
    }

    public CartPage viewCart(){
        return ProductAddedToCartWindow.viewCart();
    }

    /** Validations **/
    public static boolean isGalleryLoaded(){
        return ElementDisplayed(AllProductsHeader)
                && getText(AllProductsHeader).toUpperCase().contains("PRODUCTS");
    }
    public ProductGallery validateFilterByCategorySuccessful(String category, String subCategory) {
        Assert.assertTrue(
                ElementDisplayed(AllProductsHeader) &&
                        getText(AllProductsHeader).toUpperCase().contains("PRODUCTS") &&
                        getText(AllProductsHeader).toUpperCase().contains(category.toUpperCase()) &&
                        getText(AllProductsHeader).toUpperCase().contains(subCategory.toUpperCase())
        );
        return this;
    }

    public ProductGallery validateFilterByBrandSuccessful(String brandName) {
        Assert.assertTrue(
                ElementDisplayed(AllProductsHeader) &&
                        getText(AllProductsHeader).toUpperCase().contains("PRODUCTS") &&
                        getText(AllProductsHeader).toUpperCase().contains(brandName.toUpperCase())
        );
        return this;
    }

    public ProductGallery validateProductAddedToCart(){
        ProductAddedToCartWindow.validateProductAddedToCart();
        return this;
    }

    public ProductGallery validatePriceEquals(String productName, int price){
        Assert.assertEquals(getProductPrice(productName).substring(4), Integer.toString(price));
        return this;
    }
}
