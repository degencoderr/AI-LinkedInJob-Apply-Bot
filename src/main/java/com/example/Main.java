package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Main {
    private static WebDriver driver;

    public static void main(String[] args) {
        // Setup EdgeDriver
        String driverPath = Paths.get("src", "main", "resources", "msedgedriver.exe").toAbsolutePath().toString();
        System.setProperty("webdriver.edge.driver", driverPath);

        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Navigate to LinkedIn login page
        driver.get("https://www.linkedin.com/login");

        // Perform login
        loginToLinkedIn("divyabattula33@gmail.com", "Harshi@1319");

        LinkedInJobSearch jobSearch = new LinkedInJobSearch(driver);
        jobSearch.navigateToJobSearch("Software Engineer", "United States");
        // Close the browser
        //driver.quit();
    }

    private static void loginToLinkedIn(String username, String password) {
        WebElement emailField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

        emailField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }
}
