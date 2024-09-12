package com.example;


import java.util.List;

public class JobFilter {
    private String datePosted;
    private List<String> experienceLevel;
    private List<String> jobType;
    private List<String> remote;
    private String position;
    private String location;

    // Getters and Setters
    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public List<String> getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(List<String> experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public List<String> getJobType() {
        return jobType;
    }

    public void setJobType(List<String> jobType) {
        this.jobType = jobType;
    }

    public List<String> getRemote() {
        return remote;
    }

    public void setRemote(List<String> remote) {
        this.remote = remote;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}

