package com.example.data.repositories;

import com.example.data.storages.firebase.ApproveFeedbackStorage;
import com.example.data.storages.firebase.ApproveRestaurantStorage;
import com.example.data.storages.firebase.GetModeratorFeedbacksStorage;
import com.example.data.storages.firebase.GetModeratorRestaurantsStorage;
import com.example.data.storages.firebase.RejectFeedbackStorage;
import com.example.data.storages.firebase.RejectRestaurantStorage;
import com.example.domain.listeners.GetModeratorFeedbacksListener;
import com.example.domain.listeners.GetModeratorRestaurantsListener;
import com.example.domain.models.NewFeedbackModel;
import com.example.domain.repository.ModeratorRepository;

public class ModeratorRepositoryImpl implements ModeratorRepository {

    GetModeratorFeedbacksStorage getModeratorFeedbacksStorage;
    GetModeratorRestaurantsStorage getModeratorRestaurantsStorage;
    RejectRestaurantStorage rejectRestaurantStorage;
    RejectFeedbackStorage rejectFeedbackStorage;
    ApproveRestaurantStorage approveRestaurantStorage;
    ApproveFeedbackStorage approveFeedbackStorage;

    public ModeratorRepositoryImpl(GetModeratorFeedbacksStorage getModeratorFeedbacksStorage, GetModeratorRestaurantsStorage getModeratorRestaurantsStorage, RejectRestaurantStorage rejectRestaurantStorage, RejectFeedbackStorage rejectFeedbackStorage, ApproveRestaurantStorage approveRestaurantStorage, ApproveFeedbackStorage approveFeedbackStorage) {
        this.getModeratorFeedbacksStorage = getModeratorFeedbacksStorage;
        this.getModeratorRestaurantsStorage = getModeratorRestaurantsStorage;
        this.rejectRestaurantStorage = rejectRestaurantStorage;
        this.rejectFeedbackStorage = rejectFeedbackStorage;
        this.approveRestaurantStorage = approveRestaurantStorage;
        this.approveFeedbackStorage = approveFeedbackStorage;
    }

    @Override
    public void getRestaurants(GetModeratorRestaurantsListener listener) {
        getModeratorRestaurantsStorage.getRestaurants(listener);
    }

    @Override
    public void rejectRestaurant(String restaurantId) {
        rejectRestaurantStorage.reject(restaurantId);
    }

    @Override
    public void approveRestaurant(String restaurantId) {
        approveRestaurantStorage.approve(restaurantId);
    }

    @Override
    public void getFeedbacks(GetModeratorFeedbacksListener listener) {
        getModeratorFeedbacksStorage.getFeedbacks(listener);
    }

    @Override
    public void rejectFeedback(String FeedbackId) {
        rejectFeedbackStorage.reject(FeedbackId);
    }

    @Override
    public void approveFeedback(NewFeedbackModel newFeedbackModel) {
        approveFeedbackStorage.approve(newFeedbackModel);
    }
}
