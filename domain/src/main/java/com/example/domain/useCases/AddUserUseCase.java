package com.example.domain.useCases;

import com.example.domain.listeners.AccountListener;
import com.example.domain.listeners.AddUserListener;
import com.example.domain.models.AddUserModelDomain;
import com.example.domain.repository.AccountRepository;

import java.util.HashMap;
import java.util.Objects;

public class AddUserUseCase {

    AccountRepository accountRepository;


    public AddUserUseCase(AccountRepository accountRepository){

        this.accountRepository = accountRepository;

    }

    public void execute(AddUserListener listener, AddUserModelDomain addUserModelDomain){
        accountRepository.addUser(listener, addUserModelDomain);
    }
}
