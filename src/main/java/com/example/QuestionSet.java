package com.example;

import java.util.HashMap;
import java.util.Map;

public class QuestionSet {
    private Map<String, String> userAnswers;

    // Constructor to initialize the map
    public QuestionSet() {
        userAnswers = new HashMap<>();
    }

    // Method to add a question-answer pair
    public void addAnswer(String questionKey, String answerValue) {
        userAnswers.put(questionKey, answerValue);
    }

    // Getter for userAnswers
    public Map<String, String> getUserAnswers() {
        return userAnswers;
    }
}
