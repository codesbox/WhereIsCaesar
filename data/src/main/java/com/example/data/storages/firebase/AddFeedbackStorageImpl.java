package com.example.data.storages.firebase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.domain.listeners.AddFeedbackListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFeedbackStorageImpl implements AddFeedbackStorage{

    @Override
    public void addFeedBack(AddFeedbackListener listener, String id, String dishName, String feedback, String restaurantName, Integer estimation, String restaurantId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("Users");

        usersRef.whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshotp = task.getResult();
                            if (querySnapshotp != null && !querySnapshotp.isEmpty()) {
                                DocumentSnapshot documentSnapshotr = querySnapshotp.getDocuments().get(0);
                                String firstName = documentSnapshotr.getString("firstName");
                                String lastName = documentSnapshotr.getString("lastName");
                                Integer level = documentSnapshotr.getDouble("feedbackCount").intValue();
                                CollectionReference collection = db.collection("Feedbacks");


                                DocumentReference newDocumentRef = collection.document();


                                Map<String, Object> data = new HashMap<>();
                                data.put("dishName", dishName);
                                data.put("feedbackText", feedback);
                                data.put("restaurantName", restaurantName);
                                data.put("userId", id);
                                data.put("estimation", estimation);
                                data.put("firstName", firstName);
                                data.put("lastName", lastName);
                                data.put("userLevel", level);
                                data.put("restaurantId", restaurantId);
                                data.put("status", "m");


                                newDocumentRef.set(data)
                                        .addOnSuccessListener(aVoid -> {

                                            CollectionReference usersCollection = db.collection("Users");

                                            String targetId = FirebaseAuth.getInstance().getUid();

                                            usersCollection.whereEqualTo("id", targetId)
                                                    .get()
                                                    .addOnSuccessListener(querySnapshot -> {
                                                        for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {

                                                            Integer currentFeedbackCount = documentSnapshot.getLong("feedbackCount").intValue();


                                                            int updatedFeedbackCount = currentFeedbackCount + 1;


                                                            documentSnapshot.getReference().update("feedbackCount", updatedFeedbackCount)
                                                                    .addOnSuccessListener(aVoi -> {

                                                                    })
                                                                    .addOnFailureListener(e -> {

                                                                    });
                                                        }
                                                    })
                                                    .addOnFailureListener(e -> {

                                                    });

                                            listener.onSuccess();







                                            //Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show();

                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e("FirestoreExample", "Ошибка при получении документа: ", task.getException());
                                        });





                            } else {
                                Log.d("FirestoreExample", "Документ не найден");
                            }
                        } else {
                            Log.e("FirestoreExample", "Ошибка при получении документа: ", task.getException());
                        }
                    }
                });


    }
}
