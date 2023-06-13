package com.example.data.storages.models;

import com.example.domain.models.MapDishCard;
import com.example.domain.models.PointModel;

import java.util.List;

public class RestaurantModelData {

    public String restaurantName, userId;
    public Integer allSum, allCount;
    public PointModel geoPoint;
    public List<MapDishCard> dishNameList;

    public RestaurantModelData(String restaurantName, String userId, Integer allSum,
                                 Integer allCount, PointModel geoPoint,
                                 List<MapDishCard> dishNameList) {
        this.restaurantName = restaurantName;
        this.userId = userId;
        this.allSum = allSum;
        this.allCount = allCount;
        this.geoPoint = geoPoint;
        this.dishNameList = dishNameList;
    }
}
