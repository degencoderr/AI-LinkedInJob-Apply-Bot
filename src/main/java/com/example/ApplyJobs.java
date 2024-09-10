package com.example;

import com.example.helper.helper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ApplyJobs {

    private WebDriver driver;
    private com.example.helper.helper helper;
    private Logger logger;
    private handleQuestionnaire handleQuestionnaire;
    private Wait<WebDriver> wait;
    private JavascriptExecutor js;

    // Constructor to initialize the WebDriver and Utility
    public ApplyJobs(WebDriver driver) {
        this.driver = driver;
        this.helper = new helper(driver); // Initialize Utility with driver
        this.handleQuestionnaire = new handleQuestionnaire(driver);
        this.logger = LoggerFactory.getLogger(ApplyJobs.class);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    // Method to handle Easy Apply form
    public boolean handleEasyApplyForm() {
        long startTime = System.currentTimeMillis();
        long timeoutInMillis = Duration.ofMinutes(10).toMillis();  // Set timeout duration

        try {
            while (true) {
                // Check if the overall timeout has occurred
                if ((System.currentTimeMillis() - startTime) >= timeoutInMillis) {
                    logger.warn("Application process timed out after " + (timeoutInMillis / 1000) + " seconds.");
                    return false; // Return false to indicate failure due to timeout
                }

                // Check if it's a single-page or multi-page application
                WebElement submitButton = null;
                WebElement nextButton = null;
                try {
                    submitButton = driver.findElement(By.xpath("//button[@aria-label='Submit application']"));
                } catch (NoSuchElementException e) {
                    nextButton = driver.findElement(By.xpath("//button[@aria-label='Continue to next step']"));
                }

                if (submitButton != null) {
                    // Handle single-page application submission
                    logger.info("Single-page application detected.");
                    js.executeScript("arguments[0].scrollIntoView();", submitButton);
                    submitButton.click();
                    logger.info("Job application submitted.");
                    helper.randomSleep(5);
                    String closePopUp = "//button[@aria-label='Dismiss']";
                    helper.findScrollAndClickByXPath(closePopUp);
                    helper.randomSleep(5);
                    return true; // Successfully submitted
                }

                if (nextButton != null) {
                    // Handle multi-page application
                    logger.info("Multi-page application detected. Proceeding to the next step.");
                    handleMultiPageForm();
                }
            }
        } catch (Exception e) {
            logger.error("Error in Easy Apply form: " + e.getMessage());
            return false;
        }
    }

    private void handleMultiPageForm() {
        try {
            // Go through contact info, resume, and additional questions
            while (true) {
                // Check for "Next" or "Review your application" button
                WebElement nextButton = null;
                WebElement reviewButton = null;

                try {
                    nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Continue to next step']")));
                } catch (TimeoutException e) {
                    try {
                        reviewButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Review your application']")));
                    } catch (TimeoutException ex) {
                        logger.warn("Neither Next nor Review button found, proceeding to submit.");
                    }

                }

                // Handle experience, work, visa-related questions
                handleQuestionnaire.handleApplicationQuestions();

                // If the next button is found, click it
                if (nextButton != null) {
                    js.executeScript("arguments[0].scrollIntoView();", nextButton);
                    nextButton.click();
                }

                // If the review button is found, click it
                if (reviewButton != null) {
                    js.executeScript("arguments[0].scrollIntoView();", reviewButton);
                    reviewButton.click();
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("Error handling multi-page form: " + e.getMessage());
        }
    }





}
