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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.domain.listeners.AddDishListener;
import com.example.domain.models.DishModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentAddDishBinding;
import com.example.whereiscaesarv2.databinding.FragmentCategoryBSBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.SearchAdapter;
import com.example.whereiscaesarv2.presentation.util.listeners.SearchRecyclerClickListener;
import com.example.whereiscaesarv2.presentation.viewModels.sharedViewModels.SearchSharedViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.AddDishFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.AddDishFragmentViewModelFactory;
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
                    requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    binding.progressBar2.setVisibility(View.VISIBLE);
                    AddDishListener addDishListener = new AddDishListener() {
                        @Override
                        public void onSuccess() {
                            requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            binding.progressBar2.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), "Блюдо добавлено", Toast.LENGTH_SHORT).show();
                            NavHostFragment.findNavController(AddDishFragment.this).popBackStack(R.id.myRestaurantCardFragment, false);
                        }

                        @Override
                        public void onFailure() {
                            requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            binding.progressBar2.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), "Неизвестная ошибка", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onDishHasAlreadyBeenAdded() {
                            requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            binding.progressBar2.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), "Данное блюдо уже добавлено", Toast.LENGTH_SHORT).show();
                        }
                    };

                    AddDishFragmentViewModel viewModel = new ViewModelProvider(AddDishFragment.this, new AddDishFragmentViewModelFactory()).get(AddDishFragmentViewModel.class);
                    viewModel.addDish(dishModelDomain, restaurantName, addDishListener);

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