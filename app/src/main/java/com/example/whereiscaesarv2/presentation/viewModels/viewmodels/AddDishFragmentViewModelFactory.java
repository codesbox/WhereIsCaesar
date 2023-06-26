package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.repositories.MyRestaurantRepositoryImpl;
import com.example.data.storages.firebase.AddDishStorage;
import com.example.data.storages.firebase.AddDishStorageImpl;
import com.example.data.storages.firebase.DeleteDishStorage;
import com.example.data.storages.firebase.DeleteDishStorageImpl;
import com.example.data.storages.firebase.GetMyRestaurantStorage;
import com.example.data.storages.firebase.GetMyRestaurantStorageImpl;
import com.example.domain.repository.MyRestaurantRepository;
import com.example.domain.useCases.AddDishUseCase;

public class AddDishFragmentViewModelFactory implements ViewModelProvider.Factory {

    AddDishUseCase useCase;

    public AddDishFragmentViewModelFactory(){
        AddDishStorage addDishStorage = new AddDishStorageImpl();
        DeleteDishStorage deleteDishStorage = new DeleteDishStorageImpl();
        GetMyRestaurantStorage getMyRestaurantStorage = new GetMyRestaurantStorageImpl();
        MyRestaurantRepository repository = new MyRestaurantRepositoryImpl(addDishStorage, deleteDishStorage, getMyRestaurantStorage);
        useCase = new AddDishUseCase(repository);
    }




    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddDishFragmentViewModel(useCase);
    }
}
