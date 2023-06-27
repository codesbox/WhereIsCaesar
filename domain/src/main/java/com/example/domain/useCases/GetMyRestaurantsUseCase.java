package com.example.domain.useCases;

import com.example.domain.listeners.GetMyRestaurantsListener;
import com.example.domain.repository.MyRestaurantsRepository;

public class GetMyRestaurantsUseCase {

    MyRestaurantsRepository repository;

    public GetMyRestaurantsUseCase(MyRestaurantsRepository repository){

        this.repository = repository;

    }

    public void execute(String userId, GetMyRestaurantsListener listener){
        repository.getMyRestaurants(userId, listener);

    }
}
