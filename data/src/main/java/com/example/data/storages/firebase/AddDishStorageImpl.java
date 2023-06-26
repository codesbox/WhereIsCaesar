package com.example.data.storages.firebase;

import android.widget.Toast;

import com.example.domain.listeners.AddDishListener;
import com.example.domain.models.DishModelDomain;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDishStorageImpl implements AddDishStorage{
    @Override
    public void addDish(DishModelDomain dishModelDomain, String restaurantName, AddDishListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Категории и блюда");

        collectionRef.document(dishModelDomain.title).get().addOnSuccessListener(documentSnapshot -> {
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
                                    List<String> dishesNames = (List<String>) documentSnapshotq.get("dishesNames");


                                    String json = new Gson().toJson(documentSnapshotq.get("dishes"));
                                    Type type = new TypeToken<Map<String, Object>>(){}.getType();
                                    Map<String, Object> dishesMap = new Gson().fromJson(json, type);

                                    if (dishesMap != null && dishesMap.containsKey(dishModelDomain.title)) {
                                        listener.onDishHasAlreadyBeenAdded();
                                    } else {
                                        Map<String, Object> newDish = new HashMap<>();
                                        newDish.put("count", 0);
                                        newDish.put("sum", 0);
                                        newDish.put("category", tag);
                                        newDish.put("imageUrl", imageUrlq);
                                        dishesMap.put(dishModelDomain.title, newDish);
                                        dishesNames.add(dishModelDomain.title);
                                        documentSnapshotq.getReference().update("dishesNames", dishesNames);
                                        documentSnapshotq.getReference().update("dishes", dishesMap)
                                                .addOnSuccessListener(aV -> {
                                                    listener.onSuccess();
                                                })
                                                .addOnFailureListener(e -> {
                                                    listener.onFailure();
                                                });
                                    }

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
        });

    }
}
