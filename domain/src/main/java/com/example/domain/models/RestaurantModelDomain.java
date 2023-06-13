package com.example.domain.models;

import java.io.Serializable;
import java.util.List;

public class RestaurantModelDomain implements Serializable {

    public String restaurantName, userId;
    public Integer allSum, allCount;
    public PointModel geoPoint;
    public List<MapDishCard> dishNameList;

    public RestaurantModelDomain(String restaurantName, String userId, Integer allSum,
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
