package com.gmail.artemkrot.coronavirus.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.gmail.artemkrot.coronavirus.R;
import com.gmail.artemkrot.coronavirus.net.DataLoadListener;
import com.gmail.artemkrot.coronavirus.net.DataLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity implements DataLoadListener {

    private BottomNavigationView bottomNavigationView;
    private DataLoader dataLoader;
    private ViewUpdate infoPage = null;
    private ViewUpdate mapPage = null;

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
        Toast toast = Toast.makeText(getApplicationContext(),
                getString(R.string.text_message_data_was_update), Toast.LENGTH_SHORT);
        toast.show();
    }

    private void updateData() {
        if (mapPage != null) {
            mapPage.updateData();
        }
        if (infoPage != null) {
            infoPage.updateData();
        }
    }

    private void initVerbals() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_one:
                        startInfoFragment();
                        break;
                    case R.id.action_two:
                        loadData();
                        break;
                    case R.id.action_three:
                        startMapFragment();
                        break;
                }
                return true;
            }
        });
    }

    private void startInfoFragment() {
        InfoFragment infoFragment = new InfoFragment();
        infoPage = infoFragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, infoFragment);
        fragmentTransaction.commit();
    }

    private void startMapFragment() {
        MapFragment mapFragment = new MapFragment();
        mapPage = mapFragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, mapFragment);
        fragmentTransaction.commit();
    }

    private void loadData() {
        dataLoader = new DataLoader(this);
        dataLoader.loadData();
    }
}