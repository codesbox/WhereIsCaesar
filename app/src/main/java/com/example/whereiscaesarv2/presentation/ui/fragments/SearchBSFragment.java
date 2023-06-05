package com.example.whereiscaesarv2.presentation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentSearchBSBinding;
import com.example.whereiscaesarv2.presentation.viewModels.MapSharedViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class SearchBSFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentSearchBSBinding binding = FragmentSearchBSBinding.bind(view);
        MapSharedViewModel mapSharedViewModel = new ViewModelProvider((ViewModelStoreOwner) requireActivity(), (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MapSharedViewModel.class);
        mapSharedViewModel.getBottomSheetBehavior().observe(getViewLifecycleOwner(), bottomSheetBehavior -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        });
        binding.button3.setOnClickListener(v -> {

            NavHostFragment.findNavController(this).popBackStack(R.id.searchLinkBSFragment, false);


        });
    }
}