package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.domain.listeners.GetMyRestaurantsListener;
import com.example.domain.useCases.GetMyRestaurantsUseCase;

public class MyRestaurantsViewModel extends ViewModel {

    GetMyRestaurantsUseCase useCase;

    public MyRestaurantsViewModel(GetMyRestaurantsUseCase useCase){
        this.useCase = useCase;
    }

    public void getRestaurants(String userId, GetMyRestaurantsListener listener){
        useCase.execute(userId, listener);
    }
}
