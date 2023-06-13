package com.example.data.storages.firebase;

import com.example.domain.listeners.GetEstimationsListener;

public interface EstimationsStorage {

    void getEstimations(GetEstimationsListener listener, String dishName, String restaurantName);
}
