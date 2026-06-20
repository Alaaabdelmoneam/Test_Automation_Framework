package org.blazedemo.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import static org.blazedemo.utils.actions.BrowserActions.navigateTo;
import static org.blazedemo.utils.actions.ElementsActions.*;

public class PaymentPage {

    private static final By nameOnCard = By.cssSelector("input[name='name_on_card']");
    private static final By cardNumber = By.cssSelector("input[name='card_number']");
    private static final By CVC = By.cssSelector("input[name='cvc']");
    private static final By expiryMonth = By.cssSelector("input[name='expiry_month']");
    private static final By expiryYear = By.cssSelector("input[name='expiry_year']");
    private static final By confirmOrder = By.id("submit");

    public static final By orderPlacedMsgLocator = By.cssSelector("h2 b");
    public static final By continueToHomePage = By.cssSelector("a[data-qa='continue-button']");
    public static final String orderPlacedMsgSuccess = "Order Placed!";

    /** Actions **/

    public PaymentPage navigate(){
        navigateTo("https://automationexercise.com/payment");
        return this;
    }

    public PaymentPage pay(
            String nameOnCard,
            String cardNumber,
            String cvc,
            String expiryMonth,
            String expiryYear
    ){
        sendText(PaymentPage.nameOnCard, nameOnCard);
        sendText(PaymentPage.cardNumber, String.valueOf(cardNumber));
        sendText(PaymentPage.CVC, String.valueOf(cvc));
        sendText(PaymentPage.expiryMonth, String.valueOf(expiryMonth));
        sendText(PaymentPage.expiryYear, String.valueOf(expiryYear));
        click(confirmOrder);
        return this;
    }

    public PaymentPage validateOrderSuccessful(){
        Assert.assertTrue(
                ElementDisplayed(orderPlacedMsgLocator) &&
                        getText(orderPlacedMsgLocator).equalsIgnoreCase(orderPlacedMsgSuccess) &&
                        ElementDisplayed(continueToHomePage)
        );
        return this;
    }
    public HomePage continueToHomePage(){
        click(PaymentPage.continueToHomePage);
        return new HomePage();
    }
}
