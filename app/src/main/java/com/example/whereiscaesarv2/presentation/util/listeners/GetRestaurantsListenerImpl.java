package com.example.whereiscaesarv2.presentation.util.listeners;

import androidx.lifecycle.MutableLiveData;

import com.example.domain.listeners.GetRestaurantsListener;
import com.example.domain.models.RestaurantModelDomain;

import java.util.List;

public class GetRestaurantsListenerImpl implements GetRestaurantsListener {

    MutableLiveData<List<RestaurantModelDomain>> allRestaurantsList;

    public GetRestaurantsListenerImpl(MutableLiveData<List<RestaurantModelDomain>> allRestaurantsList){
        this.allRestaurantsList = allRestaurantsList;
    }
    @Override
    public void getRestaurants(List<RestaurantModelDomain> restaurantModelDomainList) {
        allRestaurantsList.setValue(restaurantModelDomainList);
    }
}
