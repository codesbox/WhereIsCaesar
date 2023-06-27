package com.example.data.repositories;

import com.example.data.storages.firebase.AddFeedbackStorage;
import com.example.data.storages.firebase.GetMyFeedbacksStorage;
import com.example.domain.listeners.AddFeedbackListener;
import com.example.domain.listeners.GetMyFeedbacksListener;
import com.example.domain.repository.MyFeedbacksRepository;

public class MyFeedbacksRepositoryImpl implements MyFeedbacksRepository {

    GetMyFeedbacksStorage getMyFeedbacksStorage;
    AddFeedbackStorage addFeedbackStorage;
    public MyFeedbacksRepositoryImpl(GetMyFeedbacksStorage getMyFeedbacksStorage, AddFeedbackStorage addFeedbackStorage){
        this.getMyFeedbacksStorage = getMyFeedbacksStorage;
        this.addFeedbackStorage = addFeedbackStorage;
    }




    @Override
    public void getMyFeedbacks(String userId, GetMyFeedbacksListener listener) {
        getMyFeedbacksStorage.getMyFeedbacks(userId, listener);
    }

    @Override
    public void addFeedback(AddFeedbackListener listener, String id, String dishName, String feedback, String restaurantName, Integer estimation) {
        addFeedbackStorage.addFeedBack(listener, id, dishName, feedback, restaurantName, estimation);
    }
}
