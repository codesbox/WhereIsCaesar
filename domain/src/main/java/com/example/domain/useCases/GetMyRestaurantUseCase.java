package com.example.domain.useCases;

import com.example.domain.listeners.GetMyRestaurantListener;
import com.example.domain.repository.MyRestaurantRepository;

public class GetMyRestaurantUseCase {

    MyRestaurantRepository repository;

    public GetMyRestaurantUseCase(MyRestaurantRepository repository){
        this.repository = repository;
    }
    public void execute(String restaurantName, GetMyRestaurantListener listener){
        repository.getMyRestaurant(restaurantName, listener);
    }
}
