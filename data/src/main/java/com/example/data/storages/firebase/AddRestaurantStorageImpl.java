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
    public void addRestaurant(Double latitude, Double longitude, String restaurantName, String id, AddRestaurantListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference restaurantsRef = db.collection("Restaurants");
        String restaurantId = restaurantsRef.document().getId();

        // Создание данных для добавления
        Map<String, Object> restaurantData = new HashMap<>();
        restaurantData.put("allSum", 0);
        restaurantData.put("allCount", 0);
        restaurantData.put("dishes", new HashMap<>());
        restaurantData.put("dishesNames", new ArrayList<>());
        restaurantData.put("latitude", latitude);
        restaurantData.put("longitude", longitude);
        restaurantData.put("name", restaurantName);
        restaurantData.put("mainPoint", true);
        restaurantData.put("address", true);
        restaurantData.put("userId", id);

        GeoPoint location = new GeoPoint(latitude, longitude);
        restaurantData.put("geoPoint", location);

        // Добавление документа в коллекцию Restaurants
        restaurantsRef.document(restaurantName)
                .set(restaurantData, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    // Успешное добавление
                    listener.onSuccess();
                })
                .addOnFailureListener(e -> {
                    listener.onFailure();
                    // Ошибка при добавлении
                });
    }
}
