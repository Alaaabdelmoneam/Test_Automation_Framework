package org.blazedemo.tests.ui;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.blazedemo.pages.ProdcutsPage;
import org.blazedemo.tests.basetest.BaseTest;
import org.testng.annotations.Test;

@Log4j2
@Epic("Automation Exercise")
@Feature("User Management")
@Story("Product Management")
@Owner("Alaa")
public class ProductsTestcases extends BaseTest {

    @Test
    @Description("Adding a Valid Product to cart")
    public void addToCartValid(){
        new ProdcutsPage().navigate().addProductToCart("Men Tshirt").validateProductAddedToCart();
    }

    @Test
    @Description("Adding invalid Product to cart")
    public void addToCartInvalid(){
        new ProdcutsPage().navigate().addProductToCart("Invalidd");
    }

    @Test
    @Description("View a Valid Product")
    public void viewProductValid(){
        new ProdcutsPage().navigate().viewProduct("Men Tshirt");
    }

    @Test
    @Description("View an invalid Product")
    public void viewProductInvalid(){
        new ProdcutsPage().navigate().addProductToCart("Invalidd");
    }

    @Test
    @Description("Search for an existing product")
    public void searchForExistingProduct(){
        new ProdcutsPage().navigate().searchForProducts("Men Tshirt");
    }

    @Test
    @Description("Search for a non-existing product")
    public void searchForNonExistingProduct(){
        new ProdcutsPage().navigate().searchForProducts("Invalidd");
    }

    @Test
    @Description("Filter Products by valid category")
    public void filterProductsByValidCategory(){
        new ProdcutsPage().navigate().filterByCategory("Men", "Tshirts");
    }

    @Test
    @Description("Filter Products by invalid category")
    public void filterProductsByInvalidCategory(){
        new ProdcutsPage().navigate().filterByCategory("Invalidd", "Tshirts");
    }

    @Test
    @Description("Filter Products by invalid sub-category")
    public void filterProductsByInvalidSubCategory(){
        new ProdcutsPage().navigate().filterByCategory("Men", "Invalidd");
    }

    @Test
    @Description("Filter Products by valid brand")
    public void filterProductsByValidBrand(){
        new ProdcutsPage().navigate().filterByBrand("Polo");
    }

    @Test
    @Description("Filter Products by invalid brand")
    public void filterProductsByInvalidBrand(){
        new ProdcutsPage().navigate().filterByBrand("Invalidd");
    }
}