package com.example.whereiscaesarv2.presentation.util.listeners;

import com.example.domain.models.NewFeedbackModel;
import com.example.domain.models.NewRestaurantModel;

public interface NewFeedbacksListener {

    void reject(NewFeedbackModel newFeedbackModel);
    void approve(NewFeedbackModel newFeedbackModel);
}
