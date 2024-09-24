package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class QuestionLoader {

    // Step 2: Declare a static instance (initially null)
    private static Questions instance;

    // Step 1: Private constructor to prevent instantiation from outside
    private QuestionLoader() {
        // Prevent instantiation
    }

    // Step 3: Public static method to provide access to the single instance
    public static Questions getInstance(String filePath) throws IOException {
        if (instance == null) {
            synchronized (QuestionLoader.class) { // Double-checked locking
                if (instance == null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    instance = objectMapper.readValue(new File(filePath), Questions.class);
                }
            }
        }
        return instance;
    }

    public static String getAnswerForQuestion(String questionText) throws IOException {
        Questions questions = getInstance("questions.json");
        for (Map.Entry<String, String> entry : questions.getQuestions().entrySet()) {
            if (questionText.toLowerCase().contains(entry.getKey().toLowerCase())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
