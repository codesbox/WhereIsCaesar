package com.example.whereiscaesarv2.presentation.util.listeners;

import androidx.lifecycle.MutableLiveData;

import com.example.domain.listeners.DishesListener;
import com.example.domain.models.DishModelDomain;

import java.util.List;

public class DishesListenerImpl implements DishesListener {

    MutableLiveData<List<DishModelDomain>> mutableLiveData;

    public DishesListenerImpl(MutableLiveData<List<DishModelDomain>> mutableLiveData){
        this.mutableLiveData = mutableLiveData;
    }

    @Override
    public void getDishes(List<DishModelDomain> dishModelDomainList) {
        mutableLiveData.setValue(dishModelDomainList);

    }
}
