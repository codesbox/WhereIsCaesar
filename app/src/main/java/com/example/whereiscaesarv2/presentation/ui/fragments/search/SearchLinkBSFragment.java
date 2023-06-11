package com.example.whereiscaesarv2.presentation.ui.fragments.search;


import static com.example.whereiscaesarv2.presentation.app.App.isAuto;
import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentSearchLinkBSBinding;
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
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(350);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setDraggable(false);
        isAuto = false;

        binding.searchBar.setOnClickListener(v -> {
            bottomSheetBehavior.setDraggable(true);
            bottomSheetBehavior.setHideable(true);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            isAuto = true;
            NavHostFragment.findNavController(this).navigate(R.id.action_searchLinkBSFragment_to_searchBSFragment);
        });

        binding.floatingActionButton2.setOnClickListener(v -> {
            bottomSheetBehavior.setDraggable(true);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            isAuto = true;
            NavHostFragment.findNavController(this).navigate(R.id.action_searchLinkBSFragment_to_account_graph);
        });
    }
}