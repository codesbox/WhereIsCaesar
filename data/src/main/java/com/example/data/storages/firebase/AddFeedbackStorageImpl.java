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
    public void addFeedBack(AddFeedbackListener listener, String id, String dishName, String feedback, String restaurantName, Integer estimation) {

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



                                            CollectionReference collectionRef = db.collection("Категории и блюда");

                                            collectionRef.document(dishName).get().addOnSuccessListener(documentSnapshot -> {
                                                if (documentSnapshot.exists()) {
                                                    //List<Object> tags = documentSnapshot.get("tags", List.class);
                                                    String jsons = new Gson().toJson(documentSnapshot.get("tags"));
                                                    Type types = new TypeToken<List<Object>>(){}.getType();
                                                    List<Object> tags = new Gson().fromJson(jsons, types);
                                                    if (tags != null && tags.size() > 1) {
                                                        String tag = tags.get(1).toString();
                                                        String imageUrlq = documentSnapshot.getString("image");
                                                        CollectionReference restaurantsCollection = db.collection("Restaurants");


                                                        restaurantsCollection.document(restaurantName)
                                                                .get()
                                                                .addOnSuccessListener(documentSnapshotq -> {
                                                                    if (documentSnapshotq.exists()) {
                                                                        //Map<String, Object> dishesMap = documentSnapshot.get("dishes", Map.class);

                                                                        Integer allCount = documentSnapshotq.getLong("allCount").intValue();
                                                                        Integer allSum = documentSnapshotq.getLong("allSum").intValue();


                                                                        String json = new Gson().toJson(documentSnapshotq.get("dishes"));
                                                                        Type type = new TypeToken<Map<String, Object>>(){}.getType();
                                                                        Map<String, Object> dishesMap = new Gson().fromJson(json, type);

                                                                        if (dishesMap != null && dishesMap.containsKey(dishName)) {
                                                                            Map<String, Object> targetDish = (Map<String, Object>) dishesMap.get(dishName);
                                                                            double currentCountDouble = ((Number) targetDish.get("count")).doubleValue();
                                                                            double currentSumDouble = ((Number) targetDish.get("sum")).doubleValue();
                                                                            int currentCount = (int) currentCountDouble;
                                                                            int currentSum = (int) currentSumDouble;

                                                                            int updatedCount = currentCount + 1;
                                                                            int updatedSum = currentSum + estimation;

                                                                            targetDish.put("count", updatedCount);
                                                                            targetDish.put("sum", updatedSum);
                                                                        } else {
                                                                            Map<String, Object> newDish = new HashMap<>();
                                                                            newDish.put("count", 1);
                                                                            newDish.put("sum", estimation);
                                                                            newDish.put("category", tag);
                                                                            newDish.put("imageUrl", imageUrlq);


                                                                            dishesMap.put(dishName, newDish);
                                                                        }

                                                                        documentSnapshotq.getReference().update("allCount", allCount + 1);
                                                                        documentSnapshotq.getReference().update("allSum", allSum + estimation);



                                                                        documentSnapshotq.getReference().update("dishes", dishesMap)
                                                                                .addOnSuccessListener(aV -> {
                                                                                    listener.onSuccess();
                                                                                })
                                                                                .addOnFailureListener(e -> {
                                                                                    listener.onFailure();
                                                                                });
                                                                    } else {
                                                                    }
                                                                })
                                                                .addOnFailureListener(e -> {
                                                                    listener.onFailure();
                                                                });
                                                    }
                                                } else {
                                                    listener.onFailure();
                                                }
                                            }).addOnFailureListener(e -> {
                                                listener.onFailure();
                                            });







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
