package com.gmail.artemkrot.taxi.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.artemkrot.taxi.R;
import com.gmail.artemkrot.taxi.net.DataLoadListener;
import com.gmail.artemkrot.taxi.net.DataLoader;
import com.gmail.artemkrot.taxi.repository.CarLocationEntity;
import com.gmail.artemkrot.taxi.repository.CarLocationRepository;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.artemkrot.taxi.net.Constant.API_KEY;

public class MainActivity extends Activity implements DataLoadListener, OnCarClickListener {

    private CarItemAdapter adapter;
    private MapView mapview;
    private DataLoader dataLoader;
    private final CarLocationRepository carLocationRepository = CarLocationRepository.getInstance();
    private List<CarLocationEntity> carLocationList = new ArrayList<>();

    public static final float ZOOM = 15.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        mapview = (MapView) findViewById(R.id.mapview);
        initVerbals();
        loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dataLoader.finish();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onCarSelect(Point point) {
        mapview.getMap().move(
                new CameraPosition(point, ZOOM, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
    }

    @Override
    public void onFinishLoad() {
        updateData();
    }

    private void initVerbals() {
        RecyclerView recyclerView = findViewById(R.id.list);
        adapter = new CarItemAdapter(new ArrayList<CarLocationEntity>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new CarItemDecoration();
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void updateData() {
        carLocationList.addAll(carLocationRepository.getCarLocationsList());
        adapter.update(carLocationList);
        addCarPointsOnMap();
    }

    private void addCarPointsOnMap() {
        for (CarLocationEntity carLocation : carLocationList) {
            mapview.getMap().getMapObjects().addPlacemark(carLocation.getCoordinate());
        }
    }

    private void loadData() {
        dataLoader = new DataLoader(this);
        dataLoader.loadData();
    }
}