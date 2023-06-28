package com.example.data.repositories;

import com.example.data.storages.firebase.AddDishStorage;
import com.example.data.storages.firebase.AddPointStorage;
import com.example.data.storages.firebase.AddPointStorageImpl;
import com.example.data.storages.firebase.AddRestaurantStorage;
import com.example.data.storages.firebase.DeleteDishStorage;
import com.example.data.storages.firebase.DeletePointStorage;
import com.example.data.storages.firebase.DeleteRestaurantStorage;
import com.example.data.storages.firebase.GetMyRestaurantStorage;
import com.example.data.storages.firebase.GetRestaurantPoints;
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
import com.example.domain.repository.MyRestaurantRepository;

public class MyRestaurantRepositoryImpl implements MyRestaurantRepository {

    AddDishStorage addDishStorage;
    DeleteDishStorage deleteDishStorage;
    GetMyRestaurantStorage getMyRestaurantStorage;
    AddRestaurantStorage addRestaurantStorage;
    DeleteRestaurantStorage deleteRestaurantStorage;
    GetRestaurantPoints getRestaurantPoints;
    DeletePointStorage deletePointStorage;
    AddPointStorage addPointStorage;

    public MyRestaurantRepositoryImpl(AddDishStorage addDishStorage, DeleteDishStorage deleteDishStorage, GetMyRestaurantStorage getMyRestaurantStorage, AddRestaurantStorage addRestaurantStorage, DeleteRestaurantStorage deleteRestaurantStorage, GetRestaurantPoints getRestaurantPoints, DeletePointStorage deletePointStorage, AddPointStorage addPointStorage){
        this.addDishStorage = addDishStorage;
        this.deleteDishStorage = deleteDishStorage;
        this.getMyRestaurantStorage = getMyRestaurantStorage;
        this.addRestaurantStorage = addRestaurantStorage;
        this.deleteRestaurantStorage = deleteRestaurantStorage;
        this.getRestaurantPoints = getRestaurantPoints;
        this.deletePointStorage = deletePointStorage;
        this.addPointStorage = addPointStorage;

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
    public void addRestaurant(Double latitude, Double longitude, String restaurantName, String id, AddRestaurantListener listener, String address) {
        addRestaurantStorage.addRestaurant(latitude, longitude, restaurantName, id, listener, address);
    }

    @Override
    public void deleteRestaurant(String restaurantId, DeleteRestaurantListener listener) {
        deleteRestaurantStorage.deleteRestaurant(restaurantId, listener);
    }

    @Override
    public void getRestaurantPoints(String restaurantId, GetPointsListener getPointsListener) {
        getRestaurantPoints.getPoints(restaurantId, getPointsListener);
    }

    @Override
    public void deletePoint(RestaurantPointModel restaurantPointModel, DeletePointListener listener) {
        deletePointStorage.deletePoint(restaurantPointModel, listener);
    }

    @Override
    public void addPoint(Double latitude, Double longitude, String address, String restaurantId, AddPointListener listener) {
        addPointStorage.AddPoint(latitude, longitude, address, restaurantId, listener);
    }
}
