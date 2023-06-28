package com.example.domain.useCases;

import com.example.domain.repository.ModeratorRepository;

public class ApproveRestaurantUseCase {

    ModeratorRepository repository;

    public ApproveRestaurantUseCase(ModeratorRepository repository) {
        this.repository = repository;
    }

    public void execute(String restaurantId){
        repository.approveRestaurant(restaurantId);
    }
}
