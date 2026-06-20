package org.blazedemo.pages.components;

import org.blazedemo.pages.ProdcutsPage;
import org.openqa.selenium.By;

import static org.blazedemo.utils.actions.ElementsActions.click;

public class CategoryAndBrandSidebar {

    /** Locators **/
    // dynamic locators

    public static By productsSearchByCategory(String categoryName){
        return By.cssSelector("a[href=\"" + "#" +
                categoryName +
                "\"]");
    }

    public static By productsSearchBySubCategory(String categoryName, String subCategoryName){
        return By.xpath("//div[@id='" +
                categoryName +
                "']//a[contains(.,'"+ subCategoryName +"')]");
    }

    public static By productsSearchByBrand(String brandName){
        return By.cssSelector("a[href='/brand_products/" + brandName + "']");
    }

    public ProductGallery filterByCategory(String category, String subCategory){
        click(productsSearchByCategory(category));
        click(productsSearchBySubCategory(category, subCategory));
        return new ProductGallery();
    }

    public ProductGallery filterByBrand(String brandName){
        click(productsSearchByBrand(brandName));
        return new ProductGallery();
    }
}
