# 🚀 AI-Powered LinkedIn Job Apply Bot

## 📌 Description

The **AI-Powered LinkedIn Job Apply Bot** is an automation tool designed to streamline the **Easy Apply** job application process on LinkedIn. Built using **Java with Selenium and Spring Boot**, this bot intelligently navigates job postings, applies filters, answers job-related questions, and submits applications automatically. 

To enhance efficiency, the bot integrates with **OpenAI's ChatGPT API** to generate responses for application questions dynamically, making job applications smarter and faster.

---

## 🔥 Features

✅ **Automated Job Applications** – Uses Selenium WebDriver to navigate and apply for jobs on LinkedIn’s Easy Apply.  
✅ **Smart Filtering** – Configure job search filters (location, experience, job type, etc.) via a JSON-based configuration.  
✅ **AI-Powered Answers** – Uses OpenAI’s ChatGPT to generate intelligent responses for application questions.  
✅ **Progress Tracking** – Monitors the application progress using LinkedIn’s progress bar to prevent getting stuck.  
✅ **Error Handling & Retry Logic** – Detects missing required fields and handles incomplete applications gracefully.  
✅ **Rate Limiting & Efficiency Optimization** – Prevents excessive API usage and ensures compliance with LinkedIn’s limits.  

---

## 🛠️ Tech Stack

- **Backend:** Java, Spring Boot  
- **Automation Framework:** Selenium WebDriver  
- **AI Integration:** OpenAI ChatGPT API  
- **Data Handling:** JSON-based configuration for filters and responses  
- **Logging & Monitoring:** SLF4J, Logback  

---

## ⚡ Installation & Setup

### **1️⃣ Prerequisites**
- **Java 17** or later
- **Maven**
- **Google Chrome** & **Chromedriver** (Ensure the correct version is installed)
- **LinkedIn Account**

### **2️⃣ Clone the Repository**
```sh
git clone https://github.com/degencoderr/AI-LinkedInJob-Apply-Bot.git
cd AI-LinkedInJob-Apply-Bot
```

### **3️⃣ Install Dependencies**
```sh
mvn clean install
```

### **4️⃣ Configure API Keys & Filters**
- **ChatGPT API Key**: Obtain an OpenAI API key and update `application.properties`
- **Filters**: Modify `filters.json` to set job search preferences
- **Questions & Answers**: Update `questions.json` to define responses

### **5️⃣ Run the Bot**
```sh
mvn spring-boot:run
```

---

## 🎯 How It Works

1. The bot logs into LinkedIn and navigates to job listings based on configured filters.
2. It applies to jobs automatically using **Easy Apply**.
3. The bot answers job-related questions using pre-configured JSON responses or **ChatGPT**.
4. It monitors the application progress using the **progress bar** to detect stuck pages.
5. If required questions are missing, it retries intelligently or skips problematic applications.

---

## 🚀 Future Improvements

- Implement **AI-powered resume matching** to prioritize job applications.
- Add **multi-threading** to increase application speed.
- Enhance **error handling** for more robust automation.

---

## 📜 License

This project is open-source under the **MIT License**.

---

## 📩 Contact

For any issues, feel free to open an **issue** or **pull request** in the repository.  

💡 Happy Job Hunting! 🚀
