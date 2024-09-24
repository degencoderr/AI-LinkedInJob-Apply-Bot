package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.IOException;
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
        try {
            JobFilter jobFilter = FilterLoader.loadFilters("filters.json");

            LinkedInJobSearch jobSearch = new LinkedInJobSearch(driver);
            jobSearch.navigateToJobSearch(jobFilter);
            // Continue with using jobFilter
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception (log it or print the stack trace)
            System.out.println("Error loading job filters from JSON file.");
        }

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
