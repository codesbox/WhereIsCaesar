package com.example.whereiscaesarv2.presentation.viewModels.sharedViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.models.DishModelDomain;

import java.util.ArrayList;
import java.util.List;

public class SearchSharedViewModel extends ViewModel {

    List<DishModelDomain> list;
    MutableLiveData<List<DishModelDomain>> mutableLiveData = new MutableLiveData<>();

    public SearchSharedViewModel(){ list = new ArrayList<>(); }

    public void addElement(DishModelDomain dishModelDomain){
        list.add(dishModelDomain);
        mutableLiveData.setValue(list);
    }

    public LiveData<List<DishModelDomain>> getData(){ return mutableLiveData; }

}
