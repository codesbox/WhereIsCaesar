package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.repositories.ModeratorRepositoryImpl;
import com.example.data.storages.firebase.ApproveFeedbackStorage;
import com.example.data.storages.firebase.ApproveFeedbackStorageImpl;
import com.example.data.storages.firebase.ApproveRestaurantStorage;
import com.example.data.storages.firebase.ApproveRestaurantStorageImpl;
import com.example.data.storages.firebase.GetModeratorFeedbacksStorage;
import com.example.data.storages.firebase.GetModeratorFeedbacksStorageImpl;
import com.example.data.storages.firebase.GetModeratorRestaurantsStorage;
import com.example.data.storages.firebase.GetModeratorRestaurantsStorageImpl;
import com.example.data.storages.firebase.RejectFeedbackStorage;
import com.example.data.storages.firebase.RejectFeedbackStorageImpl;
import com.example.data.storages.firebase.RejectRestaurantStorage;
import com.example.data.storages.firebase.RejectRestaurantStorageImpl;
import com.example.domain.repository.ModeratorRepository;
import com.example.domain.useCases.ApproveFeedbackUseCase;
import com.example.domain.useCases.ApproveRestaurantUseCase;
import com.example.domain.useCases.GetModeratorFeedbacksUseCase;
import com.example.domain.useCases.GetModeratorRestaurantsUseCase;
import com.example.domain.useCases.RejectFeedbackUseCase;
import com.example.domain.useCases.RejectRestaurantUseCase;

public class NewFeedbacksViewModelFactory implements ViewModelProvider.Factory {

    GetModeratorFeedbacksUseCase getModeratorFeedbacksUseCase;
    RejectFeedbackUseCase rejectFeedbackUseCase;
    ApproveFeedbackUseCase approveFeedbackUseCase;



    public NewFeedbacksViewModelFactory(){
        GetModeratorFeedbacksStorage getModeratorFeedbacksStorage = new GetModeratorFeedbacksStorageImpl();
        GetModeratorRestaurantsStorage getModeratorRestaurantsStorage = new GetModeratorRestaurantsStorageImpl();
        RejectRestaurantStorage rejectRestaurantStorage = new RejectRestaurantStorageImpl();
        RejectFeedbackStorage rejectFeedbackStorage = new RejectFeedbackStorageImpl();
        ApproveRestaurantStorage approveRestaurantStorage = new ApproveRestaurantStorageImpl();
        ApproveFeedbackStorage approveFeedbackStorage = new ApproveFeedbackStorageImpl();
        ModeratorRepository moderatorRepository = new ModeratorRepositoryImpl(getModeratorFeedbacksStorage, getModeratorRestaurantsStorage, rejectRestaurantStorage, rejectFeedbackStorage, approveRestaurantStorage, approveFeedbackStorage);
        getModeratorFeedbacksUseCase = new GetModeratorFeedbacksUseCase(moderatorRepository);
        rejectFeedbackUseCase = new RejectFeedbackUseCase(moderatorRepository);
        approveFeedbackUseCase = new ApproveFeedbackUseCase(moderatorRepository);

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewFeedbacksViewModel(getModeratorFeedbacksUseCase, rejectFeedbackUseCase, approveFeedbackUseCase);
    }
}
