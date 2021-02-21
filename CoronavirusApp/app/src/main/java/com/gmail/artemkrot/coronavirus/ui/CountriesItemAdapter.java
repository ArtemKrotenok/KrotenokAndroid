package com.gmail.artemkrot.coronavirus.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.artemkrot.coronavirus.R;
import com.gmail.artemkrot.coronavirus.repository.InfectionCountry;

import java.util.List;

public class CountriesItemAdapter extends RecyclerView.Adapter<CountriesItemAdapter.NewsListHolder> {

    private final List<InfectionCountry> infectionCountryList;

    public CountriesItemAdapter(List<InfectionCountry> infectionCountryList) {
        this.infectionCountryList = infectionCountryList;
    }

    @NonNull
    @Override
    public NewsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsListHolder(
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(R.layout.list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListHolder holder, int position) {
        final InfectionCountry infectionCountry = infectionCountryList.get(position);
        holder.confirmedText.setText(TextFormatUtil.formattedNumber(infectionCountry.getConfirmed()));
        holder.nameCountryText.setText(infectionCountry.getName());
    }

    @Override
    public int getItemCount() {
        return infectionCountryList.size();
    }

    public void update(List<InfectionCountry> list) {
        infectionCountryList.clear();
        infectionCountryList.addAll(list);
        notifyDataSetChanged();
    }

    static class NewsListHolder extends RecyclerView.ViewHolder {
        TextView confirmedText;
        TextView nameCountryText;

        NewsListHolder(@NonNull View itemView) {
            super(itemView);
            confirmedText = itemView.findViewById(R.id.confirmed);
            nameCountryText = itemView.findViewById(R.id.country);
        }
    }
}