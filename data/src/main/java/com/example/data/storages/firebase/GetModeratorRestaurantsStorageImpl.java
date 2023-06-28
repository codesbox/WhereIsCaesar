package com.example.data.storages.firebase;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.domain.listeners.GetModeratorRestaurantsListener;
import com.example.domain.models.NewRestaurantModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetModeratorRestaurantsStorageImpl implements GetModeratorRestaurantsStorage{

    static Integer counter = 0;
    static Integer count = 0;
    @Override
    public void getRestaurants(GetModeratorRestaurantsListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Restaurants");

        Query query = collectionRef.whereEqualTo("status", "m");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<NewRestaurantModel> newRestaurantModelList = new ArrayList<>();
                    count = task.getResult().size();
                    for (DocumentSnapshot document : task.getResult()) {
                        CollectionReference collectionRef = db.collection("Users");
                        Query query = collectionRef.whereEqualTo(FieldPath.documentId(), document.getString("userId"));

                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){

                                    for (DocumentSnapshot documentSnapshot : task.getResult()){
                                        Log.d("AAAAAAAAAAAAAAAA", "sss");
                                        newRestaurantModelList.add(new NewRestaurantModel(document.getString("name"), document.getId(), documentSnapshot.getString("firstName"), documentSnapshot.getString("lastName")));
                                        listener.onSuccess(newRestaurantModelList);
                                    }
                                    counter += 1;
                                    if (counter.equals(count)){
                                        listener.onSuccess(newRestaurantModelList);
                                    }

                                }
                            }
                        });


                    }
                } else {
                    Log.d(TAG, "Ошибка получения документов: " + task.getException());
                    listener.onFailure();
                }
            }
        });


    }
}
