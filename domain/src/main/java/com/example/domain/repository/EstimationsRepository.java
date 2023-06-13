package com.example.domain.repository;

import com.example.domain.listeners.GetEstimationsListener;

public interface EstimationsRepository {

    void getEstimations(GetEstimationsListener listener, String dishName, String restaurantName);
}
