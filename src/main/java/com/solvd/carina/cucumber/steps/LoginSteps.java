package com.solvd.carina.cucumber.steps;

import com.solvd.carina.cucumber.common.LoginPageBase;
import com.solvd.carina.cucumber.common.ProductPageBase;
import com.solvd.models.User;
import com.solvd.carina.cucumber.runners.Hooks;
import com.solvd.utils.DatabaseUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.testng.AssertJUnit.assertTrue;

public class LoginSteps {
    private WebDriver driver = Hooks.getDriver();
    private User user;
    private LoginPageBase loginPage;
    private ProductPageBase productPage;

    @Given("the user {string} exists in the database")
    public void theUserExistsInTheDatabase(String username) {
        user = DatabaseUtils.getUserByUsername(username);
        assertTrue("User does not exist in the database", user != null);
    }

    @When("the user logs into SauceDemo")
    public void theUserLogsIntoSauceDemo() {
        loginPage = loginPage.initPage(driver, LoginPageBase.class);
        productPage = loginPage.login(user.getUsername(), user.getPassword());
    }

    @Then("the product page should be displayed")
    public void theProductPageShouldBeDisplayed() {
        assertTrue("Product page is not displayed!", productPage.isProductPageOpened());
    }
}
