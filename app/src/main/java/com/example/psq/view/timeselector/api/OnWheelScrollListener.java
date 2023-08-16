package com.example.psq.view.timeselector.api;

import com.example.psq.view.timeselector.view.WheelView;

public interface OnWheelScrollListener {
    void onScrollingStarted(WheelView wheel);

    void onScrollingFinished(WheelView wheel);
}
