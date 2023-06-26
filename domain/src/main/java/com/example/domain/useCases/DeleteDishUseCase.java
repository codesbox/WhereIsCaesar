package com.example.domain.useCases;

import com.example.domain.listeners.DeleteDishListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.repository.MyRestaurantRepository;

public class DeleteDishUseCase {

    MyRestaurantRepository repository;

    public DeleteDishUseCase(MyRestaurantRepository repository){
        this.repository = repository;
    }

    public void execute(DishModelDomain dishModelDomain, String restaurantName, DeleteDishListener listener){
        repository.deleteDish(dishModelDomain, restaurantName, listener);

    }
}
