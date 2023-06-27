package com.example.domain.useCases;

import com.example.domain.listeners.AddFeedbackListener;
import com.example.domain.repository.MyFeedbacksRepository;

public class AddFeedbackUseCase {

    MyFeedbacksRepository repository;

    public AddFeedbackUseCase(MyFeedbacksRepository repository){
        this.repository = repository;

    }

    public void execute(AddFeedbackListener listener, String id, String dishName, String feedback, String restaurantName, Integer estimation, String restaurantId){
        repository.addFeedback(listener, id, dishName, feedback, restaurantName, estimation, restaurantId);
    }
}
