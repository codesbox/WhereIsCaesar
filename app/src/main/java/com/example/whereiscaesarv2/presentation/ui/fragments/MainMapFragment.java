package com.example.whereiscaesarv2.presentation.ui.fragments;

import static com.example.whereiscaesarv2.presentation.app.App.isMapKitSetApiKey;
import static com.example.whereiscaesarv2.presentation.app.App.mapContext;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentMainMapBinding;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModelFactory;
import com.example.whereiscaesarv2.presentation.viewModels.sharedViewModels.MapSharedViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;


public class MainMapFragment extends Fragment {

    MainMapFragmentViewModel viewModel;
    public BottomSheetBehavior<FragmentContainerView> bottomSheetBehavior;

    private MapView mapView;
    double yotcLat = 55.751244;
    double yotcLon = 37.618426;
    private static boolean  isMapKitInitialized = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (!isMapKitSetApiKey){
            MapKitFactory.setApiKey("6c6cd304-9d0b-4a28-a718-27e056899465");
            isMapKitSetApiKey = true;
        }

        if (!isMapKitInitialized){
            MapKitFactory.initialize(requireContext());
            isMapKitInitialized = true;
        }

        return inflater.inflate(R.layout.fragment_main_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentMainMapBinding binding = FragmentMainMapBinding.bind(view);
        mapView = binding.mapView;
        MapSharedViewModel mapSharedViewModel = new ViewModelProvider(requireActivity(), (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(MapSharedViewModel.class);


        viewModel = new ViewModelProvider(this, new MainMapFragmentViewModelFactory()).get(MainMapFragmentViewModel.class);


        mapView.getMap().move(
                new CameraPosition(new Point(yotcLat, yotcLon), 10.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
        mapContext = requireContext();




        bottomSheetBehavior = BottomSheetBehavior.from(binding.containerBottomSheet);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset == 0.0) {
                    Navigation.findNavController(bottomSheet).popBackStack(R.id.searchLinkBSFragment, false);


                }
            }
        });
        mapSharedViewModel.setBehaviorMutableLiveData(bottomSheetBehavior);
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();

    }
}