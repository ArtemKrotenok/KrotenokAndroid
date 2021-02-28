package com.gmail.artemkrot.taxi.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.artemkrot.taxi.R;
import com.gmail.artemkrot.taxi.repository.CarLocationEntity;

import java.util.List;

public class CarItemAdapter extends RecyclerView.Adapter<CarItemAdapter.NewsListHolder> {

    private final List<CarLocationEntity> carLocationList;
    private OnCarClickListener onCarClickListener;

    public CarItemAdapter(List<CarLocationEntity> carLocationList, OnCarClickListener onCarClickListener) {
        this.carLocationList = carLocationList;
        this.onCarClickListener = onCarClickListener;
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
    public void onBindViewHolder(@NonNull NewsListHolder holder, final int position) {
        final CarLocationEntity carLocationEntity = carLocationList.get(position);
        holder.idText.setText(String.valueOf(carLocationEntity.getId()));
        holder.headingText.setText(carLocationEntity.getHeading());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCarClickListener.onCarSelect(carLocationEntity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carLocationList.size();
    }

    public void update(List<CarLocationEntity> list) {
        carLocationList.clear();
        carLocationList.addAll(list);
        notifyDataSetChanged();
    }

    static class NewsListHolder extends RecyclerView.ViewHolder {
        TextView idText;
        TextView headingText;

        NewsListHolder(@NonNull View itemView) {
            super(itemView);
            idText = itemView.findViewById(R.id.id);
            headingText = itemView.findViewById(R.id.heading);
        }
    }
}