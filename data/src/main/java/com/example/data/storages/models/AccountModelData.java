package com.example.data.storages.models;

public class AccountModelData {
    public String firstName, lastName, phoneNumber, id, type;
    public Integer feedBackCount;

    public AccountModelData(String firstName, String lastName, String phoneNumber, String id, String type, Integer feedBackCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.feedBackCount = feedBackCount;
        this.type = type;
    }
}
