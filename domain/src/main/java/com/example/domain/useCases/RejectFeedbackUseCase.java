package com.example.domain.useCases;

import com.example.domain.repository.ModeratorRepository;

public class RejectFeedbackUseCase {

    ModeratorRepository repository;

    public RejectFeedbackUseCase(ModeratorRepository repository) {
        this.repository = repository;
    }

    public void execute(String feedbackId){
        repository.rejectFeedback(feedbackId);
    }
}
