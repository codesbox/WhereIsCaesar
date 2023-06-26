package com.example.domain.listeners;

public interface AddDishListener {

    void onSuccess();
    void onFailure();
    void  onDishHasAlreadyBeenAdded();
}
