package com.example.whereiscaesarv2.presentation.ui.fragments;

import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.domain.listeners.AddRestaurantListener;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentAddRestaurantBinding;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.AddRestaurantViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.AddRestaurantViewModelFactory;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantsViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantsViewModelFactory;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddRestaurantFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentAddRestaurantBinding binding = FragmentAddRestaurantBinding.bind(view);

        AddRestaurantViewModel viewModel = new ViewModelProvider(this, new AddRestaurantViewModelFactory()).get(AddRestaurantViewModel.class);

        String id = getArguments().getString("id");

        binding.signUpButton.setOnClickListener(v -> {

            String restaurantName = binding.firstNameRes.getText().toString();
            Double latitude = Double.valueOf(binding.lati.getText().toString());
            Double longitude = Double.valueOf(binding.longi.getText().toString());
            String address = binding.adress.getText().toString();

            AddRestaurantListener addRestaurantListener = new AddRestaurantListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                }
            };

            viewModel.addRestaurant(latitude, longitude, restaurantName, id, addRestaurantListener);
        });
    }
}