package com.example.data.repositories;

import com.example.data.storages.firebase.AccountStorage;
import com.example.domain.listeners.AccountListener;
import com.example.domain.listeners.AddUserListener;
import com.example.domain.models.AddUserModelDomain;
import com.example.domain.repository.AccountRepository;

import java.util.Map;

public class AccountRepositoryImpl implements AccountRepository {

    private final AccountStorage accountStorage;

    public AccountRepositoryImpl(AccountStorage accountStorage) {
        this.accountStorage = accountStorage;
    }

    @Override
    public void getAccountData(AccountListener listener, String id) {

        accountStorage.getAccountData(listener, id);

    }

    @Override
    public void addUser(AddUserListener listener, AddUserModelDomain addUserModelDomain) {
        accountStorage.addUser(listener, addUserModelDomain);
    }
}
