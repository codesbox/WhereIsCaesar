package com.example.domain.models;

public class FeedbackModel {
    public String userName;
    public Integer estimation;
    public String feedback;
    public Integer level;

    public FeedbackModel(String userName, Integer estimation, String feedback, Integer level) {
        this.userName = userName;
        this.estimation = estimation;
        this.feedback = feedback;
        this.level = level;
    }
}