package com.example.data.storages.firebase;

import com.example.domain.listeners.GetPointsListener;

public interface GetRestaurantPoints {

    void getPoints(String restaurantId, GetPointsListener getPointsListener);
}
