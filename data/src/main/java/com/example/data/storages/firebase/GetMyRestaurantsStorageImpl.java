package com.example.data.storages.firebase;

import com.example.domain.listeners.GetMyRestaurantsListener;
import com.example.domain.models.MyRestaurantsModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetMyRestaurantsStorageImpl implements GetMyRestaurantsStorage{

    @Override
    public void getMyRestaurants(String userId, GetMyRestaurantsListener listener) {

        List<MyRestaurantsModel> restaurantsList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference restaurantsRef = db.collection("Restaurants");

        Query query = restaurantsRef.whereEqualTo("userId", userId);

        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {

                                restaurantsList.add(new MyRestaurantsModel(document.getString("name"), document.getId(), document.getString("status")));

                            }
                            listener.onSuccess(restaurantsList);
                        }
                    } else {
                        listener.onFailure();
                    }
                });
    }
}
