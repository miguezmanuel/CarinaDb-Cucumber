package com.solvd.carina.cucumber.steps;

import com.solvd.mappers.OrderMapper;
import com.solvd.mappers.UserMapper;
import com.solvd.models.User;
import com.solvd.models.UserOrder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class OrderSteps {
    private WebDriver driver;
    private User user;
    private List<UserOrder> orders;

    @Given("the user {string} exists in the database")
    public void theUserExistsInTheDatabase(String username) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            user = userMapper.getUserByUsername(username);
        }
        assertTrue("User does not exist in the database", user != null);
    }

    @Given("the user has pending orders in the database")
    public void theUserHasPendingOrdersInTheDatabase() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            orders = orderMapper.getOrdersByUserId(user.getId());
        }
        assertTrue("No orders found for user", orders != null && !orders.isEmpty());
    }

    @When("the user adds their orders to the cart and proceeds to checkout")
    public void theUserAddsTheirOrdersToTheCartAndProceedsToCheckout() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys(user.getUsername());
        driver.findElement(By.id("password")).sendKeys(user.getPassword());
        driver.findElement(By.id("login-button")).click();

        for (UserOrder order : orders) {
            WebElement addToCartButton = driver.findElement(By.xpath("//div[text()='" + order.getProductName() + "']/following-sibling::button"));
            addToCartButton.click();
        }

        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("finish")).click();
    }

    @Then("the order should be placed successfully")
    public void theOrderShouldBePlacedSuccessfully() {
        WebElement confirmationMessage = driver.findElement(By.className("complete-header"));
        assertTrue(confirmationMessage.getText().contains("Thank you for your order!"));
        driver.quit();
    }
}

