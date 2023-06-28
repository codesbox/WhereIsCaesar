package com.example.data.storages.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.listeners.GetEstimationsListener;
import com.example.domain.models.FeedbackModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EstimationsStorageImpl implements EstimationsStorage{



    @Override
    public void getEstimations(GetEstimationsListener listener, String dishName, String restaurantName) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference feedbacksRef = db.collection("Feedbacks");

        feedbacksRef.whereEqualTo("dishName", dishName)
                .whereEqualTo("restaurantId", restaurantName)
                .whereEqualTo("status", "s")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Получение списка документов
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            List<FeedbackModel> feedbackList = new ArrayList<>();
                            for (DocumentSnapshot document : documents) {
                                feedbackList.add(new FeedbackModel(document.getString("firstName") + " " + document.getString("lastName"), document.getDouble("estimation").intValue(), document.getString("feedbackText"), document.getDouble("userLevel").intValue()));

                            }
                            listener.getEstimations(feedbackList);
                        } else {
                            Log.e("FirestoreExample", "Ошибка при получении документов: ", task.getException());
                        }
                    }
                });


    }
}
