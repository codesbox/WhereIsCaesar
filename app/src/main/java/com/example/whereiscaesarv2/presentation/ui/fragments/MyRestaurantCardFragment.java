package com.example.whereiscaesarv2.presentation.ui.fragments;

import static android.content.ContentValues.TAG;
import static com.example.data.util.LogTags.DATABASE_ERROR;

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

import com.example.data.storages.models.RestaurantModelData;
import com.example.domain.listeners.DeleteDishListener;
import com.example.domain.listeners.DeleteRestaurantListener;
import com.example.domain.listeners.GetMyRestaurantListener;
import com.example.domain.models.DishModelDomain;
import com.example.domain.models.MapDishCard;
import com.example.domain.models.PointModel;
import com.example.domain.models.RestaurantModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentMyRestaurantCardBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.MyRestaurantDishesAdapter;
import com.example.whereiscaesarv2.presentation.ui.recycler.RestaurantDishesAdapter;
import com.example.whereiscaesarv2.presentation.util.listeners.RestaurantDishCardClickListener;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantCardFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantCardFragmentViewModelFactory;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SearchBSFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SearchBSFragmentViewModelFactory;
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

        MyRestaurantCardFragmentViewModel viewModel = new ViewModelProvider(this, new MyRestaurantCardFragmentViewModelFactory()).get(MyRestaurantCardFragmentViewModel.class);

        String name = getArguments().getString("name");
        String restaurantId = getArguments().getString("id");
        binding.restaurantName.setText(name);
        binding.goBack.setOnClickListener(v -> {

            NavHostFragment.findNavController(this).popBackStack();

        });
        binding.addDishBut.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            bundle.putString("restaurantName", restaurantId);

            NavHostFragment.findNavController(this).navigate(R.id.action_myRestaurantCardFragment_to_addDishFragment, bundle);
        });

        binding.managePointBut.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            bundle.putString("restaurantId", restaurantId);

            NavHostFragment.findNavController(this).navigate(R.id.action_myRestaurantCardFragment_to_myRestaurantsPointsFragment, bundle);
        });
        binding.goBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });

        DeleteDishListener deleteDishListener = new DeleteDishListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(requireContext(), "Блюдо удалено", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure() {
                Toast.makeText(requireContext(), "Неизвестная ошибка", Toast.LENGTH_SHORT).show();
            }
        };

        RestaurantDishCardClickListener listener = new RestaurantDishCardClickListener() {
            @Override
            public void onCardClick(MapDishCard mapDishCard) {
                viewModel.deleteDish(new DishModelDomain(mapDishCard.dishName, mapDishCard.imageUrl, true), restaurantId, deleteDishListener);
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

        GetMyRestaurantListener getMyRestaurantListener = new GetMyRestaurantListener() {
            @Override
            public void onSuccess(RestaurantModelDomain restaurantModelDomain) {

                List<MapDishCard> salads = new ArrayList<>();
                List<MapDishCard> hotDishes = new ArrayList<>();
                List<MapDishCard> soups = new ArrayList<>();
                List<MapDishCard> drinks = new ArrayList<>();
                List<MapDishCard> desserts = new ArrayList<>();
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
                }
                hotadapter.setItemList(hotDishes);
                salatsadapter.setItemList(salads);
                dessertadapter.setItemList(desserts);
                drinksadapter.setItemList(drinks);
                soupsadapter.setItemList(soups);

            }

            @Override
            public void onFailure() {
                Toast.makeText(requireContext(), "Неизвестная ошибка", Toast.LENGTH_SHORT).show();
            }
        };

        viewModel.getRestaurant(restaurantId, getMyRestaurantListener);


        binding.deleteRestaurantBut.setOnClickListener(v -> {

            DeleteRestaurantListener deleteRestaurantListener = new DeleteRestaurantListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(requireContext(), "Ресторан удален", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(MyRestaurantCardFragment.this).popBackStack(R.id.myRestaurantsFragment2, false);
                }

                @Override
                public void onFailure() {
                    Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                }
            };

            viewModel.deleteRestaurant(restaurantId, deleteRestaurantListener);

        });
    }
}