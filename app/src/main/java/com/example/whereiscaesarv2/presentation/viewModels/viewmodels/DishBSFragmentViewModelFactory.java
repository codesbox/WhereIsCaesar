package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.repositories.EstimationsRepositoryImpl;
import com.example.data.storages.firebase.EstimationsStorage;
import com.example.data.storages.firebase.EstimationsStorageImpl;
import com.example.domain.listeners.GetEstimationsListener;
import com.example.domain.models.FeedbackModel;
import com.example.domain.repository.EstimationsRepository;
import com.example.domain.useCases.GetEstimationsUseCase;
import com.example.whereiscaesarv2.presentation.util.listeners.GetEstimationListenerImpl;

import java.util.List;

public class DishBSFragmentViewModelFactory implements ViewModelProvider.Factory {


    GetEstimationsUseCase getEstimationsUseCase;

    MutableLiveData<List<FeedbackModel>> mutableLiveData = new MutableLiveData<>();
    public DishBSFragmentViewModelFactory(){
        EstimationsStorage storage = new EstimationsStorageImpl();
        EstimationsRepository repository = new EstimationsRepositoryImpl(storage);
        GetEstimationsListener listener = new GetEstimationListenerImpl(mutableLiveData);
        getEstimationsUseCase = new GetEstimationsUseCase(listener, repository);

    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DishBSFragmentViewModel(mutableLiveData, getEstimationsUseCase);
    }
}
