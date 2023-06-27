package com.example.data.storages.firebase;

import com.example.domain.listeners.GetMyFeedbacksListener;

public interface GetMyFeedbacksStorage {
    void getMyFeedbacks(String userId, GetMyFeedbacksListener listener);
}
