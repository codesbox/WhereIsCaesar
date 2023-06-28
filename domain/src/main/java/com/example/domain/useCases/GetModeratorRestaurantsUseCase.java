package com.example.domain.useCases;

import com.example.domain.listeners.GetModeratorRestaurantsListener;
import com.example.domain.repository.ModeratorRepository;

public class GetModeratorRestaurantsUseCase {

    ModeratorRepository repository;

    public GetModeratorRestaurantsUseCase(ModeratorRepository repository) {
        this.repository = repository;
    }

    public void execute(GetModeratorRestaurantsListener listener){
        repository.getRestaurants(listener);
    }
}
