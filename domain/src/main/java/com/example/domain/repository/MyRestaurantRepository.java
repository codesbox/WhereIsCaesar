package com.example.domain.repository;

import com.example.domain.listeners.AddDishListener;
import com.example.domain.listeners.AddPointListener;
import com.example.domain.listeners.AddRestaurantListener;
import com.example.domain.listeners.DeleteDishListener;
import com.example.domain.listeners.DeletePointListener;
import com.example.domain.listeners.DeleteRestaurantListener;
import com.example.domain.listeners.GetMyRestaurantListener;
import com.example.domain.listeners.GetPointsListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.models.RestaurantPointModel;

public interface MyRestaurantRepository {

    void addDish(DishModelDomain dishModelDomain, String restaurantName, AddDishListener listener);
    void deleteDish(DishModelDomain dishModelDomain, String restaurantName, DeleteDishListener listener);
    void getMyRestaurant(String restaurantName, GetMyRestaurantListener listener);
    void addRestaurant(Double latitude, Double longitude, String restaurantName, String id, AddRestaurantListener listener, String address);
    void deleteRestaurant(String restaurantId, DeleteRestaurantListener listener);
    void getRestaurantPoints(String restaurantId, GetPointsListener getPointsListener);
    void deletePoint(RestaurantPointModel restaurantPointModel, DeletePointListener listener);
    void addPoint(Double latitude, Double longitude, String address, String restaurantId, AddPointListener listener);
}
