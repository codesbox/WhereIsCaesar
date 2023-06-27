package com.example.domain.repository;

import com.example.domain.listeners.GetMyRestaurantsListener;

public interface MyRestaurantsRepository {

    void getMyRestaurants(String userId, GetMyRestaurantsListener listener);
}
