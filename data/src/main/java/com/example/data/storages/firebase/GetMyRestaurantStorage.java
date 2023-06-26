package com.example.data.storages.firebase;

import com.example.domain.listeners.GetMyRestaurantListener;

public interface GetMyRestaurantStorage {

    void GetMyRestaurant(String restaurantName, GetMyRestaurantListener listener);
}
