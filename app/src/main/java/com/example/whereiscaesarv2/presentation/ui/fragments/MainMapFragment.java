package com.example.whereiscaesarv2.presentation.ui.fragments;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;
import static com.example.whereiscaesarv2.presentation.app.App.isMapKitSetApiKey;
import static com.example.whereiscaesarv2.presentation.app.App.mapContext;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.data.repositories.RestaurantsRepositoryImpl;
import com.example.data.storages.firebase.RestaurantsStorage;
import com.example.data.storages.firebase.RestaurantsStorageImpl;
import com.example.domain.listeners.GetRestaurantsListener;
import com.example.domain.models.PointModel;
import com.example.domain.models.RestaurantModelDomain;
import com.example.domain.repository.RestaurantsRepository;
import com.example.domain.useCases.GetRestaurantsUseCase;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentMainMapBinding;
import com.example.whereiscaesarv2.presentation.util.listeners.CameraListenerImpl;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;
import java.util.List;


public class MainMapFragment extends Fragment {

    MainMapFragmentViewModel viewModel;
    public static BottomSheetBehavior<FragmentContainerView> bottomSheetBehavior;

    List<PlacemarkMapObject> markers = new ArrayList<>();

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
        isAuto = false;
        FragmentMainMapBinding binding = FragmentMainMapBinding.bind(view);

        ImageProvider imageProvider = ImageProvider.fromResource(
                requireContext(),
                R.drawable.marker
        );

        mapView = binding.mapView;



        viewModel = new ViewModelProvider(requireActivity(), new MainMapFragmentViewModelFactory()).get(MainMapFragmentViewModel.class);


        mapView.getMap().move(
                new CameraPosition(new Point(yotcLat, yotcLon), 10.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        mapContext = requireContext();



        bottomSheetBehavior = BottomSheetBehavior.from(binding.containerBottomSheet);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d("CCCCCCCCC", String.valueOf(slideOffset));
                Log.d("QQQQQQQQ", String.valueOf(bottomSheetBehavior.getPeekHeight()));
                if (bottomSheetBehavior.getPeekHeight() == 0){
                    if (slideOffset == 0.0 && !isAuto) {

                        Navigation.findNavController(bottomSheet).popBackStack(R.id.searchLinkBSFragment, false);
                    }
                }
                else{
                    if (slideOffset == -1.0 && !isAuto){
                        Navigation.findNavController(bottomSheet).popBackStack(R.id.searchLinkBSFragment, false);
                    }
                }

            }
        });



        viewModel.getRestaurants().observe(getViewLifecycleOwner(), restaurantModelDomainList -> {

            for (PlacemarkMapObject marker : markers){
                //mapView.getMap().getMapObjects().remove(marker);
                marker.getParent().remove(marker);
            }
            markers.clear();

            for (RestaurantModelDomain restaurant : restaurantModelDomainList){

                PlacemarkMapObject marker = mapView.getMap().getMapObjects().addPlacemark(
                        new Point(restaurant.geoPoint.latitude, restaurant.geoPoint.longitude), imageProvider);
                marker.setDraggable(true);
                markers.add(marker);

            }
        });

        mapView.getMap().addCameraListener(cameraListener);
        viewModel.getSelectedDishes().observe(getViewLifecycleOwner(), dishList -> {
            Point topLeft = mapView.getMap().getVisibleRegion().getTopLeft();
            Point bottomRight = mapView.getMap().getVisibleRegion().getBottomRight();
            viewModel.updateRestaurantsList(dishList, new PointModel(topLeft.getLatitude(), topLeft.getLongitude()),
                    new PointModel(bottomRight.getLatitude(), bottomRight.getLongitude()));

        });


    }

    private final CameraListener cameraListener = new CameraListener() {
        @Override
        public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
            if (b){
                Log.d("CAMERA", "q");

                Point bottomRight = mapView.getMap().getVisibleRegion().getBottomRight();
                Point topLeft = mapView.getMap().getVisibleRegion().getTopLeft();
                Log.d("DDDDDDDDDDD", String.valueOf(viewModel.getSelectedDishes().getValue().size()));
                for (String qqq : viewModel.getSelectedDishes().getValue()){
                    Log.d("DDDDDDDDDDD", qqq);
                }

                viewModel.updateRestaurantsList(viewModel.getSelectedDishes().getValue(), new PointModel(topLeft.getLatitude(), topLeft.getLongitude()), new PointModel(bottomRight.getLatitude(), bottomRight.getLongitude()));
            }
        }
    };

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