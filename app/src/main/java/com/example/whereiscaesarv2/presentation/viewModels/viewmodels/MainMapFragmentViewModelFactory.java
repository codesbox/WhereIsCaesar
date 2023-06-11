package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.repositories.DishesRepositoryImpl;
import com.example.data.repositories.RestaurantsRepositoryImpl;
import com.example.data.storages.firebase.DishesStorage;
import com.example.data.storages.firebase.DishesStorageImpl;
import com.example.data.storages.firebase.RestaurantsStorage;
import com.example.data.storages.firebase.RestaurantsStorageImpl;
import com.example.domain.listeners.GetRestaurantsListener;
import com.example.domain.models.RestaurantModelDomain;
import com.example.domain.repository.DishesRepository;
import com.example.domain.repository.RestaurantsRepository;
import com.example.domain.useCases.GetRestaurantsUseCase;
import com.example.whereiscaesarv2.presentation.util.listeners.GetRestaurantsListenerImpl;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModel;

import java.util.List;

public class MainMapFragmentViewModelFactory implements ViewModelProvider.Factory {

    MutableLiveData<List<RestaurantModelDomain>> allRestaurantsList = new MutableLiveData<>();

    GetRestaurantsUseCase getRestaurantsUseCase;

    public MainMapFragmentViewModelFactory(){
        RestaurantsStorage storage = new RestaurantsStorageImpl();
        RestaurantsRepository repository = new RestaurantsRepositoryImpl(storage);
        GetRestaurantsListener listener = new GetRestaurantsListenerImpl(allRestaurantsList);
        getRestaurantsUseCase = new GetRestaurantsUseCase(repository, listener);


    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainMapFragmentViewModel(allRestaurantsList, getRestaurantsUseCase);
    }
}
