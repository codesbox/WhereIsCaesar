package com.example.whereiscaesarv2.presentation.ui.fragments;

import static com.example.whereiscaesarv2.presentation.app.App.isMapKitSetApiKey;
import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.domain.listeners.AddRestaurantListener;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentAddRestaurantBinding;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.AddRestaurantViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.AddRestaurantViewModelFactory;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantsViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantsViewModelFactory;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.SetOptions;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddRestaurantFragment extends Fragment {


    private MapView mapView;
    double yotcLat = 55.751244;
    double yotcLon = 37.618426;
    private static boolean  isMapKitInitialized = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (!isMapKitSetApiKey){
            MapKitFactory.setApiKey("6c6cd304-9d0b-4a28-a718-27e056899465");
            isMapKitSetApiKey = true;
        }

        if (!isMapKitInitialized){
            MapKitFactory.initialize(requireContext());
            isMapKitInitialized = true;
        }



        return inflater.inflate(R.layout.fragment_add_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentAddRestaurantBinding binding = FragmentAddRestaurantBinding.bind(view);

        binding.signUpButton.setEnabled(false);



        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                boolean allFieldsFilled = !binding.firstNameRes.getText().toString().isEmpty() &&
                        !binding.adress.getText().toString().isEmpty();
                binding.signUpButton.setEnabled(allFieldsFilled);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        binding.adress.addTextChangedListener(textWatcher);
        binding.firstNameRes.addTextChangedListener(textWatcher);




        mapView = binding.mapView2;
        mapView.getMap().move(
                new CameraPosition(new Point(yotcLat, yotcLon), 10.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        AddRestaurantViewModel viewModel = new ViewModelProvider(this, new AddRestaurantViewModelFactory()).get(AddRestaurantViewModel.class);

        String id = getArguments().getString("id");

        binding.signUpButton.setOnClickListener(v -> {

            requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            binding.progressBar4.setVisibility(View.VISIBLE);

            String restaurantName = binding.firstNameRes.getText().toString();

            String address = binding.adress.getText().toString();



            AddRestaurantListener addRestaurantListener = new AddRestaurantListener() {
                @Override
                public void onSuccess() {
                    binding.progressBar4.setVisibility(View.GONE);
                    requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(AddRestaurantFragment.this).popBackStack();
                }

                @Override
                public void onFailure() {
                    binding.progressBar4.setVisibility(View.GONE);
                    requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                }
            };

            Point point = mapView.getMap().getCameraPosition().getTarget();

            viewModel.addRestaurant(point.getLatitude(), point.getLongitude(), restaurantName, id, addRestaurantListener, address);
        });
    }
}