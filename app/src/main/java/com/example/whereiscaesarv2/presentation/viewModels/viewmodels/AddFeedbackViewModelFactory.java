package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.repositories.MyFeedbacksRepositoryImpl;
import com.example.data.storages.firebase.AddFeedbackStorage;
import com.example.data.storages.firebase.AddFeedbackStorageImpl;
import com.example.data.storages.firebase.GetMyFeedbacksStorage;
import com.example.data.storages.firebase.GetMyFeedbacksStorageImpl;
import com.example.domain.repository.MyFeedbacksRepository;
import com.example.domain.useCases.AddFeedbackUseCase;

public class AddFeedbackViewModelFactory implements ViewModelProvider.Factory {

    AddFeedbackUseCase useCase;

    public AddFeedbackViewModelFactory(){
        GetMyFeedbacksStorage getMyFeedbacksStorage = new GetMyFeedbacksStorageImpl();
        AddFeedbackStorage addFeedbackStorage = new AddFeedbackStorageImpl();
        MyFeedbacksRepository repository = new MyFeedbacksRepositoryImpl(getMyFeedbacksStorage, addFeedbackStorage);
        useCase = new AddFeedbackUseCase(repository);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddFeedbackViewModel(useCase);
    }
}
