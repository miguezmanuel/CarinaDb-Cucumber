package com.solvd.carina.cucumber.common;


import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;

public abstract class LoginPageBase extends AbstractPage {

    protected LoginPageBase(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public abstract void typeUsername(String username);

    public abstract void typePassword(String password);

    public abstract void clickLoginButton();

    public abstract ProductPageBase login(String username, String password);
}
