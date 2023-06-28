package com.example.domain.useCases;

import com.example.domain.listeners.DeletePointListener;
import com.example.domain.models.RestaurantPointModel;
import com.example.domain.repository.MyRestaurantRepository;

public class DeletePointUseCase {

    MyRestaurantRepository repository;

    public DeletePointUseCase(MyRestaurantRepository repository) {
        this.repository = repository;
    }

    public void execute(RestaurantPointModel restaurantPointModel, DeletePointListener listener){
        repository.deletePoint(restaurantPointModel, listener);
    }
}
