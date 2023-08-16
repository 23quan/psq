package com.example.psq.view.timeselector.api;

import com.example.psq.view.timeselector.view.WheelView;

public interface OnWheelChangedListener {
    void onChanged(WheelView wheel, int oldValue, int newValue);
}
