package com.example.data.storages.firebase;

import com.example.domain.listeners.GetModeratorFeedbacksListener;

public interface GetModeratorFeedbacksStorage {
    void getFeedbacks(GetModeratorFeedbacksListener listener);
}
