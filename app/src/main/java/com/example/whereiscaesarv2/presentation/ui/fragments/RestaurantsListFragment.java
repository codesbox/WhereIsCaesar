package com.example.whereiscaesarv2.presentation.ui.fragments;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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

import java.util.ArrayList;
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
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setDraggable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(400);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setHideable(false);
        isAuto = false;

        binding.cancelButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack(R.id.searchLinkBSFragment, false);
        });

        RestaurantsListCardClickListener listener = new RestaurantsListCardClickListener() {
            @Override
            public void onCardClick(RestaurantModelDomain restaurantModelDomain) {
                isAuto = true;
                bottomSheetBehavior.setHideable(true);
                bottomSheetBehavior.setDraggable(true);
                bottomSheetBehavior.setPeekHeight(0);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                Bundle bundle = new Bundle();
                bundle.putSerializable("restaurantCard", restaurantModelDomain);
                MainMapFragmentViewModel viewModel = new ViewModelProvider(requireActivity(), new MainMapFragmentViewModelFactory()).get(MainMapFragmentViewModel.class);
                bundle.putStringArrayList("selectedDishes", (ArrayList<String>) viewModel.getSelectedDishes().getValue());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavHostFragment.findNavController(RestaurantsListFragment.this).navigate(R.id.action_restaurantsListFragment_to_restaurantCardBSFragment, bundle);
                    }
                }, 100);

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