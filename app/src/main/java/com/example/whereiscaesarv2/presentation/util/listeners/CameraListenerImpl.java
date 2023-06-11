package com.example.whereiscaesarv2.presentation.util.listeners;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.domain.models.PointModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MainMapFragmentViewModel;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.List;

public class CameraListenerImpl implements CameraListener {

    MainMapFragmentViewModel vm;
    MapView mapView;

    public CameraListenerImpl(MainMapFragmentViewModel vm, MapView mapView){
        this.vm = vm;
        this.mapView = mapView;
        Log.d("CAMERA", "q");
    }


    @Override
    public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
        if (b){
            Log.d("CAMERA", "q");

            List<String> q = new ArrayList<>();
            Point bottomRight = mapView.getMap().getVisibleRegion().getBottomRight();
            Point topLeft = mapView.getMap().getVisibleRegion().getTopLeft();

            vm.updateRestaurantsList(q, new PointModel(topLeft.getLatitude(), topLeft.getLongitude()), new PointModel(bottomRight.getLatitude(), bottomRight.getLongitude()));
        }

    }
}
