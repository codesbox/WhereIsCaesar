package com.example.data.repositories;

import com.example.data.storages.firebase.GetMyRestaurantsStorage;
import com.example.domain.listeners.GetMyRestaurantsListener;
import com.example.domain.repository.MyRestaurantsRepository;

public class MyRestaurantsRepositoryImpl implements MyRestaurantsRepository {

    GetMyRestaurantsStorage getMyRestaurantsStorage;

    public MyRestaurantsRepositoryImpl(GetMyRestaurantsStorage getMyRestaurantsStorage){
        this.getMyRestaurantsStorage = getMyRestaurantsStorage;
    }

    @Override
    public void getMyRestaurants(String userId, GetMyRestaurantsListener listener) {
        getMyRestaurantsStorage.getMyRestaurants(userId, listener);
    }
}
