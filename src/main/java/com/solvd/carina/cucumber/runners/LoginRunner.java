package com.solvd.carina.cucumber.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.solvd.carina.cucumber.steps",
        plugin = {"pretty", "html:target/cucumber-report.html"}
)
public class LoginRunner {
}