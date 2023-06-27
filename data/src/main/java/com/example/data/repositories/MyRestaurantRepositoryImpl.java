package com.example.data.repositories;

import com.example.data.storages.firebase.AddDishStorage;
import com.example.data.storages.firebase.AddRestaurantStorage;
import com.example.data.storages.firebase.DeleteDishStorage;
import com.example.data.storages.firebase.GetMyRestaurantStorage;
import com.example.domain.listeners.AddDishListener;
import com.example.domain.listeners.AddRestaurantListener;
import com.example.domain.listeners.DeleteDishListener;
import com.example.domain.listeners.GetMyRestaurantListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.repository.MyRestaurantRepository;

public class MyRestaurantRepositoryImpl implements MyRestaurantRepository {

    AddDishStorage addDishStorage;
    DeleteDishStorage deleteDishStorage;
    GetMyRestaurantStorage getMyRestaurantStorage;
    AddRestaurantStorage addRestaurantStorage;

    public MyRestaurantRepositoryImpl(AddDishStorage addDishStorage, DeleteDishStorage deleteDishStorage, GetMyRestaurantStorage getMyRestaurantStorage, AddRestaurantStorage addRestaurantStorage){
        this.addDishStorage = addDishStorage;
        this.deleteDishStorage = deleteDishStorage;
        this.getMyRestaurantStorage = getMyRestaurantStorage;
        this.addRestaurantStorage = addRestaurantStorage;

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

    @Override
    public void addRestaurant(Double latitude, Double longitude, String restaurantName, String id, AddRestaurantListener listener) {
        addRestaurantStorage.addRestaurant(latitude, longitude, restaurantName, id, listener);
    }
}
