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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.domain.models.DishModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentAddDishBinding;
import com.example.whereiscaesarv2.databinding.FragmentCategoryBSBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.SearchAdapter;
import com.example.whereiscaesarv2.presentation.util.listeners.SearchRecyclerClickListener;
import com.example.whereiscaesarv2.presentation.viewModels.sharedViewModels.SearchSharedViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SearchBSFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SearchBSFragmentViewModelFactory;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDishFragment extends Fragment {

    String categoryName;
    String imageUrl;
    Boolean isCategory;

    SearchAdapter adapter;
    SearchRecyclerClickListener listener;
    SearchBSFragmentViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_dish, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isAuto = false;
        FragmentAddDishBinding binding = FragmentAddDishBinding.bind(view);
        String name;
        String restaurantName = getArguments().getString("restaurantName");

        if (getArguments().getString("categoryName") != null){
            categoryName = getArguments().getString("categoryName");
            name = categoryName;
        }
        else{
            categoryName = "main";
            name = "Добавить блюдо";
        }

        binding.categoryNameTextView.setText(name);
        binding.popToBackStackButton.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
        binding.cancelButton.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack(R.id.myRestaurantCardFragment, false));

        vm = new ViewModelProvider(this, new SearchBSFragmentViewModelFactory(categoryName)).get(SearchBSFragmentViewModel.class);
        vm.getDishes().observe(getViewLifecycleOwner(), dishModelDomainList -> adapter.setItemList(dishModelDomainList));

        listener = new SearchRecyclerClickListener() {
            @Override
            public void onCardClick(DishModelDomain dishModelDomain) {
                if (dishModelDomain.isCategory){

                    Bundle bundle = new Bundle();
                    bundle.putString("categoryName", dishModelDomain.title);
                    bundle.putString("restaurantName", restaurantName);
                    bundle.putBoolean("isCategory", dishModelDomain.isCategory);
                    Navigation.findNavController(requireActivity(), R.id.mainFragmentContainerView).navigate(R.id.addDishFragment, bundle);

                }
                else {

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference collectionRef = db.collection("Категории и блюда");

                    collectionRef.document(dishModelDomain.title).get().addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            //List<Object> tags = documentSnapshot.get("tags", List.class);
                            String jsons = new Gson().toJson(documentSnapshot.get("tags"));
                            Type types = new TypeToken<List<Object>>(){}.getType();
                            List<Object> tags = new Gson().fromJson(jsons, types);
                            if (tags != null && tags.size() > 1) {
                                String tag = tags.get(1).toString();
                                String imageUrlq = documentSnapshot.getString("image");
                                CollectionReference restaurantsCollection = db.collection("Restaurants");


                                restaurantsCollection.document(restaurantName)
                                        .get()
                                        .addOnSuccessListener(documentSnapshotq -> {
                                            if (documentSnapshotq.exists()) {
                                                //Map<String, Object> dishesMap = documentSnapshot.get("dishes", Map.class);

                                                Integer allCount = documentSnapshotq.getLong("allCount").intValue();
                                                Integer allSum = documentSnapshotq.getLong("allSum").intValue();
                                                List<String> dishesNames = (List<String>) documentSnapshotq.get("dishesNames");


                                                String json = new Gson().toJson(documentSnapshotq.get("dishes"));
                                                Type type = new TypeToken<Map<String, Object>>(){}.getType();
                                                Map<String, Object> dishesMap = new Gson().fromJson(json, type);

                                                if (dishesMap != null && dishesMap.containsKey(dishModelDomain.title)) {
                                                    Toast.makeText(requireContext(), "Данное блюдо уже добавлено", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Map<String, Object> newDish = new HashMap<>();
                                                    newDish.put("count", 0);
                                                    newDish.put("sum", 0);
                                                    newDish.put("category", tag);
                                                    newDish.put("imageUrl", imageUrlq);
                                                    dishesMap.put(dishModelDomain.title, newDish);
                                                    dishesNames.add(dishModelDomain.title);
                                                    documentSnapshotq.getReference().update("dishesNames", dishesNames);
                                                    documentSnapshotq.getReference().update("dishes", dishesMap)
                                                            .addOnSuccessListener(aV -> {
                                                                Toast.makeText(requireContext(), "Блюдо добавлено", Toast.LENGTH_SHORT).show();
                                                                NavHostFragment.findNavController(AddDishFragment.this).popBackStack(R.id.myRestaurantCardFragment, false);

                                                            })
                                                            .addOnFailureListener(e -> {
                                                                Toast.makeText(requireContext(), "Ошибка4", Toast.LENGTH_SHORT).show();
                                                            });
                                                }

                                            } else {
                                            }
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(requireContext(), "Ошибка2", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        } else {
                            Toast.makeText(requireContext(), "Не найдена категория", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void progressBar(List<DishModelDomain> dishModelDomainList) {

                if (dishModelDomainList.size() != 0){
                    binding.progressBar2.setVisibility(View.GONE);
                }
                else {
                    binding.progressBar2.setVisibility(View.VISIBLE);
                }

            }
        };

        adapter = new SearchAdapter(getContext(), listener);
        RecyclerView recyclerView = binding.recyclerview;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}