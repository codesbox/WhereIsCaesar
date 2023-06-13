package com.example.whereiscaesarv2.presentation.util.listeners;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;
import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.domain.models.RestaurantModelDomain;
import com.example.whereiscaesarv2.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;

import java.util.ArrayList;
import java.util.List;

public class MapObjectTapListenerImpl implements MapObjectTapListener {

    RestaurantModelDomain restaurantModelDomain;
    FragmentContainerView view;
    List<String> selectedDishes;

    public MapObjectTapListenerImpl(RestaurantModelDomain restaurantModelDomain, FragmentContainerView view, List<String> selectedDishes){
        this.restaurantModelDomain = restaurantModelDomain;
        this.view = view;
        this.selectedDishes = selectedDishes;

    }

    @Override
    public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {

        isAuto = true;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setDraggable(true);
        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurantCard", restaurantModelDomain);
        bundle.putStringArrayList("selectedDishes", (ArrayList<String>) selectedDishes);
        Navigation.findNavController(view).navigate(R.id.restaurantCardBSFragment, bundle);


        return false;
    }
}
