package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.domain.listeners.DeleteDishListener;
import com.example.domain.listeners.GetMyRestaurantListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.useCases.DeleteDishUseCase;
import com.example.domain.useCases.GetMyRestaurantUseCase;

public class MyRestaurantCardFragmentViewModel extends ViewModel {

    GetMyRestaurantUseCase useCase;
    DeleteDishUseCase deleteDishUseCase;

    public MyRestaurantCardFragmentViewModel(GetMyRestaurantUseCase useCase, DeleteDishUseCase deleteDishUseCase){
        this.useCase = useCase;
        this.deleteDishUseCase = deleteDishUseCase;
    }

    public void getRestaurant(String restaurantName, GetMyRestaurantListener listener){
        useCase.execute(restaurantName, listener);
    }

    public void deleteDish(DishModelDomain dishModelDomain, String restaurantName, DeleteDishListener listener){
        deleteDishUseCase.execute(dishModelDomain, restaurantName, listener);
    }
}
