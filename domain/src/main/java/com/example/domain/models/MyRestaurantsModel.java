package com.example.domain.models;

public class MyRestaurantsModel {
    public String restaurantName, restaurantId, status;

    public MyRestaurantsModel(String restaurantName, String restaurantId, String status) {
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
        this.status = status;
    }
}
