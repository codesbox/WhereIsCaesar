package com.example.whereiscaesarv2.presentation.util.listeners;

import com.example.domain.models.DishModelDomain;
import com.example.domain.models.RestaurantModelDomain;

import java.util.List;

public interface RestaurantsListCardClickListener {

    void onCardClick(RestaurantModelDomain restaurantModelDomain);
    void progressBar(List<RestaurantModelDomain> restaurantModelDomainList);
}
