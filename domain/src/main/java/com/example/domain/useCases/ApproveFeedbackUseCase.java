package com.example.domain.useCases;

import com.example.domain.models.NewFeedbackModel;
import com.example.domain.repository.ModeratorRepository;

public class ApproveFeedbackUseCase {

    ModeratorRepository repository;

    public ApproveFeedbackUseCase(ModeratorRepository repository) {
        this.repository = repository;
    }
    public void execute(NewFeedbackModel newFeedbackModel){
        repository.approveFeedback(newFeedbackModel);
    }
}
