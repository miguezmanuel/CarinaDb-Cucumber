package com.solvd.carina.cucumber.steps;

import com.solvd.mappers.UserMapper;
import com.solvd.models.User;
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

import static org.testng.AssertJUnit.assertTrue;


public class LoginSteps {
    private WebDriver driver;
    private User user;

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

    @When("the user logs into SauceDemo")
    public void theUserLogsIntoSauceDemo() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys(user.getUsername());
        passwordField.sendKeys(user.getPassword());
        loginButton.click();
    }

    @Then("the products page should load successfully")
    public void theProductsPageShouldLoadSuccessfully() {
        assertTrue(driver.getCurrentUrl().contains("inventory.html"));

        driver.quit();
    }
}
