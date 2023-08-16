package com.example.psq.base.viewmodel;


import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.psq.network.NetworkApi;

import java.lang.reflect.ParameterizedType;

public abstract class BaseActivityViewModel extends ViewModel implements LifecycleObserver {
    public BaseActivityViewModel() {

    }
}
