package com.example.data.storages.firebase;

import com.example.domain.models.NewFeedbackModel;

public interface ApproveFeedbackStorage {
    void approve(NewFeedbackModel newFeedbackModel);
}
