package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.models.RestaurantModelDomain;

import java.util.List;

public class RestaurantCardBSFragmentViewModel extends ViewModel {

    MutableLiveData<RestaurantModelDomain> mutableLiveData = new MutableLiveData<>();

    MutableLiveData<List<String>> selectedDishes = new MutableLiveData<>();

    public LiveData<RestaurantModelDomain> getRestaurant(){
        return mutableLiveData;
    }

    public LiveData<List<String>> getSelectedDishes(){
        return selectedDishes;
    }

    public void setMutableLiveData(RestaurantModelDomain restaurantModelDomain){
        mutableLiveData.setValue(restaurantModelDomain);
    }

    public void setSelectedDishes(List<String> selectedDishes){
        this.selectedDishes.setValue(selectedDishes);
    }


}
