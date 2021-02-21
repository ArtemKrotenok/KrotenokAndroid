package com.gmail.artemkrot.taxi.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.gmail.artemkrot.taxi.R;
import com.gmail.artemkrot.taxi.net.DataLoadListener;
import com.gmail.artemkrot.taxi.net.DataLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity implements DataLoadListener {

    private BottomNavigationView bottomNavigationView;
    private InfoFragment infoFragment;
    private MapFragment mapFragment;
    private DataLoader dataLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVerbals();
        startInfoFragment();
        loadData();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataLoader.finish();
    }

    @Override
    public void onFinishLoad() {
        updateData();
    }

    private void initVerbals() {
        mapFragment = new MapFragment();
        infoFragment = new InfoFragment();
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_one:
                        startInfoFragment();
                        break;
                    case R.id.action_two:
                        updateData();
                        Toast toast = Toast.makeText(getApplicationContext(),
                                getString(R.string.text_message_data_was_update), Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                    case R.id.action_three:
                        startMapFragment();
                        break;
                }
                return true;
            }
        });
    }

    private void updateData() {
        infoFragment.updateData();
        if (mapFragment.isReady()) {
            mapFragment.updateData();
        }
    }

    private void startInfoFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, infoFragment);
        fragmentTransaction.commit();
    }

    private void startMapFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, mapFragment);
        fragmentTransaction.commit();
    }


    private void loadData() {
        dataLoader = new DataLoader(this);
        dataLoader.loadData();
    }
}