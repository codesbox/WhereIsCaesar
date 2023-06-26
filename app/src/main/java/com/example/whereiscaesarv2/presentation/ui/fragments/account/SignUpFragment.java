package com.example.whereiscaesarv2.presentation.ui.fragments.account;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
        isAuto = false;
        FragmentSignUpBinding binding = FragmentSignUpBinding.bind(view);

        vm = new ViewModelProvider(this, new SignUpViewModelFactory()).get(SignUpViewModel.class);
        binding.signUpButton.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                boolean isEmail = binding.email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
                boolean isPassword = binding.password.getText().toString().length() >= 6;

                boolean allFieldsFilled = !binding.email.getText().toString().isEmpty() &&
                        !binding.password.getText().toString().isEmpty() &&
                        !binding.firstName.getText().toString().isEmpty() &&
                        !binding.lastName.getText().toString().isEmpty() &&
                        isEmail && isPassword;
                binding.signUpButton.setEnabled(allFieldsFilled);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        binding.firstName.addTextChangedListener(textWatcher);
        binding.lastName.addTextChangedListener(textWatcher);
        binding.email.addTextChangedListener(textWatcher);
        binding.password.addTextChangedListener(textWatcher);

        binding.signUpButton.setOnClickListener(v -> {
            requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            binding.progressBar3.setVisibility(View.VISIBLE);

            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            String firstName = binding.firstName.getText().toString();
            String lastName = binding.lastName.getText().toString();

            AddUserModelDomain addUserModelDomain = new AddUserModelDomain(firstName, lastName, email, password);

            AddUserListener addUserListener = new AddUserListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(requireContext(), "Аккаунт успешно создан", Toast.LENGTH_SHORT).show();
                    binding.progressBar3.setVisibility(View.GONE);
                    requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onFailure() {

                    Toast.makeText(requireContext(), "Пользователь с таким email уже существует", Toast.LENGTH_SHORT).show();
                    binding.progressBar3.setVisibility(View.GONE);
                    requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
            };

            vm.addUser(addUserModelDomain, addUserListener);

        });


    }
}