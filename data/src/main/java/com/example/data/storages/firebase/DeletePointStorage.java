package com.example.data.storages.firebase;

import com.example.domain.listeners.DeletePointListener;
import com.example.domain.models.RestaurantPointModel;

public interface DeletePointStorage {
    void deletePoint(RestaurantPointModel restaurantPointModel, DeletePointListener listener);
}
