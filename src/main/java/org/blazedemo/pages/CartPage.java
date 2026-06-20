package org.blazedemo.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import static org.blazedemo.utils.actions.BrowserActions.navigateTo;
import static org.blazedemo.utils.actions.ElementsActions.click;
import static org.blazedemo.utils.actions.ElementsActions.getText;

public class CartPage extends BasePage{

    /** Locators **/

    // Product Info
    private static final By proceedToCheckoutButton = By.cssSelector(".btn.check_out");

    // Dynamic Locators
    private static By getProductNameLocator(String productName){
        return By.xpath("//h4/a[.='" +
                productName +
                "']");
    }

    private static By getProductPriceLocator(String productName){
        return By.xpath("//h4/a[.='" +
                productName +
                "']/../../following-sibling::td[@class='cart_price']/p");
    }

    private static By getProductQuantityLocator(String productName){
        return By.xpath("//h4/a[.='" +
                productName +
                "']/../../following-sibling::td[@class='cart_quantity']/button");
    }

    private static By getProductTotalPriceLocator(String productName){
        return By.xpath("//h4/a[.='" +
                productName +
                "']/../../following-sibling::td[@class=\"cart_price\"]/p");
    }

    private static By getProductDeleteLocator(String productName){
        return By.xpath("//h4/a[.='" +
                productName +
                "']/../../following-sibling::td[@class='cart_delete']/a");
    }

    /** Actions **/

    @Override
    public CartPage navigate() {
        navigateTo("https://automationexercise.com/view_cart");
        return this;
    }

    public CheckoutPage checkout(){
        click(proceedToCheckoutButton);
        return new CheckoutPage();
    }

    public CartPage removeProductFromCart(String productName){
        click(getProductDeleteLocator(productName));
        return this;
    }
    public String getProductName(String productName){
        return getText(getProductNameLocator(productName));
    }

    public String getProductPrice(String productName){
        return getText(getProductNameLocator(productName)).substring(4);
    }

    public String getProductQuantity(String productName){
        return getText(getProductQuantityLocator(productName));
    }

    public String getProductTotalPrice(String productName){
        return getText(getProductTotalPriceLocator(productName));
    }

    /** Validations **/

    public CartPage validateProductDetails(String productName,
                                           String price,
                                           String Quantity,
                                           String totalPrice)
    {
        Assert.assertEquals(getProductName(productName), productName);
        Assert.assertEquals(getProductPrice(productName), price);
        Assert.assertEquals(getProductQuantity(productName), Quantity);
        Assert.assertEquals(getProductTotalPrice(productName), totalPrice);
        return this;
    }
}
