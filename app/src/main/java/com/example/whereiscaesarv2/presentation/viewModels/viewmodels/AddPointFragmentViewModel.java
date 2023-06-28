package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.domain.listeners.AddPointListener;
import com.example.domain.useCases.AddPointUseCase;

public class AddPointFragmentViewModel extends ViewModel {

    AddPointUseCase addPointUseCase;

    public AddPointFragmentViewModel(AddPointUseCase addPointUseCase){
        this.addPointUseCase = addPointUseCase;
    }

    public void addPoint(Double latitude, Double longitude, String address, String restaurantId, AddPointListener listener){
        addPointUseCase.execute(latitude, longitude, address, restaurantId, listener);
    }
}
