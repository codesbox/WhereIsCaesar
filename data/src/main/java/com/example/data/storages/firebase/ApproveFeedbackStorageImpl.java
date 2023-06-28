package com.example.data.storages.firebase;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.domain.models.NewFeedbackModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApproveFeedbackStorageImpl implements ApproveFeedbackStorage{
    @Override
    public void approve(NewFeedbackModel newFeedbackModel) {



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Feedbacks");

        Query query = collectionRef.whereEqualTo(FieldPath.documentId(), newFeedbackModel.feedbackId);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String documentId = document.getId();
                        DocumentReference documentRef = collectionRef.document(documentId);

                        // Обновление значения поля "q"
                        documentRef.update("status", "s")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {


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



                                        CollectionReference collectionRef = db.collection("Категории и блюда");

                                        collectionRef.document(newFeedbackModel.dishName).get().addOnSuccessListener(documentSnapshot -> {
                                            if (documentSnapshot.exists()) {
                                                //List<Object> tags = documentSnapshot.get("tags", List.class);
                                                String jsons = new Gson().toJson(documentSnapshot.get("tags"));
                                                Type types = new TypeToken<List<Object>>(){}.getType();
                                                List<Object> tags = new Gson().fromJson(jsons, types);
                                                if (tags != null && tags.size() > 1) {
                                                    String tag = tags.get(1).toString();
                                                    String imageUrlq = documentSnapshot.getString("image");
                                                    CollectionReference restaurantsCollection = db.collection("Restaurants");


                                                    restaurantsCollection.document(newFeedbackModel.restaurantId)
                                                            .get()
                                                            .addOnSuccessListener(documentSnapshotq -> {
                                                                if (documentSnapshotq.exists()) {
                                                                    //Map<String, Object> dishesMap = documentSnapshot.get("dishes", Map.class);

                                                                    Integer allCount = documentSnapshotq.getLong("allCount").intValue();
                                                                    Integer allSum = documentSnapshotq.getLong("allSum").intValue();


                                                                    String json = new Gson().toJson(documentSnapshotq.get("dishes"));
                                                                    Type type = new TypeToken<Map<String, Object>>(){}.getType();
                                                                    Map<String, Object> dishesMap = new Gson().fromJson(json, type);

                                                                    if (dishesMap != null && dishesMap.containsKey(newFeedbackModel.dishName)) {
                                                                        Map<String, Object> targetDish = (Map<String, Object>) dishesMap.get(newFeedbackModel.dishName);
                                                                        double currentCountDouble = ((Number) targetDish.get("count")).doubleValue();
                                                                        double currentSumDouble = ((Number) targetDish.get("sum")).doubleValue();
                                                                        int currentCount = (int) currentCountDouble;
                                                                        int currentSum = (int) currentSumDouble;

                                                                        int updatedCount = currentCount + 1;
                                                                        int updatedSum = currentSum + newFeedbackModel.estimation;

                                                                        targetDish.put("count", updatedCount);
                                                                        targetDish.put("sum", updatedSum);
                                                                    } else {
                                                                        Map<String, Object> newDish = new HashMap<>();
                                                                        newDish.put("count", 1);
                                                                        newDish.put("sum", newFeedbackModel.estimation);
                                                                        newDish.put("category", tag);
                                                                        newDish.put("imageUrl", imageUrlq);


                                                                        dishesMap.put(newFeedbackModel.dishName, newDish);
                                                                    }

                                                                    documentSnapshotq.getReference().update("allCount", allCount + 1);
                                                                    documentSnapshotq.getReference().update("allSum", allSum + newFeedbackModel.estimation);



                                                                    documentSnapshotq.getReference().update("dishes", dishesMap)
                                                                            .addOnSuccessListener(aV -> {

                                                                            })
                                                                            .addOnFailureListener(e -> {

                                                                            });
                                                                } else {
                                                                }
                                                            })
                                                            .addOnFailureListener(e -> {

                                                            });
                                                }
                                            } else {

                                            }
                                        }).addOnFailureListener(e -> {

                                        });




                                        Log.d(TAG, "Значение поля успешно обновлено");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Ошибка обновления значения поля", e);
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "Ошибка получения документов: " + task.getException());
                }
            }
        });


    }
}
