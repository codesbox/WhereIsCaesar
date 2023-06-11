package com.example.domain.listeners;

import com.example.domain.models.RestaurantModelDomain;

import java.util.List;

public interface GetRestaurantsListener {

    void getRestaurants(List<RestaurantModelDomain> restaurantModelDomainList);
}
