package com.solvd.carina.cucumber.web.pages;

import com.solvd.carina.cucumber.common.LoginPageBase;
import com.solvd.carina.cucumber.common.ProductPageBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;


public class LoginPage extends LoginPageBase {

    @FindBy(xpath = "//input[@id='user-name']")
    private ExtendedWebElement usernameField;

    @FindBy(xpath = "//input[@id='password']")
    private ExtendedWebElement passwordField;

    @FindBy(xpath = "//input[@type='submit']")
    private ExtendedWebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void typeUsername(String username) {
        usernameField.type(username);
    }

    @Override
    public void typePassword(String password) {
        passwordField.type(password);
    }

    @Override
    public void clickLoginButton() {
        loginButton.click();
    }

    @Override
    public ProductPageBase login(String username, String password) {
        typeUsername(username);
        typePassword(password);
        clickLoginButton();
        return initPage(getDriver(), ProductPageBase.class);
    }
}
