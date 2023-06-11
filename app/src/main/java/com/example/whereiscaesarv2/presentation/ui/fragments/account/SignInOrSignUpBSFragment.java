package com.example.whereiscaesarv2.presentation.ui.fragments.account;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentSignInOrSignUpBSBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class SignInOrSignUpBSFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in_or_sign_up_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        isAuto = false;
        FragmentSignInOrSignUpBSBinding binding = FragmentSignInOrSignUpBSBinding.bind(view);
        binding.button.setOnClickListener(v -> {

            Navigation.findNavController(requireActivity(), R.id.mainFragmentContainerView).navigate(R.id.signInFragment);

        });

    }
}