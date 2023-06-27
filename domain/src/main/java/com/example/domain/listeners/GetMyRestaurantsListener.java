package com.example.domain.listeners;

import com.example.domain.models.RestaurantModelDomain;

import java.util.List;

public interface GetMyRestaurantsListener {

    void onSuccess(List<String> restaurantsList);
    void onFailure();

}
