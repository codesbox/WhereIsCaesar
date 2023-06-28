package com.example.whereiscaesarv2.presentation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.domain.listeners.GetModeratorRestaurantsListener;
import com.example.domain.models.NewRestaurantModel;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentNewRestaurantsBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.MyRestaurantDishesAdapter;
import com.example.whereiscaesarv2.presentation.ui.recycler.NewRestaurantsAdapter;
import com.example.whereiscaesarv2.presentation.util.listeners.NewRestaurantsListener;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantCardFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantCardFragmentViewModelFactory;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.NewRestaurantsFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.NewRestaurantsFragmentViewModelFactory;

import java.util.List;

public class NewRestaurantsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_restaurants, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentNewRestaurantsBinding binding = FragmentNewRestaurantsBinding.bind(view);

        NewRestaurantsFragmentViewModel viewModel = new ViewModelProvider(this, new NewRestaurantsFragmentViewModelFactory()).get(NewRestaurantsFragmentViewModel.class);

        binding.goBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });



        NewRestaurantsListener newRestaurantsListener = new NewRestaurantsListener() {
            @Override
            public void reject(NewRestaurantModel newRestaurantModel) {
                viewModel.reject(newRestaurantModel.restaurantId);
            }

            @Override
            public void approve(NewRestaurantModel newRestaurantModel) {
                viewModel.approve(newRestaurantModel.restaurantId);
            }
        };

        NewRestaurantsAdapter adapter = new NewRestaurantsAdapter(getContext(), newRestaurantsListener);
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        GetModeratorRestaurantsListener getModeratorRestaurantsListener = new GetModeratorRestaurantsListener() {
            @Override
            public void onSuccess(List<NewRestaurantModel> newRestaurantModelList) {
                adapter.setItemList(newRestaurantModelList);
            }

            @Override
            public void onFailure() {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            }
        };
        viewModel.getRestaurants(getModeratorRestaurantsListener);
    }
}