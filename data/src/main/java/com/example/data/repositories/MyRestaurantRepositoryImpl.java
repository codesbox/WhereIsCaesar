package com.example.data.repositories;

import com.example.data.storages.firebase.AddDishStorage;
import com.example.data.storages.firebase.DeleteDishStorage;
import com.example.data.storages.firebase.GetMyRestaurantStorage;
import com.example.domain.listeners.AddDishListener;
import com.example.domain.listeners.DeleteDishListener;
import com.example.domain.listeners.GetMyRestaurantListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.repository.MyRestaurantRepository;

public class MyRestaurantRepositoryImpl implements MyRestaurantRepository {

    AddDishStorage addDishStorage;
    DeleteDishStorage deleteDishStorage;
    GetMyRestaurantStorage getMyRestaurantStorage;

    public MyRestaurantRepositoryImpl(AddDishStorage addDishStorage, DeleteDishStorage deleteDishStorage, GetMyRestaurantStorage getMyRestaurantStorage){
        this.addDishStorage = addDishStorage;
        this.deleteDishStorage = deleteDishStorage;
        this.getMyRestaurantStorage = getMyRestaurantStorage;

    }


    @Override
    public void addDish(DishModelDomain dishModelDomain, String restaurantName, AddDishListener listener) {
        addDishStorage.addDish(dishModelDomain, restaurantName, listener);
    }

    @Override
    public void deleteDish(DishModelDomain dishModelDomain, String restaurantName, DeleteDishListener listener) {
        deleteDishStorage.deleteDish(dishModelDomain, restaurantName, listener);
    }

    @Override
    public void getMyRestaurant(String restaurantName, GetMyRestaurantListener listener) {
        getMyRestaurantStorage.GetMyRestaurant(restaurantName, listener);
    }
}
