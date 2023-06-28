package com.example.domain.repository;

import com.example.domain.listeners.GetModeratorFeedbacksListener;
import com.example.domain.listeners.GetModeratorRestaurantsListener;
import com.example.domain.models.NewFeedbackModel;

public interface ModeratorRepository {

    void getRestaurants(GetModeratorRestaurantsListener listener);
    void rejectRestaurant(String restaurantId);
    void approveRestaurant(String restaurantId);
    void getFeedbacks(GetModeratorFeedbacksListener listener);
    void rejectFeedback(String FeedbackId);
    void approveFeedback(NewFeedbackModel newFeedbackModel);
}
