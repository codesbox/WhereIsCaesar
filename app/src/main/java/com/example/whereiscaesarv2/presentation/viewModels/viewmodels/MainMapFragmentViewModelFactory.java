package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModel;

public class MainMapFragmentViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainMapFragmentViewModel();
    }
}
