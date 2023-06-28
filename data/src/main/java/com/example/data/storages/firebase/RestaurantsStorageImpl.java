package com.example.data.storages.firebase;

import static com.example.data.util.LogTags.DATABASE_ERROR;

import android.graphics.Point;
import android.util.Log;

import com.example.data.storages.models.RestaurantModelData;
import com.example.data.storages.models.RestaurantPoint;
import com.example.domain.listeners.GetRestaurantsListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.models.MapDishCard;
import com.example.domain.models.PointModel;
import com.example.domain.models.RestaurantModelDomain;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RestaurantsStorageImpl implements RestaurantsStorage{

    @Override
    public void getRestaurants(GetRestaurantsListener listener, List<String> dishList, PointModel topLeftPoint, PointModel bottomRightPoint) {




        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference restaurantsRef = db.collection("RestaurantsPoints");

        List<Task<QuerySnapshot>> tasks = new ArrayList<>();



        Query query = restaurantsRef.whereLessThan("latitude", topLeftPoint.latitude)
                .whereGreaterThan("latitude", bottomRightPoint.latitude);
        Task<QuerySnapshot> task1 = query.get();
        tasks.add(task1);

        Query query1 = restaurantsRef.whereLessThan("longitude", bottomRightPoint.longitude)
                .whereGreaterThan("longitude", topLeftPoint.longitude);
        Task<QuerySnapshot> task2 = query1.get();
        tasks.add(task2);

        Tasks.whenAllSuccess(tasks).addOnCompleteListener(task -> {

            if(task.isSuccessful()){

                List<Object> results = task.getResult();

                List<DocumentSnapshot> combinedResults = new ArrayList<>();

                QuerySnapshot firstQuerySnapshot = (QuerySnapshot) results.get(0);
                combinedResults.addAll(firstQuerySnapshot.getDocuments());


                for (int i = 1; i < results.size(); i++) {
                    QuerySnapshot currentQuerySnapshot = (QuerySnapshot) results.get(i);
                    List<DocumentSnapshot> tempList = new ArrayList<>(currentQuerySnapshot.getDocuments());

                    for (int j = combinedResults.size() - 1; j >= 0; j--) {
                        DocumentSnapshot documentSnapshot = combinedResults.get(j);

                        if (!tempList.contains(documentSnapshot)) {
                            combinedResults.remove(j);
                        }
                    }
                }

                List<RestaurantModelData> restaurantModelDataList = new ArrayList<>();

                Map<String, List<RestaurantPoint>> pointsList = new HashMap<>();
                List<String> restaurantsId = new ArrayList<>();


                for (DocumentSnapshot documentSnapshot : combinedResults){



                    GeoPoint geoPoint = documentSnapshot.getGeoPoint("geoPoint");
                    double latitude = geoPoint.getLatitude();
                    double longitude = geoPoint.getLongitude();
                    PointModel point = new PointModel(latitude, longitude);

                    String restaurantId = documentSnapshot.getString("restaurantId");
                    String address = documentSnapshot.getString("address");



                    List<RestaurantPoint> list = pointsList.get(restaurantId);
                    if (list == null) {
                        list = new ArrayList<>();
                        pointsList.put(restaurantId, list);
                    }
                    list.add(new RestaurantPoint(address, point));
                    pointsList.put(restaurantId, list);
                    restaurantsId.add(restaurantId);

                }
                Log.d("CCCCCCCCCCCCCCCCCCCCCCCCC", String.valueOf(restaurantsId.isEmpty()));
                if (!restaurantsId.isEmpty()){

                    FirebaseFirestore db2 = FirebaseFirestore.getInstance();


                    CollectionReference restaurantsRef2 = db2.collection("Restaurants");

                    List<Task<QuerySnapshot>> tasks2 = new ArrayList<>();

                    for (String value : dishList) {
                        Task<QuerySnapshot> task10 = restaurantsRef2.whereArrayContains("dishesNames", value).get();
                        tasks2.add(task10);
                    }

                    Log.d("GHGHGHGHGHGHGHGHGG", String.valueOf(restaurantsId.size()));

                    Query query5 = restaurantsRef2.whereIn(FieldPath.documentId(), restaurantsId);
                    Task<QuerySnapshot> task20 = query5.get();
                    tasks2.add(task20);

                    Query query6 = restaurantsRef2.whereIn("status", Collections.singletonList("s"));
                    Task<QuerySnapshot> task21 = query6.get();
                    tasks2.add(task21);


                    Tasks.whenAllSuccess(tasks2).addOnCompleteListener(task3 -> {


                        if (task3.isSuccessful()){



                            List<Object> resultsq = task3.getResult();

                            List<DocumentSnapshot> combinedResultsq = new ArrayList<>();

                            QuerySnapshot firstQuerySnapshotq = (QuerySnapshot) resultsq.get(0);
                            combinedResultsq.addAll(firstQuerySnapshotq.getDocuments());

                            for (int i = 1; i < resultsq.size(); i++) {
                                QuerySnapshot currentQuerySnapshot = (QuerySnapshot) resultsq.get(i);
                                List<DocumentSnapshot> tempList = new ArrayList<>(currentQuerySnapshot.getDocuments());

                                for (int j = combinedResultsq.size() - 1; j >= 0; j--) {
                                    DocumentSnapshot documentSnapshot = combinedResultsq.get(j);

                                    if (!tempList.contains(documentSnapshot)) {
                                        combinedResultsq.remove(j);
                                    }
                                }
                            }


                            for (DocumentSnapshot documentSnapshot : combinedResultsq){


                                String documentId = documentSnapshot.getId();


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

                                List<RestaurantPoint> restaurantPointList = pointsList.get(documentId);

                                for (RestaurantPoint restaurantPoint : restaurantPointList){


                                    PointModel pointModel = new PointModel(restaurantPoint.pointModel.latitude, restaurantPoint.pointModel.longitude);
                                    String restaurantId = documentSnapshot.getId();
                                    RestaurantModelData restaurantModelData = new RestaurantModelData(restaurantName,
                                            userId ,allSum, allCount, pointModel, dishMapList, restaurantPoint.address, restaurantId);
                                    restaurantModelDataList.add(restaurantModelData);
                                }


                            }

                            List<RestaurantModelDomain> restaurantModelDomainList = new ArrayList<>();
                            for (RestaurantModelData restaurantModelData : restaurantModelDataList){

                                restaurantModelDomainList.add(new RestaurantModelDomain(restaurantModelData.restaurantName,
                                        restaurantModelData.userId, restaurantModelData.allSum, restaurantModelData.allCount, restaurantModelData.geoPoint,
                                        restaurantModelData.dishNameList, restaurantModelData.address, restaurantModelData.restaurantId));

                            }
                            listener.getRestaurants(restaurantModelDomainList);





                        }else{


                        }









                    });

                }
                else{
                    List<RestaurantModelDomain> restaurantModelDomainList = new ArrayList<>();
                    listener.getRestaurants(restaurantModelDomainList);
                }











            }
            else{
                Log.w(DATABASE_ERROR, "RestaurantsStorageImpl");
            }

        });

    }

}
