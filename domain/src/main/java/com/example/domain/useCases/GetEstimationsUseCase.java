package com.example.domain.useCases;

import com.example.domain.listeners.GetEstimationsListener;
import com.example.domain.repository.EstimationsRepository;

public class GetEstimationsUseCase {

    GetEstimationsListener listener;
    EstimationsRepository repository;

    public GetEstimationsUseCase(GetEstimationsListener listener, EstimationsRepository repository){
        this.listener = listener;
        this.repository = repository;
    }

    public void execute(String restaurantName, String dishName){
        repository.getEstimations(listener, dishName, restaurantName);
    }
}
