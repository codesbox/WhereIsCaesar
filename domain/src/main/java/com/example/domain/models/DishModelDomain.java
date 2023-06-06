package com.example.domain.models;

public class DishModelDomain {
    public String title, imageUrl;
    public Boolean isCategory;

    public DishModelDomain(String title, String imageUrl, Boolean isCategory) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.isCategory = isCategory;
    }


}
