package com.example.domain.useCases;

import com.example.domain.listeners.GetMyFeedbacksListener;
import com.example.domain.repository.MyFeedbacksRepository;

public class GetMyFeedbacksUseCase {

    MyFeedbacksRepository repository;

    public GetMyFeedbacksUseCase(MyFeedbacksRepository repository){
        this.repository = repository;
    }

    public void execute(String userId, GetMyFeedbacksListener listener){
        repository.getMyFeedbacks(userId, listener);
    }
}
