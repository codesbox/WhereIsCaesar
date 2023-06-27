package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.repositories.MyRestaurantRepositoryImpl;
import com.example.data.storages.firebase.AddDishStorage;
import com.example.data.storages.firebase.AddDishStorageImpl;
import com.example.data.storages.firebase.AddRestaurantStorage;
import com.example.data.storages.firebase.AddRestaurantStorageImpl;
import com.example.data.storages.firebase.DeleteDishStorage;
import com.example.data.storages.firebase.DeleteDishStorageImpl;
import com.example.data.storages.firebase.GetMyRestaurantStorage;
import com.example.data.storages.firebase.GetMyRestaurantStorageImpl;
import com.example.domain.repository.MyRestaurantRepository;
import com.example.domain.useCases.AddRestaurantUseCase;

public class AddRestaurantViewModelFactory implements ViewModelProvider.Factory {

    AddRestaurantUseCase useCase;

    public AddRestaurantViewModelFactory(){
        AddDishStorage addDishStorage = new AddDishStorageImpl();
        DeleteDishStorage deleteDishStorage = new DeleteDishStorageImpl();
        GetMyRestaurantStorage getMyRestaurantStorage = new GetMyRestaurantStorageImpl();
        AddRestaurantStorage addRestaurantStorage = new AddRestaurantStorageImpl();
        MyRestaurantRepository repository = new MyRestaurantRepositoryImpl(addDishStorage, deleteDishStorage, getMyRestaurantStorage, addRestaurantStorage);
        useCase = new AddRestaurantUseCase(repository);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddRestaurantViewModel(useCase);
    }
}
