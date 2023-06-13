package com.example.whereiscaesarv2.presentation.util.listeners;

import androidx.lifecycle.MutableLiveData;

import com.example.domain.listeners.GetEstimationsListener;
import com.example.domain.models.FeedbackModel;

import java.util.List;

public class GetEstimationListenerImpl implements GetEstimationsListener {

    MutableLiveData<List<FeedbackModel>> mutableLiveData;

    public GetEstimationListenerImpl(MutableLiveData<List<FeedbackModel>> mutableLiveData){

        this.mutableLiveData = mutableLiveData;

    }
    @Override
    public void getEstimations(List<FeedbackModel> feedbackModelList) {
        mutableLiveData.setValue(feedbackModelList);
    }
}
