package com.example.whereiscaesarv2.presentation.ui.fragments;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;
import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.models.MapDishCard;
import com.example.domain.models.RestaurantModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentRestaurantCardBSBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.RestaurantDishesAdapter;
import com.example.whereiscaesarv2.presentation.util.listeners.RestaurantDishCardClickListener;
import com.example.whereiscaesarv2.presentation.viewModels.sharedViewModels.SearchSharedViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.RestaurantCardBSFragmentViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RestaurantCardBSFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant_card_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentRestaurantCardBSBinding binding = FragmentRestaurantCardBSBinding.bind(view);


        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setDraggable(true);
        bottomSheetBehavior.setPeekHeight(400);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        isAuto = false;
        binding.cancelButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(RestaurantCardBSFragment.this).popBackStack();
        });

        RestaurantCardBSFragmentViewModel vm = new ViewModelProvider(requireActivity(), (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(RestaurantCardBSFragmentViewModel.class);

        if (getArguments() != null){
            vm.setMutableLiveData((RestaurantModelDomain) getArguments().getSerializable("restaurantCard"));
            vm.setSelectedDishes(getArguments().getStringArrayList("selectedDishes"));
        }

        vm.getRestaurant().observe(getViewLifecycleOwner(), restaurantModelDomain -> {

            binding.restaurantName.setText(restaurantModelDomain.restaurantName);
            if (restaurantModelDomain.allSum == 0.0){
                binding.imageView3.setText("0.0");
            }
            else{
                double result = (double) restaurantModelDomain.allSum / restaurantModelDomain.allCount;
                DecimalFormat decimalFormat = new DecimalFormat("#0.0");
                String formattedResult = decimalFormat.format(result);
                binding.imageView3.setText(formattedResult);

            }

            List<MapDishCard> salads = new ArrayList<>();
            List<MapDishCard> hotDishes = new ArrayList<>();
            List<MapDishCard> soups = new ArrayList<>();
            List<MapDishCard> drinks = new ArrayList<>();
            List<MapDishCard> desserts = new ArrayList<>();
            List<MapDishCard> selects = new ArrayList<>();
            for (MapDishCard dishCard : restaurantModelDomain.dishNameList){
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
                if (vm.getSelectedDishes().getValue().contains(dishCard.dishName)){
                    selects.add(dishCard);
                }
            }

            RestaurantDishCardClickListener listener = new RestaurantDishCardClickListener() {
                @Override
                public void onCardClick(MapDishCard mapDishCard) {
                    isAuto = true;
                    bottomSheetBehavior.setHideable(true);
                    bottomSheetBehavior.setDraggable(true);
                    bottomSheetBehavior.setPeekHeight(0);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    Bundle bundle = new Bundle();
                    bundle.putString("restaurantName", vm.getRestaurant().getValue().restaurantName);
                    bundle.putSerializable("dishCard", mapDishCard);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NavHostFragment.findNavController(RestaurantCardBSFragment.this).navigate(R.id.action_restaurantCardBSFragment_to_dishBSFragment, bundle);

                        }
                    }, 100);


                }
            };

            RestaurantDishesAdapter hotadapter = new RestaurantDishesAdapter(getContext(), listener);
            RecyclerView hotrecyclerView = binding.hotDishRecyclerView;
            hotrecyclerView.setAdapter(hotadapter);
            hotrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            RestaurantDishesAdapter salatsadapter = new RestaurantDishesAdapter(getContext(), listener);
            RecyclerView salatrecyclerView = binding.saladsAndSnacksRecyclerView;
            salatrecyclerView.setAdapter(salatsadapter);
            salatrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            RestaurantDishesAdapter dessertadapter = new RestaurantDishesAdapter(getContext(), listener);
            RecyclerView dessertsrecyclerView = binding.dessertsRecyclerView;
            dessertsrecyclerView.setAdapter(dessertadapter);
            dessertsrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            RestaurantDishesAdapter drinksadapter = new RestaurantDishesAdapter(getContext(), listener);
            RecyclerView drinkrecyclerView = binding.drinksRecyclerView;
            drinkrecyclerView.setAdapter(drinksadapter);
            drinkrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            RestaurantDishesAdapter soupsadapter = new RestaurantDishesAdapter(getContext(), listener);
            RecyclerView souprecyclerView = binding.soupsAndBrothsRecyclerView;
            souprecyclerView.setAdapter(soupsadapter);
            souprecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            RestaurantDishesAdapter selectadapter = new RestaurantDishesAdapter(getContext(), listener);
            RecyclerView selectrecyclerView = binding.selectedRecyclerView;
            selectrecyclerView.setAdapter(selectadapter);
            selectrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            hotadapter.setItemList(hotDishes);
            salatsadapter.setItemList(salads);
            dessertadapter.setItemList(desserts);
            drinksadapter.setItemList(drinks);
            soupsadapter.setItemList(soups);
            selectadapter.setItemList(selects);


        });


    }


}