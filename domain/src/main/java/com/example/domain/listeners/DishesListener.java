package com.example.domain.listeners;

import com.example.domain.models.DishModelDomain;

import java.util.List;

public interface DishesListener {
    void getDishes(List<DishModelDomain> dishModelDomainList);

}
