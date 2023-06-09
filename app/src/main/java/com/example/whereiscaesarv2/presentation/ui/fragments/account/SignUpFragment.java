package com.example.whereiscaesarv2.presentation.ui.fragments.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.domain.listeners.AddUserListener;
import com.example.domain.models.AddUserModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentSignUpBinding;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SearchBSFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SearchBSFragmentViewModelFactory;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SignUpViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.SignUpViewModelFactory;

public class SignUpFragment extends Fragment {

    private SignUpViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentSignUpBinding binding = FragmentSignUpBinding.bind(view);

        vm = new ViewModelProvider(this, new SignUpViewModelFactory()).get(SignUpViewModel.class);

        binding.signUpButton.setOnClickListener(v -> {

            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            String firstName = binding.firstName.getText().toString();
            String lastName = binding.lastName.getText().toString();

            AddUserModelDomain addUserModelDomain = new AddUserModelDomain(firstName, lastName, email, password);

            AddUserListener addUserListener = new AddUserListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(requireContext(), "Аккаунт успешно создан", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure() {

                    Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();

                }
            };

            vm.addUser(addUserModelDomain, addUserListener);

        });


    }
}