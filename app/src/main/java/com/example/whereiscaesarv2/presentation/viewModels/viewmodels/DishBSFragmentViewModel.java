package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.models.FeedbackModel;
import com.example.domain.models.MapDishCard;
import com.example.domain.useCases.GetEstimationsUseCase;

import java.util.List;

public class DishBSFragmentViewModel extends ViewModel {
    MutableLiveData<List<FeedbackModel>> mutableLiveData;
    GetEstimationsUseCase getEstimationsUseCase;

    public DishBSFragmentViewModel(MutableLiveData<List<FeedbackModel>> mutableLiveData, GetEstimationsUseCase getEstimationsUseCase){
        this.mutableLiveData = mutableLiveData;
        this.getEstimationsUseCase = getEstimationsUseCase;

    }

    public void getEstimation(String restaurantName, String dishName){
        getEstimationsUseCase.execute(restaurantName, dishName);
    }

    public LiveData<List<FeedbackModel>> getMutableLiveData(){
        return mutableLiveData;
    }



}
