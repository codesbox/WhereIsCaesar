package com.example.domain.models;

public class AddUserModelDomain {
    public String firstName, lastName, email, password;

    public AddUserModelDomain(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}
