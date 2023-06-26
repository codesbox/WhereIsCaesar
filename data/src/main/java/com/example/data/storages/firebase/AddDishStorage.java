package com.example.data.storages.firebase;

import com.example.domain.listeners.AddDishListener;
import com.example.domain.models.DishModelDomain;

public interface AddDishStorage {

    void addDish(DishModelDomain dishModelDomain, String restaurantName, AddDishListener listener);
}
