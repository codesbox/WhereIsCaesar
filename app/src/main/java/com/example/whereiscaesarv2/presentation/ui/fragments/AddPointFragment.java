package com.example.whereiscaesarv2.presentation.ui.fragments;

import static com.example.whereiscaesarv2.presentation.app.App.isMapKitSetApiKey;

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

import com.example.domain.listeners.AddPointListener;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentAddPointBinding;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.AddPointFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.AddPointFragmentViewModelFactory;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantCardFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantCardFragmentViewModelFactory;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;


public class AddPointFragment extends Fragment {


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


        return inflater.inflate(R.layout.fragment_add_point, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentAddPointBinding binding = FragmentAddPointBinding.bind(view);

        AddPointFragmentViewModel viewModel = new ViewModelProvider(this, new AddPointFragmentViewModelFactory()).get(AddPointFragmentViewModel.class);

        binding.signUpButton.setEnabled(false);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                boolean allFieldsFilled = !binding.adress.getText().toString().isEmpty();
                binding.signUpButton.setEnabled(allFieldsFilled);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        binding.adress.addTextChangedListener(textWatcher);


        mapView = binding.mapView2;
        mapView.getMap().move(
                new CameraPosition(new Point(yotcLat, yotcLon), 10.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        binding.goBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });

        binding.signUpButton.setOnClickListener(v -> {
            requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            binding.progressBar5.setVisibility(View.VISIBLE);

            AddPointListener addPointListener = new AddPointListener() {
                @Override
                public void onSuccess() {
                    requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    binding.progressBar5.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Точка добавлена", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(AddPointFragment.this).popBackStack();
                }

                @Override
                public void onFailure() {
                    requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    binding.progressBar5.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                }
            };
            String address = binding.adress.getText().toString();
            String restaurantId = getArguments().getString("restaurantId");
            Point point = mapView.getMap().getCameraPosition().getTarget();
            viewModel.addPoint(point.getLatitude(), point.getLongitude(), address, restaurantId, addPointListener);
        });


    }
}