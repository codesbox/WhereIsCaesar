package com.example.whereiscaesarv2.presentation.ui.fragments;

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
import android.widget.Toast;

import com.example.domain.listeners.AddFeedbackListener;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentAddFeedbackBSBinding;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.AddFeedbackViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.AddFeedbackViewModelFactory;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyFeedbacksViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyFeedbacksViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFeedbackBSFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_feedback_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentAddFeedbackBSBinding binding = FragmentAddFeedbackBSBinding.bind(view);

        AddFeedbackViewModel viewModel = new ViewModelProvider(this, new AddFeedbackViewModelFactory()).get(AddFeedbackViewModel.class);

        String dishName = getArguments().getString("dishName");
        String restaurantName = getArguments().getString("restaurantName");
        String firstName = getArguments().getString("firstName");
        String lastName = getArguments().getString("lastName");
        String id = getArguments().getString("id");
        String userLevel = getArguments().getString("userLevel");
        binding.dish.setText(dishName);
        binding.rest.setText(restaurantName);

        AddFeedbackListener addFeedbackListener = new AddFeedbackListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(requireContext(), "Отзыв отправлен", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(AddFeedbackBSFragment.this).popBackStack();
            }

            @Override
            public void onFailure() {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            }
        };

        binding.sendFeedback.setOnClickListener(v -> {
            String feedback = binding.feedback.getText().toString();
            Integer estimation = Integer.valueOf(binding.estimation.getText().toString());
            viewModel.addFeedback(addFeedbackListener, id, dishName, feedback, restaurantName, estimation);




        });


    }
}