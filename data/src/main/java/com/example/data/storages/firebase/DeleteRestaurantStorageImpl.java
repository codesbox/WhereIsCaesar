package com.example.data.storages.firebase;

import android.util.Log;

import com.example.domain.listeners.DeleteRestaurantListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DeleteRestaurantStorageImpl implements DeleteRestaurantStorage{
    @Override
    public void deleteRestaurant(String restaurantId, DeleteRestaurantListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference feedbacksRef = db.collection("Restaurants");

        Query query = feedbacksRef.whereEqualTo(FieldPath.documentId(), restaurantId);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("!!!!!!!!!!!!!!!!!!!!", "dfjdijfidjf");
                    document.getReference().delete().addOnSuccessListener(aVoid -> {
                        // Документ успешно удален
                    }).addOnFailureListener(e -> {

                    });
                }

                CollectionReference feedbacksRef2 = db.collection("RestaurantsPoints");

                Query query2 = feedbacksRef2.whereEqualTo("restaurantId", restaurantId);

                query2.get().addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task2.getResult()) {
                            Log.d("!!!!!!!!!!!!!!!!!!!!", "dfjdijfidjf");
                            document.getReference().delete().addOnSuccessListener(aVoid -> {
                                // Документ успешно удален
                            }).addOnFailureListener(e -> {

                            });
                        }

                        CollectionReference feedbacksRef3 = db.collection("Feedbacks");

                        Query query3 = feedbacksRef3.whereEqualTo("restaurantId", restaurantId);

                        query3.get().addOnCompleteListener(task3 -> {
                            if (task3.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task3.getResult()) {
                                    Log.d("!!!!!!!!!!!!!!!!!!!!", "dfjdijfidjf");
                                    document.getReference().delete().addOnSuccessListener(aVoid -> {
                                        // Документ успешно удален
                                    }).addOnFailureListener(e -> {

                                    });
                                }
                                listener.onSuccess();
                            } else {
                                listener.onFailure();
                            }
                        });

                    } else {
                        listener.onFailure();
                    }
                });

            } else {
                listener.onFailure();
            }
        });
    }
}
