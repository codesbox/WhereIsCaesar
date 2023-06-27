package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.domain.listeners.AddFeedbackListener;
import com.example.domain.useCases.AddFeedbackUseCase;

public class AddFeedbackViewModel extends ViewModel {

    AddFeedbackUseCase useCase;

    public AddFeedbackViewModel(AddFeedbackUseCase useCase){

        this.useCase = useCase;

    }

    public void addFeedback(AddFeedbackListener listener, String id, String dishName, String feedback, String restaurantName, Integer estimation){
        useCase.execute(listener, id, dishName, feedback, restaurantName, estimation);
    }
}
