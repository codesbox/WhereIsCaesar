package com.example.domain.listeners;

import com.example.domain.models.FeedbackModel;

import java.util.List;

public interface GetEstimationsListener {

    void getEstimations(List<FeedbackModel> feedbackModelList);
}
