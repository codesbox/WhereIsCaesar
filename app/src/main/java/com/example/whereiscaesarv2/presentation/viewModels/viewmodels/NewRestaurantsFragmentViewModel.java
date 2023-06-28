package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.domain.listeners.GetModeratorRestaurantsListener;
import com.example.domain.useCases.ApproveRestaurantUseCase;
import com.example.domain.useCases.GetModeratorRestaurantsUseCase;
import com.example.domain.useCases.RejectRestaurantUseCase;

public class NewRestaurantsFragmentViewModel extends ViewModel {

    GetModeratorRestaurantsUseCase getModeratorRestaurantsUseCase;
    RejectRestaurantUseCase rejectRestaurantUseCase;
    ApproveRestaurantUseCase approveRestaurantUseCase;

    public NewRestaurantsFragmentViewModel(GetModeratorRestaurantsUseCase getModeratorRestaurantsUseCase, RejectRestaurantUseCase rejectRestaurantUseCase, ApproveRestaurantUseCase approveRestaurantUseCase) {
        this.getModeratorRestaurantsUseCase = getModeratorRestaurantsUseCase;
        this.rejectRestaurantUseCase = rejectRestaurantUseCase;
        this.approveRestaurantUseCase = approveRestaurantUseCase;
    }

    public void getRestaurants(GetModeratorRestaurantsListener listener){
        getModeratorRestaurantsUseCase.execute(listener);
    }

    public void reject(String restaurantId){
        rejectRestaurantUseCase.execute(restaurantId);
    }
    public void approve(String restaurantId){
        approveRestaurantUseCase.execute(restaurantId);
    }
}
