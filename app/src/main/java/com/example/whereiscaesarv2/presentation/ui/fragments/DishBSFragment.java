package com.example.whereiscaesarv2.presentation.ui.fragments;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;
import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.data.repositories.AccountRepositoryImpl;
import com.example.data.storages.firebase.AccountStorage;
import com.example.data.storages.firebase.AccountStorageImpl;
import com.example.domain.listeners.AccountListener;
import com.example.domain.models.MapDishCard;
import com.example.domain.models.RestaurantModelDomain;
import com.example.domain.repository.AccountRepository;
import com.example.domain.useCases.GetAccountDataUseCase;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentDishBSBinding;
import com.example.whereiscaesarv2.presentation.ui.fragments.account.SplashAccountBSFragment;
import com.example.whereiscaesarv2.presentation.ui.recycler.DishFeedbackAdapter;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.DishBSFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.DishBSFragmentViewModelFactory;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DishBSFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dish_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentDishBSBinding binding = FragmentDishBSBinding.bind(view);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setDraggable(true);
        isAuto = false;

        MapDishCard mapDishCard = (MapDishCard) getArguments().getSerializable("dishCard");
        String restaurantName = getArguments().getString("restaurantName");
        binding.dishName.setText(mapDishCard.dishName);
        binding.feedBackCount.setText(mapDishCard.counter.toString());
        binding.estimation.setText(mapDishCard.estimation.toString());
        DishBSFragmentViewModel vm = new ViewModelProvider(requireActivity(), new DishBSFragmentViewModelFactory()).get(DishBSFragmentViewModel.class);
        vm.getEstimation(restaurantName, mapDishCard.dishName);
        vm.getMutableLiveData().observe(getViewLifecycleOwner(), feedbackModelList -> {

            DishFeedbackAdapter feedbackadapter = new DishFeedbackAdapter(getContext());
            RecyclerView feedbackrecyclerView = binding.recyclerview;
            feedbackrecyclerView.setAdapter(feedbackadapter);
            feedbackrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            feedbackadapter.setItemList(feedbackModelList);


        });
        binding.button2.setOnClickListener(v -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null){

                AccountStorage accountStorage = new AccountStorageImpl();
                AccountRepository accountRepository = new AccountRepositoryImpl(accountStorage);
                AccountListener listener = accountModelDomain -> {

                    switch (accountModelDomain.type){

                        case ("r"):
                            Toast.makeText(requireContext(), "Авторизируйтесь под обычным пользователем", Toast.LENGTH_SHORT).show();
                            break;
                        case ("u"):
                            Bundle bundle = new Bundle();
                            bundle.putString("dishName", mapDishCard.dishName);
                            bundle.putString("restaurantName", restaurantName);
                            bundle.putString("firstName", accountModelDomain.firstName);
                            bundle.putString("lastName", accountModelDomain.lastName);
                            bundle.putString("id", accountModelDomain.id);
                            bundle.putString("userLevel", accountModelDomain.feedBackCount.toString());


                            NavHostFragment.findNavController(this).navigate(R.id.action_dishBSFragment_to_addFeedbackBSFragment, bundle);
                            break;
                        case ("m"):
                            Toast.makeText(requireContext(), "Авторизируйтесь под обычным пользователем", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                };

                GetAccountDataUseCase getAccountDataUseCase = new GetAccountDataUseCase(accountRepository, listener, mAuth.getUid());
                getAccountDataUseCase.execute();

            }
            else {
                Toast.makeText(requireContext(), "Авторизируйтесь в приложении", Toast.LENGTH_SHORT).show();
            }
        });



    }
}