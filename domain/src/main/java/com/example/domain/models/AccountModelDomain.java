package com.example.domain.models;

import java.io.Serializable;

public class AccountModelDomain implements Serializable {
    public String firstName, lastName, phoneNumber, id, type;
    public Integer feedBackCount;

    public AccountModelDomain(String firstName, String lastName, String phoneNumber, String id, String type, Integer feedBackCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.feedBackCount = feedBackCount;
        this.type = type;
    }
}
