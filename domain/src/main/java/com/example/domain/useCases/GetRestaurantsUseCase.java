package com.example.domain.useCases;

import android.graphics.Point;

import com.example.domain.listeners.GetRestaurantsListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.models.PointModel;
import com.example.domain.repository.RestaurantsRepository;

import java.util.List;

public class GetRestaurantsUseCase {

    RestaurantsRepository repository;

    GetRestaurantsListener listener;

    public GetRestaurantsUseCase(RestaurantsRepository repository, GetRestaurantsListener listener) {
        this.repository = repository;
        this.listener = listener;
    }

    public void execute(List<String> dishList, PointModel topLeftPoint, PointModel bottomRightPoint){
        repository.getRestaurants(listener, dishList, topLeftPoint, bottomRightPoint);
    }
}
