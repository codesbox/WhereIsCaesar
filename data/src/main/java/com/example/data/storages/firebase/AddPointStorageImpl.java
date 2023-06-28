package com.example.data.storages.firebase;

import com.example.domain.listeners.AddPointListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;

public class AddPointStorageImpl implements AddPointStorage{
    @Override
    public void AddPoint(Double latitude, Double longitude, String address, String restaurantId, AddPointListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> pointData = new HashMap<>();
        pointData.put("latitude", latitude);
        pointData.put("longitude", longitude);
        pointData.put("address", address);
        GeoPoint location = new GeoPoint(latitude, longitude);
        pointData.put("geoPoint", location);
        pointData.put("restaurantId", restaurantId);

        db.collection("RestaurantsPoints")
                .add(pointData)
                .addOnSuccessListener(documentReference -> {
                    listener.onSuccess();
                })
                .addOnFailureListener(e -> {
                    listener.onFailure();
                });

    }
}
