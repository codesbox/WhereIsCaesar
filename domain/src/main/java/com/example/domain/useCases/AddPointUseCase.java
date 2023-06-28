package com.example.domain.useCases;

import com.example.domain.listeners.AddPointListener;
import com.example.domain.repository.MyRestaurantRepository;

public class AddPointUseCase {
    MyRestaurantRepository repository;

    public AddPointUseCase(MyRestaurantRepository repository) {
        this.repository = repository;
    }

    public void execute(Double latitude, Double longitude, String address, String restaurantId, AddPointListener listener){
        repository.addPoint(latitude, longitude, address, restaurantId, listener);
    }
}
