package com.example.data.storages.firebase;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.domain.listeners.GetModeratorFeedbacksListener;
import com.example.domain.models.NewFeedbackModel;
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

public class GetModeratorFeedbacksStorageImpl implements GetModeratorFeedbacksStorage{
    @Override
    public void getFeedbacks(GetModeratorFeedbacksListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Feedbacks");

        Query query = collectionRef.whereEqualTo("status", "m");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<NewFeedbackModel> newFeedbackModelList = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {

                        String dishName = document.getString("dishName");
                        String feedbackText = document.getString("feedbackText");
                        String firstName = document.getString("firstName");
                        String lastName = document.getString("lastName");
                        String restaurantId = document.getString("restaurantId");
                        String restaurantName = document.getString("restaurantName");
                        String userId = document.getString("userId");
                        String feedbackId = document.getId();
                        Integer estimation = document.getDouble("estimation").intValue();
                        Integer userLevel = document.getDouble("userLevel").intValue();

                        newFeedbackModelList.add(new NewFeedbackModel(dishName, feedbackText, firstName, lastName,
                                restaurantId, restaurantName, userId, feedbackId, estimation, userLevel));
                    }
                    listener.onSuccess(newFeedbackModelList);
                } else {
                    Log.d(TAG, "Ошибка получения документов: " + task.getException());
                    listener.onFailure();
                }
            }
        });
    }
}
