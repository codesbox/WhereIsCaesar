package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.domain.listeners.AddRestaurantListener;
import com.example.domain.useCases.AddRestaurantUseCase;

public class AddRestaurantViewModel extends ViewModel {

    AddRestaurantUseCase useCase;

    public AddRestaurantViewModel(AddRestaurantUseCase useCase){
        this.useCase = useCase;
    }

    public void addRestaurant(Double latitude, Double longitude, String restaurantName, String id, AddRestaurantListener listener){
        useCase.execute(latitude, longitude, restaurantName, id, listener);
    }

}
