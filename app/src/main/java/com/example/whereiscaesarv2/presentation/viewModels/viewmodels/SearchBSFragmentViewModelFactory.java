package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.repositories.DishesRepositoryImpl;
import com.example.data.storages.firebase.DishesStorage;
import com.example.data.storages.firebase.DishesStorageImpl;
import com.example.domain.listeners.DishesListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.repository.DishesRepository;
import com.example.domain.useCases.GetDishesUseCase;
import com.example.whereiscaesarv2.presentation.util.listeners.DishesListenerImpl;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SearchBSFragmentViewModel;

import java.util.List;

public class SearchBSFragmentViewModelFactory implements ViewModelProvider.Factory {

    MutableLiveData<List<DishModelDomain>> mutableLiveData = new MutableLiveData<>();
    GetDishesUseCase getDishesUseCase;

    public SearchBSFragmentViewModelFactory(String tag){

        DishesStorage dishesStorage = new DishesStorageImpl();
        DishesRepository dishesRepository = new DishesRepositoryImpl(dishesStorage);
        DishesListener dishesListener = new DishesListenerImpl(mutableLiveData);
        getDishesUseCase = new GetDishesUseCase(dishesRepository, dishesListener, tag);

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchBSFragmentViewModel(getDishesUseCase, mutableLiveData);
    }
}
