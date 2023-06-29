package com.example.whereiscaesarv2.presentation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
        String restaurantId = getArguments().getString("restaurantId");
        binding.dish.setText(dishName);
        binding.rest.setText(restaurantName);

        binding.sendFeedback.setEnabled(false);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Integer estimationNumber = Integer.valueOf(binding.estimation.getText().toString());
                boolean estimationCheck = false;
                if (estimationNumber < 11 && estimationNumber > 0){
                    estimationCheck = true;
                }

                boolean allFieldsFilled = !binding.feedback.getText().toString().isEmpty() &&
                        !binding.estimation.getText().toString().isEmpty() && estimationCheck;
                binding.sendFeedback.setEnabled(allFieldsFilled);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        binding.estimation.addTextChangedListener(textWatcher);
        binding.feedback.addTextChangedListener(textWatcher);




        AddFeedbackListener addFeedbackListener = new AddFeedbackListener() {
            @Override
            public void onSuccess() {
                binding.progressBar6.setVisibility(View.GONE);
                requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(requireContext(), "Отзыв отправлен", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(AddFeedbackBSFragment.this).popBackStack();
            }

            @Override
            public void onFailure() {
                binding.progressBar6.setVisibility(View.GONE);
                requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            }
        };

        binding.sendFeedback.setOnClickListener(v -> {
            binding.progressBar6.setVisibility(View.VISIBLE);
            requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            String feedback = binding.feedback.getText().toString();
            Integer estimation = Integer.valueOf(binding.estimation.getText().toString());
            viewModel.addFeedback(addFeedbackListener, id, dishName, feedback, restaurantName, estimation, restaurantId);




        });


    }
}