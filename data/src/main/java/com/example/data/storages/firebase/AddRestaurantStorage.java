package com.example.data.storages.firebase;

import com.example.domain.listeners.AddRestaurantListener;

public interface AddRestaurantStorage {
    void addRestaurant(Double latitude, Double longitude, String restaurantName, String id, AddRestaurantListener listener);
}
