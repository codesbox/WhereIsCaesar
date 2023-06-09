package com.example.whereiscaesarv2.presentation.ui.fragments.search;

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

import com.bumptech.glide.Glide;
import com.example.domain.models.DishModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentCategoryBSBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.SearchAdapter;
import com.example.whereiscaesarv2.presentation.util.listeners.SearchRecyclerClickListener;
import com.example.whereiscaesarv2.presentation.viewModels.sharedViewModels.SearchSharedViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SearchBSFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SearchBSFragmentViewModelFactory;

import java.util.List;

public class CategoryBSFragment extends Fragment {

    String categoryName;
    String imageUrl;
    Boolean isCategory;

    SearchAdapter adapter;
    SearchRecyclerClickListener listener;
    SearchBSFragmentViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentCategoryBSBinding binding = FragmentCategoryBSBinding.bind(view);

        assert getArguments() != null;
        categoryName = getArguments().getString("categoryName");
        imageUrl = getArguments().getString("imageUrl");
        isCategory = getArguments().getBoolean("isCategory");
        Glide.with(requireContext())
                .load(imageUrl)
                .error(R.drawable.image_not_supported)
                .into(binding.topImage);
        binding.topCategoryName.setText(categoryName);
        binding.categoryNameTextView.setText(categoryName);
        binding.popToBackStackButton.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
        binding.cancelButton.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack(R.id.mainMapFragment, false));

        vm = new ViewModelProvider(this, new SearchBSFragmentViewModelFactory(categoryName)).get(SearchBSFragmentViewModel.class);
        vm.getDishes().observe(getViewLifecycleOwner(), dishModelDomainList -> adapter.setItemList(dishModelDomainList));

        listener = new SearchRecyclerClickListener() {
            @Override
            public void onCardClick(DishModelDomain dishModelDomain) {
                if (dishModelDomain.isCategory){

                    Bundle bundle = new Bundle();
                    bundle.putString("categoryName", dishModelDomain.title);
                    bundle.putString("imageUrl", dishModelDomain.imageUrl);
                    bundle.putBoolean("isCategory", dishModelDomain.isCategory);
                    Navigation.findNavController(requireActivity(), R.id.mainFragmentContainerView).navigate(R.id.categoryBSFragment, bundle);

                }
                else {

                    SearchSharedViewModel searchSharedViewModel = new ViewModelProvider(requireActivity(), (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(SearchSharedViewModel.class);
                    searchSharedViewModel.addElement(dishModelDomain);
                    Navigation.findNavController(requireActivity(), R.id.mainFragmentContainerView).popBackStack(R.id.mainMapFragment, false);

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