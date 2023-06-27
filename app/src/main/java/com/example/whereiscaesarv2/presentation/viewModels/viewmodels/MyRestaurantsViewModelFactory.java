package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.repositories.MyRestaurantsRepositoryImpl;
import com.example.data.storages.firebase.GetMyRestaurantsStorage;
import com.example.data.storages.firebase.GetMyRestaurantsStorageImpl;
import com.example.domain.repository.MyRestaurantsRepository;
import com.example.domain.useCases.GetMyRestaurantsUseCase;

public class MyRestaurantsViewModelFactory implements ViewModelProvider.Factory {

    GetMyRestaurantsUseCase useCase;

    public MyRestaurantsViewModelFactory(){
        GetMyRestaurantsStorage storage = new GetMyRestaurantsStorageImpl();
        MyRestaurantsRepository repository = new MyRestaurantsRepositoryImpl(storage);
        useCase = new GetMyRestaurantsUseCase(repository);

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MyRestaurantsViewModel(useCase);
    }
}
