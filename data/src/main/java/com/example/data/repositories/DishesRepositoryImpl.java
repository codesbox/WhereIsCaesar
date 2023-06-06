package com.example.data.repositories;

import com.example.data.storages.firebase.DishesStorage;
import com.example.domain.listeners.DishesListener;
import com.example.domain.repository.DishesRepository;

public class DishesRepositoryImpl implements DishesRepository {
    private final DishesStorage dishesStorage;

    public DishesRepositoryImpl(DishesStorage dishesStorage){
        this.dishesStorage = dishesStorage;
    }

    @Override
    public void GetDishes(String tag, DishesListener listener) {
        dishesStorage.getDishes(tag, listener);
    }

}
