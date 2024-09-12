package com.example;

import com.example.helper.helper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class LinkedInJobSearch {

    private WebDriver driver;
    private helper helper;
    private ApplyJobs ApplyJobs;
    private Logger logger;
    private Wait<WebDriver> wait;
    private JavascriptExecutor js;

    // Constructor to initialize the WebDriver and Utility
    public LinkedInJobSearch(WebDriver driver) {
        this.driver = driver;
        this.helper = new helper(driver); // Initialize Utility with driver
        this.ApplyJobs = new ApplyJobs(driver);
        this.logger = LoggerFactory.getLogger(LinkedInJobSearch.class);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    // Method to navigate to LinkedIn job search page and enter search criteria
    public void navigateToJobSearch(JobFilter jobFilter) {
        driver.get("https://www.linkedin.com/jobs/");

        WebElement searchField = driver.findElement(By.xpath("//input[@role='combobox' and contains(@autocomplete, 'organization-title')]"));
        WebElement locationField = driver.findElement(By.xpath("//input[@role='combobox' and contains(@autocomplete, 'address-level2')]"));

        helper.sendKeysToWebElement(jobFilter.getPosition(), searchField);
        helper.sendKeysToWebElement(jobFilter.getLocation(), locationField);

        locationField.sendKeys(Keys.ENTER);
        String closeMessagesButtonXPath = "/html/body/div[5]/div[4]/aside[1]/div[1]/header/div[2]/button";
        helper.clickButtonByXPath(closeMessagesButtonXPath);
        try {
            Thread.sleep(5000); // Wait for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        applyFilters(jobFilter);
        try {
            Thread.sleep(10000); // Wait for 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        applyToJobs();


    }





    public void applyFilters(JobFilter jobFilter){
        String allFiltersButton = "//div[@class='relative mr2']/button";
        helper.clickButtonByXPath(allFiltersButton);
        String id = "advanced-filter-sortBy-DD";
        helper.findAndClickById(id);
//        String timeFilter = "advanced-filter-timePostedRange-r86400";
//        helper.findAndClickById(timeFilter);
//        String jobTypeFilter = "advanced-filter-jobType-C";
//        helper.findScrollAndClickById(jobTypeFilter);


        applyDatePosted(jobFilter);
        applyExperienceLevel(jobFilter);
        applyJobType(jobFilter);
        applyRemote(jobFilter);
        String easyApplyToggleXPath = "//input[@role='switch' and contains(@class, 'artdeco-toggle__button')]";
        helper.findScrollAndClickByXPath(easyApplyToggleXPath);
        String showResultsButtonXPath = "//button[@data-test-reusables-filters-modal-show-results-button='true']";
        helper.findScrollAndClickByXPath(showResultsButtonXPath);



    }

    // Method to iterate through job listings and apply
    public void applyToJobs() {
        // Find the job cards list using XPath
        List<WebElement> jobCards = driver.findElements(By.xpath("//ul[contains(@class, 'scaffold-layout__list-container')]/li"));
        String card = "//ul[contains(@class, 'scaffold-layout__list-container')]/li[7]";
        for (WebElement jobCard : jobCards) {
            try {
                helper.findScrollAndClickByXPath(jobCard);
                //WebElement jobCard = driver.findElement(By.xpath(card));
                WebElement divElement = jobCard.findElement(By.xpath("./div/div/div[1]/div[1]/div[2]/div[1]"));
                divElement.click();
                helper.randomSleep(5);
                boolean isEasyApply = driver.findElements(By.xpath(".//div[contains(@class, 'jobs-apply-button--top-card')]"))
                        .stream()
                        .anyMatch(element -> element.getText().contains("Easy Apply"));

                if (isEasyApply) {
                    // Click the Easy Apply button
                    WebElement easyApplyButton = driver.findElement(By.xpath("//div[contains(@class, 'jobs-apply-button--top-card')]/button"));
                    easyApplyButton.click();

                    // Handle the Easy Apply form
                    ApplyJobs.handleEasyApplyForm();

                    logger.info("Successfully applied to job: " + divElement.getAttribute("aria-label"));
                } else {
                    logger.info("Skipping job without Easy Apply: " + divElement.getAttribute("aria-label"));
                }
            }
            catch (Exception e){
                logger.error("Unexpected error: " + e.getMessage());
                closeJobCard();
            }
        }


    }

    private void closeJobCard(){
        WebElement dismissButton = driver.findElement(By.xpath("//button[@aria-label='Dismiss']"));
        dismissButton.click();
        helper.randomSleep(5);
        if(driver.findElement(By.xpath("//div[contains(@class, 'artdeco-modal__header ember-view')]"))!= null){
            WebElement discardApplication = driver.findElement(By.xpath("//button[@data-control-name='discard_application_confirm_btn']"));
            discardApplication.click();
            helper.randomSleep(5);
            logger.info("closed an exception ");
        }

    }

    // Apply Date Posted Filter
    public void applyDatePosted(JobFilter jobFilter) {
        String datePosted = jobFilter.getDatePosted();
        switch (datePosted) {
            case "Any time":
                helper.findScrollAndClickById("advanced-filter-timePostedRange-");
                break;
            case "Past month":
                helper.findScrollAndClickById("advanced-filter-timePostedRange-r2592000");
                break;
            case "Past week":
                helper.findScrollAndClickById("advanced-filter-timePostedRange-r604800");
                break;
            case "Past 24 hours":
                helper.findScrollAndClickById("advanced-filter-timePostedRange-r86400");
                break;
        }
    }

    // Apply Experience Level Filter
    public void applyExperienceLevel(JobFilter jobFilter) {
        List<String> experienceLevels = jobFilter.getExperienceLevel();
        for (String level : experienceLevels) {
            switch (level) {
                case "Internship":
                    helper.findScrollAndClickById("advanced-filter-experience-1");
                    break;
                case "Entry level":
                    helper.findScrollAndClickById("advanced-filter-experience-2");
                    break;
                case "Associate":
                    helper.findScrollAndClickById("advanced-filter-experience-3");
                    break;
                case "Mid-Senior level":
                    helper.findScrollAndClickById("advanced-filter-experience-4");
                    break;
                case "Director":
                    helper.findScrollAndClickById("advanced-filter-experience-5");
                    break;
                case "Executive":
                    helper.findScrollAndClickById("advanced-filter-experience-6");
                    break;
            }
        }
    }

    // Apply Job Type Filter
    public void applyJobType(JobFilter jobFilter) {
        List<String> jobTypes = jobFilter.getJobType();
        for (String jobType : jobTypes) {
            switch (jobType) {
                case "Full-time":
                    helper.findScrollAndClickById("advanced-filter-jobType-F");
                    break;
                case "Part-time":
                    helper.findScrollAndClickById("advanced-filter-jobType-P");
                    break;
                case "Contract":
                    helper.findScrollAndClickById("advanced-filter-jobType-C");
                    break;
                case "Internship":
                    helper.findScrollAndClickById("advanced-filter-jobType-I");
                    break;
            }
        }
    }

    // Apply Remote Filter
    public void applyRemote(JobFilter jobFilter) {
        List<String> remoteOptions = jobFilter.getRemote();
        for (String option : remoteOptions) {
            switch (option) {
                case "On-site":
                    helper.findScrollAndClickById("advanced-filter-workplaceType-1");
                    break;
                case "Hybrid":
                    helper.findScrollAndClickById("advanced-filter-workplaceType-3");
                    break;
                case "Remote":
                    helper.findScrollAndClickById("advanced-filter-workplaceType-2");
                    break;
            }
        }
    }






    // Check if job was already applied to
    private boolean isThisJobApplied() {
        try {
            WebElement easyApplyBtnDiv = driver.findElement(By.xpath("//div[contains(@class, 'jobs-apply-button')]"));
            String className = "jobs-apply-button--top-card";
            return js.executeScript("return arguments[0].classList.contains(arguments[1]);", easyApplyBtnDiv, className) != null;

        } catch (Exception e) {
            return false;
        }
    }
}
