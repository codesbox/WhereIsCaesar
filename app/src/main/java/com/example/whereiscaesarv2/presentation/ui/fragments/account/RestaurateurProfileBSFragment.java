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
import com.example.whereiscaesarv2.databinding.FragmentRestaurateurProfileBSBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;

public class RestaurateurProfileBSFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurateur_profile_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentRestaurateurProfileBSBinding binding = FragmentRestaurateurProfileBSBinding.bind(view);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setDraggable(true);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        isAuto = false;

        AccountModelDomain accountModelDomain = (AccountModelDomain) getArguments().getSerializable("account");
        binding.userName.setText(String.format("Здравствуйте, %s", accountModelDomain.firstName));

        binding.addRes.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", accountModelDomain.id);
            Navigation.findNavController(requireActivity(), R.id.mainFragmentContainerView).navigate(R.id.action_mainMapFragment_to_addRestaurantFragment2, bundle);
        });
        binding.myRestaurants.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", accountModelDomain.id);
            Navigation.findNavController(requireActivity(), R.id.mainFragmentContainerView).navigate(R.id.action_mainMapFragment_to_myRestaurantsFragment2, bundle);

        });
        binding.signOut.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();
            Navigation.findNavController(requireActivity(), R.id.containerBottomSheet).popBackStack(R.id.account_graph, false);
        });

    }
}