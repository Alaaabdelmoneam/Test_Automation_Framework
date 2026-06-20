package org.blazedemo.pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.testng.Assert;

import static org.blazedemo.utils.actions.BrowserActions.navigateTo;
import static org.blazedemo.utils.actions.ElementsActions.*;

public class CheckoutPage extends BasePage {

    @Getter
    public enum AddressField{
        ADDRESS_TITLE_FIRST_LAST_NAME("address_firstname address_lastname", 1),
        ADDRESS_COMPANY("address_address1 address_address2", 1),
        ADDRESS_1("address_address1 address_address2", 2),
        ADDRESS_2("address_address1 address_address2", 3),
        ADDRESS_CITY_STATE_POSTCODE("address_city address_state_name address_postcode", 1),
        ADDRESS_COUNTRY_NAME("address_country_name", 1),
        ADDRESS_PHONE("address_phone", 1);

        private final String locatorClass;
        private final int index;

        AddressField(String locatorClass, int index){
            this.locatorClass = locatorClass;
            this.index = index;
        }
    }

    @Getter
    public enum AddressCard{
        ADDRESS_DELIVERY("address_delivery"),
        ADDRESS_INVOICE("address_invoice");

        private final String locatorID;
        AddressCard(String locatorID){
            this.locatorID = locatorID;
        }
    }


    @Getter
    public enum ProductInfo{
        DESCRIPTION("cart_description", "p"),
        PRICE("cart_price", "p"),
        QUANTITY("cart_quantity", "button"),
        TOTAL_PRICE("cart_total", "p");

        private final String locatorClass;
        private final String elementContainingInfo;
        ProductInfo(String locatorClass, String elementContainingInfo){
            this.locatorClass = locatorClass;
            this.elementContainingInfo = elementContainingInfo;
        }
    }

    /** Locators **/

    // Static Locators
    @Getter
    private static final By totalCartPrice = By.xpath("//td[@colspan='2']/../td/p");


    private static final By placeOrderButton  = By.cssSelector("a[href='/payment']");
    private static final By placeOrderComment = By.cssSelector(".form-control");

    // Dynamic Locators
    public By getAddressData(AddressCard addressType, AddressField field){

        return By.xpath("//ul[@id='" + addressType.getLocatorID() + "']" +
                "//li[@class='" +field.getLocatorClass()+"']" +
                "[" + field.getIndex() + "]");
    }

    public By getProductDetails(String productName, ProductInfo requiredInfo){
        return By.xpath("//table//h4//a[.='" + productName + "']" +
                "/../../../td[@class='" +
                requiredInfo.getLocatorClass() +
                "']/" + requiredInfo.elementContainingInfo);
    }

    /** Actions **/

    @Override
    public CheckoutPage navigate() {
        navigateTo("https://automationexercise.com/checkout");
        return this;
    }

    public CheckoutPage writeCommentOnOrder(String comment){
        sendText(placeOrderComment, comment);
        return this;
    }

    public PaymentPage placeOrder(){
        click(placeOrderButton);
        return new PaymentPage();
    }

    public CheckoutPage verifyAddressDataField
            (AddressCard addressCard, AddressField field, String expected)
    {
        Assert.assertEquals(
                getText(getAddressData(addressCard, field)),
                expected);
        return this;
    }

    public CheckoutPage verifyAddressData
            (AddressCard addressCard, String title, String FName, String LName,
             String company, String firstAddress, String secondAddress, String city,
             String state, int postalCode, String country, String phoneNumber
            )
    {
        Assert.assertEquals(
                getText(getAddressData(
                        addressCard, AddressField.ADDRESS_TITLE_FIRST_LAST_NAME)).split(" ")[0]
                ,
                title
        );
        Assert.assertEquals(
                getText(getAddressData(
                        addressCard, AddressField.ADDRESS_TITLE_FIRST_LAST_NAME)).split(" ")[1]
                ,
                FName
        );

        Assert.assertEquals(
                getText(getAddressData(
                        addressCard, AddressField.ADDRESS_TITLE_FIRST_LAST_NAME)).split(" ")[2]
                ,
                LName
        );

        Assert.assertEquals(
                getText(getAddressData(
                        addressCard, AddressField.ADDRESS_COMPANY))
                ,
                company
        );

        Assert.assertEquals(
                getText(getAddressData(
                        addressCard, AddressField.ADDRESS_1))
                ,
                firstAddress
        );


        Assert.assertEquals(
                getText(getAddressData(
                        addressCard, AddressField.ADDRESS_2))
                ,
                secondAddress
        );


        Assert.assertEquals(
                getText(getAddressData(
                        addressCard, AddressField.ADDRESS_CITY_STATE_POSTCODE)).split(" ")[0]
                ,
                city
        );

        Assert.assertEquals(
                getText(getAddressData(
                        addressCard, AddressField.ADDRESS_CITY_STATE_POSTCODE)).split(" ")[1]
                ,
                state
        );

        Assert.assertEquals(
                getText(getAddressData(
                        addressCard, AddressField.ADDRESS_CITY_STATE_POSTCODE)).split(" ")[2]
                ,
                String.valueOf(postalCode)
        );

        Assert.assertEquals(
                getText(getAddressData(
                        addressCard, AddressField.ADDRESS_COUNTRY_NAME))
                ,
                country
        );

        Assert.assertEquals(
                getText(getAddressData(
                        addressCard, AddressField.ADDRESS_PHONE))
                ,
                phoneNumber
        );
        return this;
    }
}
