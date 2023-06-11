package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.models.DishModelDomain;
import com.example.domain.models.PointModel;
import com.example.domain.models.RestaurantModelDomain;
import com.example.domain.useCases.GetRestaurantsUseCase;

import java.util.ArrayList;
import java.util.List;

public class MainMapFragmentViewModel extends ViewModel {

    MutableLiveData<List<RestaurantModelDomain>> allRestaurantsList;

    MutableLiveData<List<RestaurantModelDomain>> restaurantsList = new MutableLiveData<>();

    GetRestaurantsUseCase getRestaurantsUseCase;

    public MainMapFragmentViewModel(MutableLiveData<List<RestaurantModelDomain>> allRestaurantsList, GetRestaurantsUseCase getRestaurantsUseCase){
        this.allRestaurantsList = allRestaurantsList;
        this.getRestaurantsUseCase = getRestaurantsUseCase;
    }

    public LiveData<List<RestaurantModelDomain>> getRestaurants(){
        return allRestaurantsList;
    }

    public void updateRestaurantsList(List<String> dishList, PointModel topLeftPoint, PointModel bottomRightPoint){
        getRestaurantsUseCase.execute(dishList, topLeftPoint, bottomRightPoint);
    }





}
