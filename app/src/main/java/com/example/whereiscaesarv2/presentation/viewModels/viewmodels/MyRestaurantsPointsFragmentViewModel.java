package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.domain.listeners.DeletePointListener;
import com.example.domain.listeners.GetPointsListener;
import com.example.domain.models.RestaurantPointModel;
import com.example.domain.useCases.DeletePointUseCase;
import com.example.domain.useCases.GetRestaurantPointsUseCase;

public class MyRestaurantsPointsFragmentViewModel extends ViewModel {

    GetRestaurantPointsUseCase getRestaurantPointsUseCase;
    DeletePointUseCase deletePointUseCase;

    public MyRestaurantsPointsFragmentViewModel(GetRestaurantPointsUseCase getRestaurantPointsUseCase, DeletePointUseCase deletePointUseCase){
        this.getRestaurantPointsUseCase = getRestaurantPointsUseCase;
        this.deletePointUseCase = deletePointUseCase;
    }

    public void getPoints(String restaurantId, GetPointsListener getPointsListener){
        getRestaurantPointsUseCase.execute(restaurantId, getPointsListener);
    }

    public void deletePoint(RestaurantPointModel restaurantPointModel, DeletePointListener listener){
        deletePointUseCase.execute(restaurantPointModel, listener);

    }
}
