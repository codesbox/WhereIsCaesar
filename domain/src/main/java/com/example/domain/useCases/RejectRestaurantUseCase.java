package com.example.domain.useCases;

import com.example.domain.repository.ModeratorRepository;

public class RejectRestaurantUseCase {

    ModeratorRepository repository;

    public RejectRestaurantUseCase(ModeratorRepository repository) {
        this.repository = repository;
    }

    public void execute(String restaurantId){
        repository.rejectRestaurant(restaurantId);
    }
}
