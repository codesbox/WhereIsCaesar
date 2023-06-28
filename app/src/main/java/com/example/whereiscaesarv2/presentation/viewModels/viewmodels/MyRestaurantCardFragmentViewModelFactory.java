package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.repositories.MyRestaurantRepositoryImpl;
import com.example.data.storages.firebase.AddDishStorage;
import com.example.data.storages.firebase.AddDishStorageImpl;
import com.example.data.storages.firebase.AddPointStorage;
import com.example.data.storages.firebase.AddPointStorageImpl;
import com.example.data.storages.firebase.AddRestaurantStorage;
import com.example.data.storages.firebase.AddRestaurantStorageImpl;
import com.example.data.storages.firebase.DeleteDishStorage;
import com.example.data.storages.firebase.DeleteDishStorageImpl;
import com.example.data.storages.firebase.DeletePointStorage;
import com.example.data.storages.firebase.DeleteRestaurantStorage;
import com.example.data.storages.firebase.DeleteRestaurantStorageImpl;
import com.example.data.storages.firebase.GetMyRestaurantStorage;
import com.example.data.storages.firebase.GetMyRestaurantStorageImpl;
import com.example.data.storages.firebase.GetRestaurantPoints;
import com.example.data.storages.firebase.GetRestaurantPointsImpl;
import com.example.data.storages.firebase.DeletePointStorageImpl;
import com.example.domain.repository.MyRestaurantRepository;
import com.example.domain.useCases.DeleteDishUseCase;
import com.example.domain.useCases.DeleteRestaurantUseCase;
import com.example.domain.useCases.GetMyRestaurantUseCase;

public class MyRestaurantCardFragmentViewModelFactory implements ViewModelProvider.Factory {

    GetMyRestaurantUseCase useCase;
    DeleteDishUseCase deleteDishUseCase;
    DeleteRestaurantUseCase deleteRestaurantUseCase;

    public MyRestaurantCardFragmentViewModelFactory(){
        AddDishStorage addDishStorage = new AddDishStorageImpl();
        DeleteDishStorage deleteDishStorage = new DeleteDishStorageImpl();
        GetMyRestaurantStorage getMyRestaurantStorage = new GetMyRestaurantStorageImpl();
        AddRestaurantStorage addRestaurantStorage = new AddRestaurantStorageImpl();
        DeleteRestaurantStorage deleteRestaurantStorage = new DeleteRestaurantStorageImpl();
        GetRestaurantPoints getRestaurantPoints = new GetRestaurantPointsImpl();
        DeletePointStorage deletePointStorage = new DeletePointStorageImpl();
        AddPointStorage addPointStorage = new AddPointStorageImpl();
        MyRestaurantRepository repository = new MyRestaurantRepositoryImpl(addDishStorage, deleteDishStorage, getMyRestaurantStorage, addRestaurantStorage, deleteRestaurantStorage, getRestaurantPoints, deletePointStorage, addPointStorage);
        useCase = new GetMyRestaurantUseCase(repository);
        deleteDishUseCase = new DeleteDishUseCase(repository);
        deleteRestaurantUseCase = new DeleteRestaurantUseCase(repository);

    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MyRestaurantCardFragmentViewModel(useCase, deleteDishUseCase, deleteRestaurantUseCase);
    }
}
