package com.example.whereiscaesarv2.presentation.ui.fragments.account;

import static android.content.ContentValues.TAG;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentSignInBinding binding = FragmentSignInBinding.bind(view);
        binding.signInButton.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                boolean isEmail = binding.email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
                boolean isPassword = binding.password.getText().toString().length() >= 6;

                boolean allFieldsFilled = !binding.email.getText().toString().isEmpty() &&
                        !binding.password.getText().toString().isEmpty() &&
                        isEmail && isPassword;
                binding.signInButton.setEnabled(allFieldsFilled);

            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        binding.email.addTextChangedListener(textWatcher);
        binding.password.addTextChangedListener(textWatcher);

        mAuth = FirebaseAuth.getInstance();
        binding.signInButton.setOnClickListener(v -> {
            requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            binding.progressBar3.setVisibility(View.VISIBLE);

            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            Log.d(TAG, email + password);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                binding.progressBar3.setVisibility(View.GONE);
                                requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.mainMapFragment);
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                binding.progressBar3.setVisibility(View.GONE);
                                requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText(requireContext(), "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


        });
        binding.signUpButton.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.mainFragmentContainerView).navigate(R.id.signUpFragment2);

        });
    }
}