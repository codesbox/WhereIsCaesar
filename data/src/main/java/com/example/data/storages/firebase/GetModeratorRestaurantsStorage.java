package com.example.data.storages.firebase;

import com.example.domain.listeners.GetModeratorRestaurantsListener;

public interface GetModeratorRestaurantsStorage {
    void getRestaurants(GetModeratorRestaurantsListener listener);
}
