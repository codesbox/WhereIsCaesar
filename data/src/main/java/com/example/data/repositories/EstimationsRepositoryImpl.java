package com.example.data.repositories;

import com.example.data.storages.firebase.EstimationsStorage;
import com.example.data.storages.firebase.EstimationsStorageImpl;
import com.example.domain.listeners.GetEstimationsListener;
import com.example.domain.repository.EstimationsRepository;

public class EstimationsRepositoryImpl implements EstimationsRepository {

    EstimationsStorage storage;

    public EstimationsRepositoryImpl(EstimationsStorage storage){
        this.storage = storage;
    }
    @Override
    public void getEstimations(GetEstimationsListener listener, String dishName, String restaurantName) {
        storage.getEstimations(listener, dishName, restaurantName);
    }
}
