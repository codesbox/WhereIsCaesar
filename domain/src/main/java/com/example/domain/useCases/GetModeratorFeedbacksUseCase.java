package com.example.domain.useCases;

import com.example.domain.listeners.GetModeratorFeedbacksListener;
import com.example.domain.repository.ModeratorRepository;

public class GetModeratorFeedbacksUseCase {

    ModeratorRepository repository;

    public GetModeratorFeedbacksUseCase(ModeratorRepository repository) {
        this.repository = repository;
    }

    public void execute(GetModeratorFeedbacksListener listener){
        repository.getFeedbacks(listener);
    }
}
