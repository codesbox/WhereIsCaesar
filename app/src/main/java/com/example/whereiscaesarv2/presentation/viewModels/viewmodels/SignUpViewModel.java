package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.data.repositories.AccountRepositoryImpl;
import com.example.data.storages.firebase.AccountStorage;
import com.example.data.storages.firebase.AccountStorageImpl;
import com.example.domain.listeners.AddUserListener;
import com.example.domain.models.AddUserModelDomain;
import com.example.domain.repository.AccountRepository;
import com.example.domain.useCases.AddUserUseCase;

public class SignUpViewModel extends ViewModel {
    AddUserUseCase addUserUseCase;

    public SignUpViewModel(AddUserUseCase addUserUseCase){
        this.addUserUseCase = addUserUseCase;
    }

    public void addUser(AddUserModelDomain addUserModelDomain, AddUserListener listener){
        addUserUseCase.execute(listener, addUserModelDomain);
    }
}
