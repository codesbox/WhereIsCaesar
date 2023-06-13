package com.example.whereiscaesarv2.presentation.ui.fragments;

import static android.content.ContentValues.TAG;
import static com.example.data.util.LogTags.DATABASE_ERROR;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.data.storages.models.RestaurantModelData;
import com.example.domain.models.MapDishCard;
import com.example.domain.models.PointModel;
import com.example.domain.models.RestaurantModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentMyRestaurantCardBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.MyRestaurantDishesAdapter;
import com.example.whereiscaesarv2.presentation.ui.recycler.RestaurantDishesAdapter;
import com.example.whereiscaesarv2.presentation.util.listeners.RestaurantDishCardClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
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


public class MyRestaurantCardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_restaurant_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentMyRestaurantCardBinding binding = FragmentMyRestaurantCardBinding.bind(view);

        String name = getArguments().getString("name");
        binding.restaurantName.setText(name);
        binding.goBack.setOnClickListener(v -> {

            NavHostFragment.findNavController(this).popBackStack();

        });
        binding.addDishBut.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            bundle.putString("restaurantName", name);

            NavHostFragment.findNavController(this).navigate(R.id.action_myRestaurantCardFragment_to_addDishFragment, bundle);
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference restaurantsRef = db.collection("Restaurants");

        restaurantsRef
                .whereEqualTo("name", name)
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
                            List<String> nnn = (List<String>) documentSnapshot.get("dishesNames");

                            Map<String, Object> dishesMap = new Gson().fromJson(json, type);

                            List<MapDishCard> dishMapList = new ArrayList<>();

                            for (Map.Entry<String, Object> entry : dishesMap.entrySet()){
                                Map<String, Object> dishValueMap = (Map<String, Object>) entry.getValue();
                                dishMapList.add(new MapDishCard(entry.getKey(), (String) dishValueMap.get("imageUrl"), ((Double) dishValueMap.get("count")).intValue(), ((Double) dishValueMap.get("sum")).intValue(), (String) dishValueMap.get("category")));
                            }

                            List<MapDishCard> salads = new ArrayList<>();
                            List<MapDishCard> hotDishes = new ArrayList<>();
                            List<MapDishCard> soups = new ArrayList<>();
                            List<MapDishCard> drinks = new ArrayList<>();
                            List<MapDishCard> desserts = new ArrayList<>();
                            for (MapDishCard dishCard : dishMapList){
                                if (dishCard.category.equals("Горячие блюда")){
                                    hotDishes.add(dishCard);
                                }
                                if (dishCard.category.equals("Салаты и закуски")){
                                    salads.add(dishCard);
                                }
                                if (dishCard.category.equals("Десерты и выпечка")){
                                    desserts.add(dishCard);
                                }
                                if (dishCard.category.equals("Напитки")){
                                    drinks.add(dishCard);
                                }
                                if (dishCard.category.equals("Супы и бульоны")){
                                    soups.add(dishCard);
                                }
                            }

                            RestaurantDishCardClickListener listener = new RestaurantDishCardClickListener() {
                                @Override
                                public void onCardClick(MapDishCard mapDishCard) {


                                        dishesMap.remove(mapDishCard.dishName);
                                        nnn.remove("солянка");


                                        restaurantsRef.document(documentId).update("dishes", dishesMap, "dishesNames", nnn).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(requireContext(), "Блюдо удалено", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                }
                            };



                            MyRestaurantDishesAdapter hotadapter = new MyRestaurantDishesAdapter(getContext(), listener);
                            RecyclerView hotrecyclerView = binding.hotDishRecyclerView;
                            hotrecyclerView.setAdapter(hotadapter);
                            hotrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            MyRestaurantDishesAdapter salatsadapter = new MyRestaurantDishesAdapter(getContext(), listener);
                            RecyclerView salatrecyclerView = binding.saladsAndSnacksRecyclerView;
                            salatrecyclerView.setAdapter(salatsadapter);
                            salatrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            MyRestaurantDishesAdapter dessertadapter = new MyRestaurantDishesAdapter(getContext(), listener);
                            RecyclerView dessertsrecyclerView = binding.dessertsRecyclerView;
                            dessertsrecyclerView.setAdapter(dessertadapter);
                            dessertsrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            MyRestaurantDishesAdapter drinksadapter = new MyRestaurantDishesAdapter(getContext(), listener);
                            RecyclerView drinkrecyclerView = binding.drinksRecyclerView;
                            drinkrecyclerView.setAdapter(drinksadapter);
                            drinkrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            MyRestaurantDishesAdapter soupsadapter = new MyRestaurantDishesAdapter(getContext(), listener);
                            RecyclerView souprecyclerView = binding.soupsAndBrothsRecyclerView;
                            souprecyclerView.setAdapter(soupsadapter);
                            souprecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            hotadapter.setItemList(hotDishes);
                            salatsadapter.setItemList(salads);
                            dessertadapter.setItemList(desserts);
                            drinksadapter.setItemList(drinks);
                            soupsadapter.setItemList(soups);


                        } else {
                            Log.d(TAG, "Ошибка получения документов: ", task.getException());
                        }
                    }
                });
    }
}