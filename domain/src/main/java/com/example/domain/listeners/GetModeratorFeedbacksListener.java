package com.example.domain.listeners;

import com.example.domain.models.NewFeedbackModel;

import java.util.List;

public interface GetModeratorFeedbacksListener {

    void onSuccess(List<NewFeedbackModel> newFeedbackModels);
    void onFailure();
}
