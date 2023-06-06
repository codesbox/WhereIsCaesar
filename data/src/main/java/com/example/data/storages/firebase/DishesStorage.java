package com.example.data.storages.firebase;

import com.example.domain.listeners.DishesListener;

public interface DishesStorage {

    void getDishes(String tag, DishesListener listener);
}
