package com.example.data.storages.firebase;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.data.storages.models.RestaurantModelData;
import com.example.domain.listeners.DeleteDishListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.models.MapDishCard;
import com.example.domain.models.PointModel;
import com.example.domain.models.RestaurantModelDomain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeleteDishStorageImpl implements DeleteDishStorage{

    @Override
    public void deleteDish(DishModelDomain dishModelDomain, String restaurantId, DeleteDishListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference restaurantsRef = db.collection("Restaurants");

        restaurantsRef
                .whereEqualTo(FieldPath.documentId(), restaurantId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                            String documentId = documentSnapshot.getId();

                            Integer allSum = documentSnapshot.getDouble("allSum").intValue();
                            Integer allCount = documentSnapshot.getDouble("allCount").intValue();


                            String json = new Gson().toJson(documentSnapshot.get("dishes"));
                            Type type = new TypeToken<Map<String, Object>>(){}.getType();
                            List<String> dishesNames = (List<String>) documentSnapshot.get("dishesNames");

                            Map<String, Object> dishesMap = new Gson().fromJson(json, type);

                            List<MapDishCard> dishMapList = new ArrayList<>();

                            Integer sum = 0;
                            Integer count = 0;

                            for (Map.Entry<String, Object> entry : dishesMap.entrySet()){
                                Map<String, Object> dishValueMap = (Map<String, Object>) entry.getValue();
                                dishMapList.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), ((Double) dishValueMap.get("count")).intValue(), ((Double) dishValueMap.get("sum")).intValue(), (String) dishValueMap.get("category")));
                                if (entry.getKey().equals(dishModelDomain.title)){
                                    sum = ((Double) dishValueMap.get("sum")).intValue();
                                    count = ((Double) dishValueMap.get("count")).intValue();
                                }
                            }
                            String restaurantName = documentSnapshot.getString("name");

                            allSum -= sum;
                            allCount -= count;




                            dishesMap.remove(dishModelDomain.title);
                            dishesNames.remove(dishModelDomain.title);



                            restaurantsRef.document(documentId).update("dishes", dishesMap, "dishesNames", dishesNames, "allSum", allSum, "allCount", allCount).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                                    CollectionReference feedbacksRef = db.collection("Feedbacks");

                                    Query query = feedbacksRef.whereEqualTo("dishName", dishModelDomain.title).whereEqualTo("restaurantId", restaurantId);

                                    query.get().addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
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


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });








                        }
                        else {
                            Log.d(TAG, "Ошибка получения документов: ", task.getException());
                            listener.onFailure();
                        }
                    }
                });

    }

}
