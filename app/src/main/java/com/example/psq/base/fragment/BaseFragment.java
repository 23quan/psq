package com.example.psq.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.psq.BR;
import com.example.psq.base.viewmodel.BaseFragmentViewModel;

import java.lang.reflect.ParameterizedType;

import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseFragment<VB extends ViewDataBinding, VM extends BaseFragmentViewModel> extends Fragment implements CustomAdapt {
    public VB vb;
    public VM vm;

    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

    public abstract void initData();

    public abstract void onClick();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        vm = new ViewModelProvider(this).get(getTClass());
        vb = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        vb.setVariable(BR.vm, vm);
        vb.executePendingBindings();
        getLifecycle().addObserver(vm);
        initView(savedInstanceState);
        initData();
        onClick();
        return vb.getRoot();
    }

    @Override
    public boolean isBaseOnWidth() {//以屏幕宽度适配
        return true;
    }

    @Override
    public float getSizeInDp() {//以设计图宽度适配
        return 0;
    }

    /**
     * 获取泛型对相应的Class对象
     */
    private Class<VM> getTClass() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) parameterizedType.getActualTypeArguments()[1];
    }
}
