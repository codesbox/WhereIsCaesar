package com.example.data.storages.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.domain.listeners.DeletePointListener;
import com.example.domain.models.RestaurantPointModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DeletePointStorageImpl implements DeletePointStorage {
    @Override
    public void deletePoint(RestaurantPointModel restaurantPointModel, DeletePointListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference feedbacksRef2 = db.collection("RestaurantsPoints");

        Query query2 = feedbacksRef2.whereEqualTo(FieldPath.documentId(), restaurantPointModel.id);

        query2.get().addOnCompleteListener(task2 -> {
            if (task2.isSuccessful()) {
                for (QueryDocumentSnapshot document : task2.getResult()) {
                    document.getReference().delete().addOnSuccessListener(aVoid -> {
                    }).addOnFailureListener(e -> {

                    });
                }
                listener.onSuccess();
            }
            else{
                listener.onFailure();
            }
        });
    }
}
