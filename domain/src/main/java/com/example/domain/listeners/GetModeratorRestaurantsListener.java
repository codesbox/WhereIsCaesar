package com.example.domain.listeners;

import com.example.domain.models.NewRestaurantModel;

import java.util.List;

public interface GetModeratorRestaurantsListener {

    void onSuccess(List<NewRestaurantModel> newRestaurantModelList);
    void onFailure();

}
