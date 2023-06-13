package com.example.domain.models;

import java.io.Serializable;

public class MapDishCard implements Serializable {
    public String dishName;
    public String imageUrl;
    public Integer counter;
    public Integer estimation;
    public String category;
    public Integer sum;

    public MapDishCard(String dishName, String imageUrl, Integer counter, Integer sum, String category) {
        this.dishName = dishName;
        this.imageUrl = imageUrl;
        this.counter = counter;
        this.sum = sum;
        this.estimation = this.sum / this.counter;
        this.category = category;
    }
}
