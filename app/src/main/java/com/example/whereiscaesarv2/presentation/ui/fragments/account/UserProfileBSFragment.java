package com.example.whereiscaesarv2.presentation.ui.fragments.account;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;
import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.models.AccountModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentUserProfileBSBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileBSFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        isAuto = false;

        FragmentUserProfileBSBinding binding = FragmentUserProfileBSBinding.bind(view);
        AccountModelDomain accountModelDomain = (AccountModelDomain) getArguments().getSerializable("account");

        binding.userName.setText(String.format("Здравствуйте, %s", accountModelDomain.firstName));
        binding.userCount.setText(String.format("Критик еды %s уровня", accountModelDomain.feedBackCount.toString()));
        binding.signOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Navigation.findNavController(requireActivity(), R.id.containerBottomSheet).popBackStack(R.id.account_graph, false);
        });
        binding.myFeedbacks.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.mainFragmentContainerView).navigate(R.id.action_mainMapFragment_to_myFeedbacksFragment2);
        });
    }
}