package com.example.domain.models;

public class NewFeedbackModel {

    public String dishName, feedbackText, firstName, lastName, restaurantId, restaurantName, userId, feedbackId;
    public Integer estimation, userLevel;

    public NewFeedbackModel(String dishName, String feedbackText, String firstName, String lastName, String restaurantId, String restaurantName, String userId, String feedbackId, Integer estimation, Integer userLevel) {
        this.dishName = dishName;
        this.feedbackText = feedbackText;
        this.firstName = firstName;
        this.lastName = lastName;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.userId = userId;
        this.feedbackId = feedbackId;
        this.estimation = estimation;
        this.userLevel = userLevel;
    }
}
