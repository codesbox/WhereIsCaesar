package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.domain.listeners.GetModeratorFeedbacksListener;
import com.example.domain.listeners.GetModeratorRestaurantsListener;
import com.example.domain.models.NewFeedbackModel;
import com.example.domain.useCases.ApproveFeedbackUseCase;
import com.example.domain.useCases.GetModeratorFeedbacksUseCase;
import com.example.domain.useCases.RejectFeedbackUseCase;

public class NewFeedbacksViewModel extends ViewModel {

    GetModeratorFeedbacksUseCase getModeratorFeedbacksUseCase;
    RejectFeedbackUseCase rejectFeedbackUseCase;
    ApproveFeedbackUseCase approveFeedbackUseCase;

    public NewFeedbacksViewModel(GetModeratorFeedbacksUseCase getModeratorFeedbacksUseCase, RejectFeedbackUseCase rejectFeedbackUseCase, ApproveFeedbackUseCase approveFeedbackUseCase) {
        this.getModeratorFeedbacksUseCase = getModeratorFeedbacksUseCase;
        this.rejectFeedbackUseCase = rejectFeedbackUseCase;
        this.approveFeedbackUseCase = approveFeedbackUseCase;
    }

    public void getFeedbacks(GetModeratorFeedbacksListener listener){
        getModeratorFeedbacksUseCase.execute(listener);
    }

    public void reject(String feedbackId){
        rejectFeedbackUseCase.execute(feedbackId);
    }
    public void approve(NewFeedbackModel newFeedbackModel){
        approveFeedbackUseCase.execute(newFeedbackModel);
    }

}
