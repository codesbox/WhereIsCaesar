package com.example.data.storages.firebase;

import com.example.domain.listeners.AddPointListener;

public interface AddPointStorage {
    void AddPoint(Double latitude, Double longitude, String address, String restaurantId, AddPointListener listener);
}
