package com.example.whereiscaesarv2.presentation.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MapSharedViewModel extends ViewModel {

    MutableLiveData<BottomSheetBehavior> behaviorMutableLiveData = new MutableLiveData<>();

    public void setBehaviorMutableLiveData(BottomSheetBehavior bottomSheetBehavior) {
        behaviorMutableLiveData.setValue(bottomSheetBehavior);
    }
    public LiveData<BottomSheetBehavior> getBottomSheetBehavior(){
        return behaviorMutableLiveData;
    }
}
