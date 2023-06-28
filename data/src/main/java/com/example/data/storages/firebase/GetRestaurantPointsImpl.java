package com.example.data.storages.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.domain.listeners.GetPointsListener;
import com.example.domain.models.RestaurantPointModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetRestaurantPointsImpl implements GetRestaurantPoints{
    @Override
    public void getPoints(String restaurantId, GetPointsListener getPointsListener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("RestaurantsPoints")
                .whereEqualTo("restaurantId", restaurantId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<RestaurantPointModel> restaurantPointModelList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String address = document.getString("address");
                                String id = document.getId();
                                RestaurantPointModel restaurantPointModel = new RestaurantPointModel(address, id);
                                restaurantPointModelList.add(restaurantPointModel);
                            }

                            getPointsListener.onSuccess(restaurantPointModelList);
                        } else {
                            getPointsListener.onFailure();
                        }
                    }
                });

    }
}
