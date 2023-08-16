package com.example.psq.view.timeselector.adapter;

import com.example.psq.view.timeselector.api.WheelAdapter;

public class NumericWheelAdapter implements WheelAdapter {
    public static final int DEFAULT_MAX_VALUE = 9;
    private static final int DEFAULT_MIN_VALUE = 0;

    private int minValue;
    private int maxValue;
    private int min;
    private String format;

    public NumericWheelAdapter() {
        this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }

    public NumericWheelAdapter(int minValue, int maxValue) {
        this(minValue, maxValue, null, 0);
    }

    public NumericWheelAdapter(int minValue, int maxValue, String format, int min) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.format = format;
        this.min = min;
    }

    @Override
    public String getItem(int index) {
        if (index >= 0) {
            if (format == null) {
                int value = minValue + index % getItemsCount();
                return Integer.toString(value);
            } else if (format.equals("minute")) {
                int mvalue = minValue + index % getItemsCount() * min;
                return Integer.toString(mvalue);
            }
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        if (format == null) {
            return maxValue - minValue + 1;
        } else if (format.equals("minute")) {
            return (maxValue - minValue) / min + 1;
        }
        return 11;
    }

    @Override
    public int getMaximumLength() {
        if (format == null) {
            int max = Math.max(Math.abs(maxValue), Math.abs(minValue));
            int maxLen = Integer.toString(max).length();
            if (minValue < 0) {
                maxLen++;
            }
            return maxLen;
        } else if (format.equals("minute")) {
            return (maxValue - minValue) / min + 1;
        }
        return 11;
    }
}
