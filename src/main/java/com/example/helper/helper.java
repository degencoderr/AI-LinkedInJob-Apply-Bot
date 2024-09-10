package com.example.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Random;

public class helper {

    private WebDriver driver;
    private Wait<WebDriver> wait;
    private JavascriptExecutor js;
    private Random rand;
    private Logger logger;
    public static final int SHORT_SLEEP = 1;
    public static final int MEDIUM_SLEEP = 2;
    public static final int LONG_SLEEP = 5;

    // Constructor to initialize the WebDriver and other utilities
    public helper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        this.rand = new Random();
        this.logger = LoggerFactory.getLogger(helper.class);
    }

    // Method to click a button by its XPath
    public void clickButtonByXPath(String xpath) {
        WebElement button = driver.findElement(By.xpath(xpath));
        wait.until(d -> button.isDisplayed());
        button.click();
    }





    public void findAndClickById(String id) {
        WebElement sortByMostRecent = driver.findElement(By.id(id));
        clickWebElement(sortByMostRecent);
    }
    public void findScrollAndClickById(String jobTypeFilter) {
        WebElement contractExperienceLevel = driver.findElement(By.id(jobTypeFilter));
        scrollToWebElement(contractExperienceLevel);
        clickWebElement(contractExperienceLevel);
    }
    public void findScrollAndClickByXPath(
            String easyApplyToggleXPath) {
        WebElement easyApplyToggle = driver
                .findElement(By.xpath(easyApplyToggleXPath));

        scrollToWebElement(easyApplyToggle);
        clickWebElement(easyApplyToggle);
    }
    public void findScrollAndClickByXPath(
            WebElement easyApplyToggleXPath) {

        scrollToWebElement(easyApplyToggleXPath);
        clickWebElement(easyApplyToggleXPath);
    }

    public void scrollToWebElement(WebElement contractExperienceLevel) {
        js.executeScript("arguments[0].scrollIntoView();", contractExperienceLevel);
    }

    public void scrollToWebElement(WebElement contractExperienceLevel, boolean sleep) {
        js.executeScript("arguments[0].scrollIntoView();", contractExperienceLevel);
        if (sleep)
            randomSleep(SHORT_SLEEP);
    }

    // Method to send text to a web element
    public void sendKeysToWebElement(String text, WebElement element) {
        element.clear();
        element.sendKeys(text);
        randomSleep(SHORT_SLEEP); // Optional sleep to mimic human interaction
    }

    public void clickWebElement(WebElement webElement) {
        js.executeScript("arguments[0].click();", webElement);
    }

    // Helper method to introduce random sleep
    public void randomSleep(int sleepSeconds) {
        try {
            int sleeptime = rand.nextInt(3000) + (sleepSeconds * 1000);
            logger.info("Sleeping for " + sleeptime + " milliseconds");
            Thread.sleep(sleeptime);
            logger.info("Sleeping for " + sleeptime + " milliseconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Optional: Add more helper methods as needed
}
