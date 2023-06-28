package com.example.data.storages.firebase;

import com.example.domain.listeners.DeleteRestaurantListener;

public interface DeleteRestaurantStorage {

    void deleteRestaurant(String restaurantId, DeleteRestaurantListener listener);
}
