package com.gmail.artemkrot.taxi.ui;

import com.gmail.artemkrot.taxi.repository.CarLocationEntity;
import com.yandex.mapkit.geometry.Point;

public interface OnCarClickListener {
    void onCarSelect(CarLocationEntity carLocationEntity);
}