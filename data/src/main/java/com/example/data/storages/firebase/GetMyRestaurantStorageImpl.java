package com.example.data.storages.firebase;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.storages.models.RestaurantModelData;
import com.example.domain.listeners.GetMyRestaurantListener;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetMyRestaurantStorageImpl implements GetMyRestaurantStorage{
    @Override
    public void GetMyRestaurant(String restaurantName, GetMyRestaurantListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference restaurantsRef = db.collection("Restaurants");

        restaurantsRef
                .whereEqualTo(FieldPath.documentId(), restaurantName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            GeoPoint geoPoint = documentSnapshot.getGeoPoint("geoPoint");
                            double latitude = 0;
                            double longitude = 0;

                            PointModel point = new PointModel(latitude, longitude);

                            String json = new Gson().toJson(documentSnapshot.get("dishes"));
                            Type type = new TypeToken<Map<String, Object>>(){}.getType();

                            Map<String, Object> dishesMap = new Gson().fromJson(json, type);

                            List<MapDishCard> dishMapList = new ArrayList<>();

                            for (Map.Entry<String, Object> entry : dishesMap.entrySet()){
                                Map<String, Object> dishValueMap = (Map<String, Object>) entry.getValue();
                                dishMapList.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), ((Double) dishValueMap.get("count")).intValue(), ((Double) dishValueMap.get("sum")).intValue(), (String) dishValueMap.get("category")));
                            }

                            Integer allSum = documentSnapshot.getDouble("allSum").intValue();

                            Integer allCount = documentSnapshot.getDouble("allCount").intValue();

                            String userId = documentSnapshot.getString("userId");
                            String restaurantName = documentSnapshot.getString("name");
                            String address = documentSnapshot.getString("address");
                            String restaurantId = documentSnapshot.getId();

                            RestaurantModelData restaurantModelData = new RestaurantModelData(restaurantName,
                                    userId ,allSum, allCount, point, dishMapList, address, restaurantId);
                            RestaurantModelDomain restaurantModelDomain = new RestaurantModelDomain(restaurantModelData.restaurantName,
                                    restaurantModelData.userId, restaurantModelData.allSum, restaurantModelData.allCount,
                                    restaurantModelData.geoPoint, restaurantModelData.dishNameList, restaurantModelData.address, restaurantModelData.restaurantId);
                            listener.onSuccess(restaurantModelDomain);
                        }
                        else {
                            Log.d(TAG, "Ошибка получения документов: ", task.getException());
                            listener.onFailure();
                        }
                    }
                });

    }
}
