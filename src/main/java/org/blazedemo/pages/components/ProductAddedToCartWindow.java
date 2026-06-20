package org.blazedemo.pages.components;

import org.blazedemo.pages.CartPage;
import org.blazedemo.pages.ProdcutsPage;
import org.openqa.selenium.By;
import org.testng.Assert;

import static org.blazedemo.utils.actions.ElementsActions.*;

public class ProductAddedToCartWindow {
    private static final By productAddedModal       = By.cssSelector(".modal-content");
    private static final By modalTitle              = By.cssSelector(".modal-content h4");
    private static final By viewCartLink            = By.cssSelector(".modal-content a");
    private static final By continueShoppingButton  = By.cssSelector(".modal-content button");
    private static final By productAddedMessage     = By.cssSelector(".modal-content p");
    private static final String SuccessfulProductAdditionMsg    = "Your product has been added to cart.";

    public static void validateProductAddedToCart(){
        Assert.assertTrue(
                ElementDisplayed(productAddedModal) &&
                        ElementDisplayed(modalTitle) &&
                        ElementDisplayed(viewCartLink) &&
                        ElementDisplayed(continueShoppingButton)
        );
        Assert.assertEquals(getText(productAddedMessage), SuccessfulProductAdditionMsg);
    }

    public static void continueShopping(){
        click(continueShoppingButton);
    }

    public static CartPage viewCart(){
        click(viewCartLink);
        return new CartPage();
    }
}
