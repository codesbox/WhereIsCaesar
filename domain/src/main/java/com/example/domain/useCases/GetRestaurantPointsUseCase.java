package com.example.domain.useCases;

import com.example.domain.listeners.GetPointsListener;
import com.example.domain.repository.MyRestaurantRepository;

public class GetRestaurantPointsUseCase {

    MyRestaurantRepository repository;
    public GetRestaurantPointsUseCase(MyRestaurantRepository repository){
        this.repository = repository;
    }
    public void execute(String restaurantId, GetPointsListener getPointsListener){
        repository.getRestaurantPoints(restaurantId, getPointsListener);
    }
}
