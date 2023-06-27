package com.example.domain.listeners;

import com.example.domain.models.MyFeedback;
import com.example.domain.models.RestaurantModelDomain;

import java.util.List;

public interface GetMyFeedbacksListener {
    void onSuccess(List<MyFeedback> myFeedbackList);
    void onFailure();
}
