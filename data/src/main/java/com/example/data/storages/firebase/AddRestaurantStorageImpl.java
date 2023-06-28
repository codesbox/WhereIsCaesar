package com.example.data.storages.firebase;

import android.widget.Toast;

import com.example.domain.listeners.AddRestaurantListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddRestaurantStorageImpl implements AddRestaurantStorage{
    @Override
    public void addRestaurant(Double latitude, Double longitude, String restaurantName, String id, AddRestaurantListener listener, String address) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference restaurantsRef = db.collection("Restaurants");
        String restaurantId = restaurantsRef.document().getId();

        // Создание данных для добавления
        Map<String, Object> restaurantData = new HashMap<>();
        restaurantData.put("allSum", 0);
        restaurantData.put("allCount", 0);
        restaurantData.put("dishes", new HashMap<>());
        restaurantData.put("dishesNames", new ArrayList<>());
        restaurantData.put("name", restaurantName);
        restaurantData.put("userId", id);
        restaurantData.put("status", "m");

        // Добавление документа в коллекцию Restaurants
        restaurantsRef.add(restaurantData)
                .addOnSuccessListener(aVoid -> {

                    Map<String, Object> pointData = new HashMap<>();
                    pointData.put("latitude", latitude);
                    pointData.put("longitude", longitude);
                    pointData.put("address", address);
                    GeoPoint location = new GeoPoint(latitude, longitude);
                    pointData.put("geoPoint", location);
                    pointData.put("restaurantId", aVoid.getId());

                    db.collection("RestaurantsPoints")
                            .add(pointData)
                            .addOnSuccessListener(documentReference -> {
                                listener.onSuccess();
                            })
                            .addOnFailureListener(e -> {
                                listener.onFailure();
                            });

                })
                .addOnFailureListener(e -> {

                    // Ошибка при добавлении
                });
    }
}
