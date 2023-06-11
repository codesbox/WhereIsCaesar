package com.example.whereiscaesarv2.presentation.ui.fragments;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.models.RestaurantModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentRestaurantsListBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.RestaurantsListAdapter;
import com.example.whereiscaesarv2.presentation.ui.recycler.SearchAdapter;
import com.example.whereiscaesarv2.presentation.util.listeners.RestaurantsListCardClickListener;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;

import java.util.List;


public class RestaurantsListFragment extends Fragment {
    private RestaurantsListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurants_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentRestaurantsListBinding binding = FragmentRestaurantsListBinding.bind(view);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(250);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        isAuto = false;

        RestaurantsListCardClickListener listener = new RestaurantsListCardClickListener() {
            @Override
            public void onCardClick(RestaurantModelDomain restaurantModelDomain) {

            }

            @Override
            public void progressBar(List<RestaurantModelDomain> restaurantModelDomainList) {

                if (restaurantModelDomainList.size() != 0){
                    binding.textView6.setVisibility(View.INVISIBLE);
                }
                else {
                    binding.textView6.setVisibility(View.VISIBLE);
                }

            }
        };

        adapter = new RestaurantsListAdapter(getContext(), listener);
        RecyclerView recyclerView = binding.recyclerviewqqq;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MainMapFragmentViewModel viewModel = new ViewModelProvider(requireActivity(), new MainMapFragmentViewModelFactory()).get(MainMapFragmentViewModel.class);

        viewModel.getRestaurants().observe(getViewLifecycleOwner(), restaurantModelDomainList -> {
            adapter.setItemList(restaurantModelDomainList);
        });



    }
}