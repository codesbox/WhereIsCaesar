package com.example.domain.useCases;

import com.example.domain.listeners.AddRestaurantListener;
import com.example.domain.repository.MyRestaurantRepository;

public class AddRestaurantUseCase {

    MyRestaurantRepository repository;

    public AddRestaurantUseCase(MyRestaurantRepository repository){
        this.repository = repository;
    }

    public void execute(Double latitude, Double longitude, String restaurantName, String id, AddRestaurantListener listener){
        repository.addRestaurant(latitude, longitude, restaurantName, id, listener);
    }
}
