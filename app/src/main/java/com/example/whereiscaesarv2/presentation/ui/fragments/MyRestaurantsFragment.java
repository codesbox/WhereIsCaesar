package com.example.whereiscaesarv2.presentation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentMyRestaurantsBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.MyRestaurantsAdapter;
import com.example.whereiscaesarv2.presentation.ui.recycler.SearchAdapter;
import com.example.whereiscaesarv2.presentation.util.listeners.MyRestaurantCardClickListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyRestaurantsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_restaurants, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentMyRestaurantsBinding binding = FragmentMyRestaurantsBinding.bind(view);

        String userid = getArguments().getString("id");

        MyRestaurantCardClickListener listener = new MyRestaurantCardClickListener() {
            @Override
            public void onCardClick(String restaurant) {

            }
        };

        List<String> restaurantsList = new ArrayList<>();

        MyRestaurantsAdapter adapter = new MyRestaurantsAdapter(getContext(), listener);
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference restaurantsRef = db.collection("Restaurants");

        // Создание запроса для фильтрации документов
        Query query = restaurantsRef.whereEqualTo("userId", userid)
                .whereEqualTo("mainPoint", true);

        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {

                                restaurantsList.add(document.getString("name"));

                            }
                            adapter.setRestaurants(restaurantsList);
                        }
                    } else {
                    }
                });



    }
}