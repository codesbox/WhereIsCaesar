package com.example.domain.listeners;


import com.example.domain.models.RestaurantModelDomain;

public interface GetMyRestaurantListener {

    void onSuccess(RestaurantModelDomain restaurantModelDomain);
    void onFailure();

}
