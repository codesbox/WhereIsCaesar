package com.example.data.storages.firebase;

import com.example.domain.listeners.AddFeedbackListener;

public interface AddFeedbackStorage {

    void addFeedBack(AddFeedbackListener listener, String id, String dishName, String feedback, String restaurantName, Integer estimation);
}
