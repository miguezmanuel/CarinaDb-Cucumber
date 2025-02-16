package com.solvd.carina.cucumber.steps;

import com.solvd.carina.cucumber.runners.Hooks;
import com.solvd.models.User;
import com.solvd.models.UserOrder;
import com.solvd.utils.DatabaseUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import java.util.List;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class OrderSteps {
    private WebDriver driver = Hooks.getDriver();
    private User user;
    private List<UserOrder> orders;

    @Given("the user has pending orders in the database")
    public void theUserHasPendingOrdersInTheDatabase() {
        user = DatabaseUtils.getUserByUsername("standard_user");
        orders = DatabaseUtils.getOrdersByUserId(user.getId());
        assertFalse("No orders found for user", orders.isEmpty());
    }

    @When("the user adds their orders to the cart and proceeds to checkout")
    public void theUserAddsTheirOrdersToTheCartAndProceedsToCheckout() {
        driver.get("https://www.saucedemo.com/");

        for (UserOrder order : orders) {
            WebElement product = driver.findElement(By.xpath("//div[text()='" + order.getProductName() + "']/ancestor::div[@class='inventory_item']//button"));
            product.click();
        }

        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
    }

    @Then("the order should be placed successfully")
    public void theOrderShouldBePlacedSuccessfully() {
        WebElement firstName = driver.findElement(By.id("first-name"));
        WebElement lastName = driver.findElement(By.id("last-name"));
        WebElement zipCode = driver.findElement(By.id("postal-code"));

        firstName.sendKeys("Test");
        lastName.sendKeys("User");
        zipCode.sendKeys("12345");

        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("finish")).click();

        WebElement confirmationMessage = driver.findElement(By.className("complete-header"));
        assertTrue("Order was not successfully placed!", confirmationMessage.getText().contains("Thank you for your order!"));
    }
}

