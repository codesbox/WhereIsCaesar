package com.example.whereiscaesarv2.presentation.ui.fragments.account;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;
import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.data.repositories.AccountRepositoryImpl;
import com.example.data.storages.firebase.AccountStorage;
import com.example.data.storages.firebase.AccountStorageImpl;
import com.example.domain.listeners.AccountListener;
import com.example.domain.repository.AccountRepository;
import com.example.domain.useCases.GetAccountDataUseCase;
import com.example.whereiscaesarv2.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashAccountBSFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_account_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        isAuto = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){

            AccountStorage accountStorage = new AccountStorageImpl();
            AccountRepository accountRepository = new AccountRepositoryImpl(accountStorage);
            AccountListener listener = accountModelDomain -> {

                switch (accountModelDomain.type){

                    case ("r"):
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        NavHostFragment.findNavController(SplashAccountBSFragment.this).navigate(R.id.action_splashAccountBSFragment_to_restaurateurProfileBSFragment);
                        break;
                    case ("u"):
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        NavHostFragment.findNavController(SplashAccountBSFragment.this).navigate(R.id.action_splashAccountBSFragment_to_userProfileBSFragment);
                        break;
                    case ("m"):
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        NavHostFragment.findNavController(SplashAccountBSFragment.this).navigate(R.id.action_splashAccountBSFragment_to_moderatorProfileBSFragment);
                        break;
                    default:
                        break;
                }
            };

            GetAccountDataUseCase getAccountDataUseCase = new GetAccountDataUseCase(accountRepository, listener, mAuth.getUid());
            getAccountDataUseCase.execute();

        }
        else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            NavHostFragment.findNavController(SplashAccountBSFragment.this).navigate(R.id.action_splashAccountBSFragment_to_signInOrSignUpBSFragment);
        }
    }
}