package com.example.domain.useCases;

import com.example.domain.listeners.AddDishListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.repository.MyRestaurantRepository;

public class AddDishUseCase {

    MyRestaurantRepository repository;

    public AddDishUseCase(MyRestaurantRepository repository){
        this.repository = repository;
    }

    public void execute(DishModelDomain dishModelDomain, String restaurantName, AddDishListener listener){
        repository.addDish(dishModelDomain, restaurantName, listener);

    }
}
