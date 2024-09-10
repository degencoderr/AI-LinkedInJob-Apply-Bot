package com.example;

import com.example.helper.helper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class handleQuestionnaire {
    private WebDriver driver;
    private com.example.helper.helper helper;
    private Logger logger;
    private Wait<WebDriver> wait;
    private JavascriptExecutor js;

    // Constructor to initialize the WebDriver and Utility
    public handleQuestionnaire(WebDriver driver) {
        this.driver = driver;
        this.helper = new helper(driver); // Initialize Utility with driver
        this.logger = LoggerFactory.getLogger(ApplyJobs.class);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public void handleApplicationQuestions() {
        try {
            List<WebElement> questions = driver.findElements(By.xpath("//div[contains(@class, 'jobs-easy-apply-form-section__grouping')]"));

            for (WebElement question : questions) {
                String questionText = question.getText().toLowerCase(); // Get the question text

                // Match the question with the provided answer
                String answer = getAnswerForQuestion(questionText);

                if (answer != null) {
                    // Identify input type and provide an answer
                    if (isTextBox(question)) {
                        WebElement inputField = question.findElement(By.tagName("input"));
                        inputField.clear();
                        inputField.sendKeys(answer);
                    } else if (isRadioButton(question)) {
                        WebElement radioButton = findRadioButtonWithValue(question, answer);
                        if (radioButton != null) {
                            radioButton.click();
                        }
                    } else if (isDropdown(question)) {
                        if(questionText.contains("experience") && answer != null){
                            answer = "yes";
                        }
                        WebElement dropdown = question.findElement(By.tagName("select"));
                        selectDropdownOption(dropdown, answer);
                    }
                } else {
                    logger.warn("No answer provided for question: " + questionText);
                }
            }
        } catch (NoSuchElementException e) {
            logger.warn("Question handling encountered an issue: " + e.getMessage());
        }
    }

    private String getAnswerForQuestion(String questionText) {
        QuestionSet questionSet = new QuestionSet();
        questionSet.addAnswer("experience", "5");
        questionSet.addAnswer("work authorization", "yes");
        questionSet.addAnswer("commute", "no");
        questionSet.addAnswer("near", "yes");
        Map<String, String> userAnswers = questionSet.getUserAnswers();
        for (String key : userAnswers.keySet()) {
            if (questionText.contains(key)) {

                return userAnswers.get(key); // Return the corresponding answer
            }
        }
        return null; // Return null if no answer is found
    }

    private boolean isTextBox(WebElement question) {
        return question.findElements(By.tagName("input")).size() > 0;
    }

    private boolean isRadioButton(WebElement question) {
        return question.findElements(By.xpath(".//input[@type='radio']")).size() > 0;
    }

    private boolean isDropdown(WebElement question) {
        return question.findElements(By.tagName("select")).size() > 0;
    }

    private WebElement findRadioButtonWithValue(WebElement question, String value) {
        List<WebElement> radioButtons = question.findElements(By.xpath(".//input[@type='radio']"));
        for (WebElement radioButton : radioButtons) {
            if (radioButton.getAttribute("value").equalsIgnoreCase(value)) {
                return radioButton;
            }
        }
        return null;
    }

    private void selectDropdownOption(WebElement dropdown, String value) {
        List<WebElement> options = dropdown.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(value)) {
                option.click();
                break;
            }
        }
    }

}
