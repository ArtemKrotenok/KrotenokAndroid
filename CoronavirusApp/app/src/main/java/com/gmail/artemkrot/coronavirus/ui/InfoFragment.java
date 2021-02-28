package com.gmail.artemkrot.coronavirus.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.artemkrot.coronavirus.R;
import com.gmail.artemkrot.coronavirus.repository.CoronaVirusDataEntity;
import com.gmail.artemkrot.coronavirus.repository.CoronaVirusDataRepository;
import com.gmail.artemkrot.coronavirus.repository.InfectionCountry;

import java.util.ArrayList;

import static com.gmail.artemkrot.coronavirus.ui.TextFormatUtil.formattedNumber;

public class InfoFragment extends Fragment implements ViewUpdate {

    private CoronaVirusDataEntity coronaVirusData;
    private CountriesItemAdapter adapter;
    private TextView lastCheckTimeText;
    private TextView totalConfirmed;
    private TextView totalDeaths;
    private TextView totalRecovered;
    private final CoronaVirusDataRepository coronaVirusDataRepository = CoronaVirusDataRepository.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initVerbals(view);
        if (coronaVirusDataRepository.isActual()) {
            setData();
        }
    }

    @Override
    public void updateData() {
        setData();
    }

    private void setData() {
        coronaVirusData = coronaVirusDataRepository.getCoronaVirusData();
        adapter.update(coronaVirusData.getInfectionCountryList());
        lastCheckTimeText.setText("last update " + coronaVirusData.getLastCheckTimeText());
        totalConfirmed.setText(formattedNumber(coronaVirusData.getTotalConfirmed()));
        totalDeaths.setText(formattedNumber(coronaVirusData.getTotalDeaths()));
        totalRecovered.setText(formattedNumber(coronaVirusData.getTotalRecovered()));
    }

    private void initVerbals(View view) {
        lastCheckTimeText = view.findViewById(R.id.last_check_time);
        totalConfirmed = view.findViewById(R.id.total_confirmed);
        totalDeaths = view.findViewById(R.id.total_deaths);
        totalRecovered = view.findViewById(R.id.total_recovered);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        adapter = new CountriesItemAdapter(new ArrayList<InfectionCountry>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration itemDecoration = new CountriesItemDecoration();
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}