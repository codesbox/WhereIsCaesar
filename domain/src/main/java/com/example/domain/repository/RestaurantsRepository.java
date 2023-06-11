package com.example.domain.repository;

import com.example.domain.listeners.GetRestaurantsListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.models.PointModel;

import java.util.List;

public interface RestaurantsRepository {

    void getRestaurants(GetRestaurantsListener listener, List<String> dishList, PointModel topLeftPoint, PointModel bottomRightPoint);
}
