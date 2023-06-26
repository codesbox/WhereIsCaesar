package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.domain.listeners.AddDishListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.useCases.AddDishUseCase;

public class AddDishFragmentViewModel extends ViewModel {

    AddDishUseCase useCase;
    public AddDishFragmentViewModel(AddDishUseCase useCase){
        this.useCase = useCase;
    }

    public void addDish(DishModelDomain dishModelDomain, String restaurantName, AddDishListener listener){
        useCase.execute(dishModelDomain, restaurantName, listener);
    }
}
