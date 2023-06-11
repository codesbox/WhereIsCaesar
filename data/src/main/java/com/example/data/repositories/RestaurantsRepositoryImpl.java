package com.example.data.repositories;

import com.example.data.storages.firebase.RestaurantsStorage;
import com.example.data.storages.firebase.RestaurantsStorageImpl;
import com.example.domain.listeners.GetRestaurantsListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.models.PointModel;
import com.example.domain.repository.RestaurantsRepository;

import java.util.List;

public class RestaurantsRepositoryImpl implements RestaurantsRepository {

    RestaurantsStorage restaurantsStorage;

    public RestaurantsRepositoryImpl(RestaurantsStorage restaurantsStorage){

        this.restaurantsStorage = restaurantsStorage;

    }


    @Override
    public void getRestaurants(GetRestaurantsListener listener, List<String> dishList, PointModel topLeftPoint, PointModel bottomRightPoint) {

        restaurantsStorage.getRestaurants(listener, dishList, topLeftPoint, bottomRightPoint);

    }

}
