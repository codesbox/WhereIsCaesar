package com.example.whereiscaesarv2.presentation.viewModels.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.repositories.AccountRepositoryImpl;
import com.example.data.storages.firebase.AccountStorage;
import com.example.data.storages.firebase.AccountStorageImpl;
import com.example.domain.repository.AccountRepository;
import com.example.domain.useCases.AddUserUseCase;

public class SignUpViewModelFactory implements ViewModelProvider.Factory {

    AddUserUseCase addUserUseCase;

    public SignUpViewModelFactory(){
        AccountStorage accountStorage = new AccountStorageImpl();
        AccountRepository accountRepository = new AccountRepositoryImpl(accountStorage);
        addUserUseCase = new AddUserUseCase(accountRepository);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SignUpViewModel(addUserUseCase);
    }
}
