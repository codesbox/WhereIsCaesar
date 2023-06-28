package com.example.whereiscaesarv2.presentation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.domain.listeners.GetModeratorFeedbacksListener;
import com.example.domain.listeners.GetModeratorRestaurantsListener;
import com.example.domain.models.NewFeedbackModel;
import com.example.domain.models.NewRestaurantModel;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentNewFeedbacksBinding;
import com.example.whereiscaesarv2.databinding.FragmentNewRestaurantsBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.NewFeedbackAdapter;
import com.example.whereiscaesarv2.presentation.ui.recycler.NewRestaurantsAdapter;
import com.example.whereiscaesarv2.presentation.util.listeners.NewFeedbacksListener;
import com.example.whereiscaesarv2.presentation.util.listeners.NewRestaurantsListener;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.NewFeedbacksViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.NewFeedbacksViewModelFactory;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.NewRestaurantsFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.NewRestaurantsFragmentViewModelFactory;

import java.util.List;

public class NewFeedbacksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_feedbacks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentNewFeedbacksBinding binding = FragmentNewFeedbacksBinding.bind(view);

        binding.goBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });


        NewFeedbacksViewModel viewModel = new ViewModelProvider(this, new NewFeedbacksViewModelFactory()).get(NewFeedbacksViewModel.class);

        NewFeedbacksListener newFeedbacksListener = new NewFeedbacksListener() {
            @Override
            public void reject(NewFeedbackModel newFeedbackModel) {
                viewModel.reject(newFeedbackModel.feedbackId);
            }

            @Override
            public void approve(NewFeedbackModel newFeedbackModel) {
                viewModel.approve(newFeedbackModel);
            }
        };

        NewFeedbackAdapter adapter = new NewFeedbackAdapter(getContext(), newFeedbacksListener);
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        GetModeratorFeedbacksListener getModeratorFeedbacksListener = new GetModeratorFeedbacksListener() {
            @Override
            public void onSuccess(List<NewFeedbackModel> newFeedbackModels) {
                adapter.setItemList(newFeedbackModels);
            }

            @Override
            public void onFailure() {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            }
        };
        viewModel.getFeedbacks(getModeratorFeedbacksListener);
    }



}