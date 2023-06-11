package com.example.domain.models;

import java.util.List;

public class RestaurantModelDomain {

    public String restaurantName, userId;
    public Integer allSum, allCount;
    public PointModel geoPoint;
    public List<String> dishNameList;

    public RestaurantModelDomain(String restaurantName, String userId, Integer allSum,
                                 Integer allCount, PointModel geoPoint,
                                 List<String> dishNameList) {
        this.restaurantName = restaurantName;
        this.userId = userId;
        this.allSum = allSum;
        this.allCount = allCount;
        this.geoPoint = geoPoint;
        this.dishNameList = dishNameList;
    }
}
