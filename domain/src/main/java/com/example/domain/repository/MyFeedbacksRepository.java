package com.example.domain.repository;

import com.example.domain.listeners.AddFeedbackListener;
import com.example.domain.listeners.GetMyFeedbacksListener;

public interface MyFeedbacksRepository {
    void getMyFeedbacks(String userId, GetMyFeedbacksListener listener);
    void addFeedback(AddFeedbackListener listener, String id, String dishName, String feedback, String restaurantName, Integer estimation, String restaurantId);
}
