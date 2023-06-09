package com.example.domain.repository;

import com.example.domain.listeners.AccountListener;
import com.example.domain.listeners.AddUserListener;
import com.example.domain.models.AddUserModelDomain;

import java.util.Map;

public interface AccountRepository {

    void getAccountData(AccountListener listener, String id);
    void addUser(AddUserListener listener, AddUserModelDomain addUserModelDomain);
}
