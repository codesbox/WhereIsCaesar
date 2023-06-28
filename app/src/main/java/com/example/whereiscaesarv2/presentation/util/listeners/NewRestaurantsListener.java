package com.example.whereiscaesarv2.presentation.util.listeners;

import com.example.domain.models.NewRestaurantModel;

public interface NewRestaurantsListener {

    void reject(NewRestaurantModel newRestaurantModel);
    void approve(NewRestaurantModel newRestaurantModel);
}
