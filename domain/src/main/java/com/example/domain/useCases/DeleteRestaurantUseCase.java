package com.example.domain.useCases;

import com.example.domain.listeners.DeleteRestaurantListener;
import com.example.domain.repository.MyRestaurantRepository;

public class DeleteRestaurantUseCase {

    MyRestaurantRepository repository;

    public DeleteRestaurantUseCase(MyRestaurantRepository repository){
        this.repository = repository;
    }

    public void execute(String restaurantId, DeleteRestaurantListener listener){
        repository.deleteRestaurant(restaurantId, listener);
    }
}
