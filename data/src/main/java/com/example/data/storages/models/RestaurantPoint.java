package com.example.data.storages.models;

import com.example.domain.models.PointModel;

public class RestaurantPoint {
    public String address;
    public PointModel pointModel;

    public RestaurantPoint(String address, PointModel pointModel) {
        this.address = address;
        this.pointModel = pointModel;
    }
}
