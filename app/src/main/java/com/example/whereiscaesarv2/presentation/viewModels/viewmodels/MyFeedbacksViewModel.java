package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.domain.listeners.GetMyFeedbacksListener;
import com.example.domain.useCases.GetMyFeedbacksUseCase;

public class MyFeedbacksViewModel extends ViewModel {

    GetMyFeedbacksUseCase useCase;

    public MyFeedbacksViewModel(GetMyFeedbacksUseCase useCase){
        this.useCase = useCase;

    }
    public void getMyFeedbacks(String userId, GetMyFeedbacksListener listener){
        useCase.execute(userId, listener);
    }

}
