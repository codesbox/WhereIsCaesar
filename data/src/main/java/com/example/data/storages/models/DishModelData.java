package com.example.data.storages.models;

public class DishModelData {

    public String title, imageUrl;
    public Boolean IsCategory;

    public DishModelData(String title, String imageUrl, Boolean isCategory) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.IsCategory = isCategory;
    }


}
