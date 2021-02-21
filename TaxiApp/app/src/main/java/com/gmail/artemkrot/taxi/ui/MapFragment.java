package com.gmail.artemkrot.taxi.ui;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gmail.artemkrot.taxi.R;
import com.gmail.artemkrot.taxi.repository.CarLocationRepository;
import com.gmail.artemkrot.taxi.repository.InfectionPointEntity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import static android.content.ContentValues.TAG;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    public static final int STROKE_WIDTH = 5;
    public static final int MIN_POINT_RADIUS = 30000;
    public static final int COEF_SCALE = 100000;
    public static final int MIN_COEF = 1;
    private GoogleMap mMap;
    private boolean isReady = false;
    private final CarLocationRepository carLocationRepository = CarLocationRepository.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public boolean isReady() {
        return isReady;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        isReady = true;
        setMapStyle();
        if (carLocationRepository.isActual()) {
            addCoronaVirusPoints();
        }
    }

    public void updateData() {
        addCoronaVirusPoints();
    }

    private void setMapStyle() {
        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.style_json));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }


    private void addCoronaVirusPoints() {
        for (InfectionPointEntity infectionPoint : carLocationRepository.getCoronaVirusData().getInfectionPointList()) {
            LatLng point = new LatLng(infectionPoint.getNavLat(), infectionPoint.getNavLong());
            long radius = MIN_POINT_RADIUS;
            int coefficient = Math.round(infectionPoint.getConfirmed() / COEF_SCALE + MIN_COEF);
            radius = radius * coefficient;
            CircleOptions circleOptions = new CircleOptions()
                    .center(point)
                    .radius(radius)
                    .fillColor(Color.RED)
                    .strokeColor(Color.RED)
                    .strokeWidth(STROKE_WIDTH);
            mMap.addCircle(circleOptions);
        }
    }
}