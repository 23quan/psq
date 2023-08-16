package com.example.psq.view.timeselector.api;

public interface DateListener {
    void onReturnDate(String time, int year, int month, int day, int hour, int minute, int isShowType);

    void onReturnDate(String empty);
}
