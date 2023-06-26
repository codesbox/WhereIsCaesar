package com.example.data.storages.firebase;

import com.example.domain.listeners.DeleteDishListener;
import com.example.domain.models.DishModelDomain;

public interface DeleteDishStorage {

    void deleteDish(DishModelDomain dishModelDomain, String restaurantName, DeleteDishListener listener);
}
