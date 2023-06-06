package com.example.domain.repository;

import com.example.domain.listeners.DishesListener;

public interface DishesRepository {

    void GetDishes(String tag, DishesListener listener);

}
