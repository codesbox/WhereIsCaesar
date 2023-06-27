package com.example.data.storages.firebase;

import com.example.domain.listeners.GetMyRestaurantsListener;

public interface GetMyRestaurantsStorage {
    void getMyRestaurants(String userId, GetMyRestaurantsListener listener);
}
