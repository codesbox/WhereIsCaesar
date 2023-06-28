package com.example.whereiscaesarv2.presentation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.domain.listeners.DeletePointListener;
import com.example.domain.listeners.GetPointsListener;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentMyRestaurantsPointsBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.RestaurantPointsAdapter;
import com.example.whereiscaesarv2.presentation.util.listeners.PointCardClickListener;
import com.example.domain.models.RestaurantPointModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantsPointsFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantsPointsFragmentViewModelFactory;

import java.util.List;


public class MyRestaurantsPointsFragment extends Fragment {

    RestaurantPointsAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_restaurants_points, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentMyRestaurantsPointsBinding binding = FragmentMyRestaurantsPointsBinding.bind(view);

        String restaurantId = getArguments().getString("restaurantId");

        MyRestaurantsPointsFragmentViewModel viewModel = new ViewModelProvider(this, new MyRestaurantsPointsFragmentViewModelFactory()).get(MyRestaurantsPointsFragmentViewModel.class);

        GetPointsListener getPointsListener = new GetPointsListener() {
            @Override
            public void onSuccess(List<RestaurantPointModel> restaurantPointModelList) {
                adapter.setItemList(restaurantPointModelList);
            }

            @Override
            public void onFailure() {

            }
        };
        viewModel.getPoints(restaurantId, getPointsListener);

        PointCardClickListener pointCardClickListener = new PointCardClickListener() {
            @Override
            public void onCardClick(RestaurantPointModel restaurantPointModel) {
                DeletePointListener deletePointListener = new DeletePointListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(requireContext(), "Точка удалена", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                    }
                };
                viewModel.deletePoint(restaurantPointModel, deletePointListener);
            }
        };

        binding.goBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(MyRestaurantsPointsFragment.this).popBackStack();
        });
        binding.addBut.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("restaurantId", restaurantId);
            NavHostFragment.findNavController(this).navigate(R.id.action_myRestaurantsPointsFragment_to_addPointFragment, bundle);
        });

        adapter = new RestaurantPointsAdapter(getContext(), pointCardClickListener);
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}