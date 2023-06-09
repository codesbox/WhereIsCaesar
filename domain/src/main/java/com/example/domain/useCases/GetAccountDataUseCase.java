package com.example.domain.useCases;

import com.example.domain.listeners.AccountListener;
import com.example.domain.repository.AccountRepository;

public class GetAccountDataUseCase {

    AccountRepository accountRepository;
    AccountListener accountListener;
    String id;

    public GetAccountDataUseCase(AccountRepository accountRepository, AccountListener accountListener, String id) {
        this.accountRepository = accountRepository;
        this.accountListener = accountListener;
        this.id = id;
    }

    public void execute(){
        accountRepository.getAccountData(accountListener, id);
    }
}
