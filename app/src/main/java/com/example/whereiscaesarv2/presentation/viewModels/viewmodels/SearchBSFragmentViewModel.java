package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.models.DishModelDomain;
import com.example.domain.useCases.GetDishesUseCase;

import java.util.List;

public class SearchBSFragmentViewModel extends ViewModel {
    GetDishesUseCase getDishesUseCase;
    MutableLiveData<List<DishModelDomain>> mutableLiveData;

    public SearchBSFragmentViewModel(GetDishesUseCase getDishesUseCase, MutableLiveData<List<DishModelDomain>> mutableLiveData){
        this.getDishesUseCase = getDishesUseCase;
        this.mutableLiveData = mutableLiveData;
        getDishesUseCase.execute();

    }

    public LiveData<List<DishModelDomain>> getDishes(){
        return mutableLiveData;
    }
}
