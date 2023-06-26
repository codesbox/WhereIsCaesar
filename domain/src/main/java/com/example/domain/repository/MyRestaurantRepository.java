package com.example.domain.repository;

import com.example.domain.listeners.AddDishListener;
import com.example.domain.listeners.DeleteDishListener;
import com.example.domain.listeners.GetMyRestaurantListener;
import com.example.domain.models.DishModelDomain;

public interface MyRestaurantRepository {

    void addDish(DishModelDomain dishModelDomain, String restaurantName, AddDishListener listener);
    void deleteDish(DishModelDomain dishModelDomain, String restaurantName, DeleteDishListener listener);
    void getMyRestaurant(String restaurantName, GetMyRestaurantListener listener);
}
