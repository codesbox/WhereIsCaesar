package com.example.domain.models;

public class NewRestaurantModel {
    public String restaurantName, restaurantId, name, lastName;

    public NewRestaurantModel(String restaurantName, String restaurantId, String name, String lastName) {
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
        this.name = name;
        this.lastName = lastName;
    }
}
