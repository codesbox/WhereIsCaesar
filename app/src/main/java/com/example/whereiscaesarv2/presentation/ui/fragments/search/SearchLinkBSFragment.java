package com.example.whereiscaesarv2.presentation.ui.fragments.search;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentSearchLinkBSBinding;
import com.example.whereiscaesarv2.presentation.viewModels.sharedViewModels.MapSharedViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;


public class SearchLinkBSFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_link_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentSearchLinkBSBinding binding = FragmentSearchLinkBSBinding.bind(view);
        MapSharedViewModel mapSharedViewModel = new ViewModelProvider(requireActivity(), (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(MapSharedViewModel.class);

        mapSharedViewModel.getBottomSheetBehavior().observe(getViewLifecycleOwner(), bottomSheetBehavior -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            bottomSheetBehavior.setPeekHeight(350);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setDraggable(false);
        });

        binding.searchBar.setOnClickListener(v -> {
            mapSharedViewModel.getBottomSheetBehavior().observe(getViewLifecycleOwner(), bottomSheetBehavior -> {
                bottomSheetBehavior.setDraggable(true);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            });
            NavHostFragment.findNavController(this).navigate(R.id.action_searchLinkBSFragment_to_searchBSFragment);
        });

        binding.floatingActionButton2.setOnClickListener(v -> {
            mapSharedViewModel.getBottomSheetBehavior().observe(getViewLifecycleOwner(), bottomSheetBehavior -> {
                bottomSheetBehavior.setDraggable(true);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            });
            NavHostFragment.findNavController(this).navigate(R.id.action_searchLinkBSFragment_to_account_graph);
        });
    }
}