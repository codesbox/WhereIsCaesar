package com.example.domain.models;

public class MyFeedback {
    public String restaurantName;
    public String dishName;
    public String feedback;
    public Integer estimation;

    public MyFeedback(String restaurantName, String dishName, String feedback, Integer estimation) {
        this.restaurantName = restaurantName;
        this.dishName = dishName;
        this.feedback = feedback;
        this.estimation = estimation;
    }
}
