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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurateurProfileBSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurateurProfileBSFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestaurateurProfileBSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurateurProfileBSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurateurProfileBSFragment newInstance(String param1, String param2) {
        RestaurateurProfileBSFragment fragment = new RestaurateurProfileBSFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurateur_profile_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentRestaurateurProfileBSBinding binding = FragmentRestaurateurProfileBSBinding.bind(view);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
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

    }
}