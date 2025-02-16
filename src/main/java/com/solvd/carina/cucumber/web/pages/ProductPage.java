package com.solvd.carina.cucumber.web.pages;

import com.solvd.carina.cucumber.common.ProductPageBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends ProductPageBase {

    @FindBy(xpath = "//div[@id='shopping_cart_container']")
    private ExtendedWebElement inventoryContainer;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isProductPageOpened() {
        return inventoryContainer.isElementPresent();
    }
}
