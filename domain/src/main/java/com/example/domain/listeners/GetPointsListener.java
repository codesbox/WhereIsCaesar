package com.example.domain.listeners;

import com.example.domain.models.RestaurantPointModel;

import java.util.List;

public interface GetPointsListener {

    void onSuccess(List<RestaurantPointModel> restaurantPointModelList);
    void onFailure();
}
