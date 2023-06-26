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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeleteDishStorageImpl implements DeleteDishStorage{

    @Override
    public void deleteDish(DishModelDomain dishModelDomain, String restaurantName, DeleteDishListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference restaurantsRef = db.collection("Restaurants");

        restaurantsRef
                .whereEqualTo("name", restaurantName)
                .whereEqualTo("mainPoint", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                            String documentId = documentSnapshot.getId();


                            String json = new Gson().toJson(documentSnapshot.get("dishes"));
                            Type type = new TypeToken<Map<String, Object>>(){}.getType();
                            List<String> dishesNames = (List<String>) documentSnapshot.get("dishesNames");

                            Map<String, Object> dishesMap = new Gson().fromJson(json, type);

                            List<MapDishCard> dishMapList = new ArrayList<>();

                            for (Map.Entry<String, Object> entry : dishesMap.entrySet()){
                                Map<String, Object> dishValueMap = (Map<String, Object>) entry.getValue();
                                dishMapList.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), ((Double) dishValueMap.get("count")).intValue(), ((Double) dishValueMap.get("sum")).intValue(), (String) dishValueMap.get("category")));
                            }
                            String restaurantName = documentSnapshot.getString("name");



                            dishesMap.remove(dishModelDomain.title);
                            dishesNames.remove(dishModelDomain.title);


                            restaurantsRef.document(documentId).update("dishes", dishesMap, "dishesNames", dishesNames).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    listener.onSuccess();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    listener.onFailure();
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
