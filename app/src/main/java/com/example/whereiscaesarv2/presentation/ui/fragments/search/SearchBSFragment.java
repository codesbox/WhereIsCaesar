package com.example.whereiscaesarv2.presentation.ui.fragments.search;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;
import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.domain.models.DishModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentSearchBSBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.RequestSearchAdapter;
import com.example.whereiscaesarv2.presentation.ui.recycler.SearchAdapter;
import com.example.whereiscaesarv2.presentation.util.listeners.SearchRecyclerClickListener;
import com.example.whereiscaesarv2.presentation.viewModels.sharedViewModels.SearchSharedViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModelFactory;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SearchBSFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SearchBSFragmentViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class SearchBSFragment extends Fragment {

    SearchBSFragmentViewModel vm;
    SearchAdapter adapter;
    RequestSearchAdapter requestAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentSearchBSBinding binding = FragmentSearchBSBinding.bind(view);

        SearchSharedViewModel searchSharedViewModel = new ViewModelProvider(requireActivity(), (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(SearchSharedViewModel.class);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        isAuto = false;

        SearchRecyclerClickListener listener = new SearchRecyclerClickListener() {
            @Override
            public void onCardClick(DishModelDomain dishModelDomain) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryName", dishModelDomain.title);
                bundle.putString("imageUrl", dishModelDomain.imageUrl);
                bundle.putBoolean("isCategory", dishModelDomain.isCategory);
                Navigation.findNavController(requireActivity(), R.id.mainFragmentContainerView).navigate(R.id.categoryBSFragment, bundle);

            }

            @Override
            public void progressBar(List<DishModelDomain> dishModelDomainList) {
                if (dishModelDomainList.size() != 0){
                    binding.progressBar.setVisibility(View.GONE);
                }
                else{
                    binding.progressBar.setVisibility(View.VISIBLE);
                }
            }
        };

        SearchRecyclerClickListener requestListener = new SearchRecyclerClickListener() {
            @Override
            public void onCardClick(DishModelDomain dishModelDomain) {
                requestAdapter.deleteItem(dishModelDomain);
            }

            @Override
            public void progressBar(List<DishModelDomain> dishModelDomainList) {
                if (dishModelDomainList.size() == 0){
                    binding.noDishes.setVisibility(View.VISIBLE);
                    binding.resultRecyclerview.setVisibility(View.GONE);
                }
                else{
                    binding.noDishes.setVisibility(View.GONE);
                    binding.resultRecyclerview.setVisibility(View.VISIBLE);
                }
            }
        };

        vm = new ViewModelProvider(this, new SearchBSFragmentViewModelFactory("main")).get(SearchBSFragmentViewModel.class);

        adapter = new SearchAdapter(getContext(), listener);
        RecyclerView recyclerView = binding.recyclerview;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        requestAdapter = new RequestSearchAdapter(getContext(), requestListener);
        RecyclerView requestRecyclerView = binding.resultRecyclerview;
        requestRecyclerView.setAdapter(requestAdapter);
        requestRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        vm.getDishes().observe(getViewLifecycleOwner(), dishModelDomainList -> adapter.setItemList(dishModelDomainList));
        searchSharedViewModel.getData().observe(getViewLifecycleOwner(), dishModelDomainList -> requestAdapter.setItemList(dishModelDomainList));

        binding.button3.setOnClickListener(v -> {

            MainMapFragmentViewModel mapFragmentViewModel = new ViewModelProvider(requireActivity(), new MainMapFragmentViewModelFactory()).get(MainMapFragmentViewModel.class);
            List<String> dishNames = new ArrayList<>();
            List<DishModelDomain> dishModelDomainList = searchSharedViewModel.getData().getValue();
            for (DishModelDomain dishModelDomain : dishModelDomainList){
                dishNames.add(dishModelDomain.title);
            }
            mapFragmentViewModel.setSelectedDishes(dishNames);
            isAuto = true;
            bottomSheetBehavior.setHideable(true);
            bottomSheetBehavior.setDraggable(true);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    NavHostFragment.findNavController(SearchBSFragment.this).navigate(R.id.action_searchBSFragment_to_restaurantsListFragment);

                }
            }, 100);




        });
    }
}