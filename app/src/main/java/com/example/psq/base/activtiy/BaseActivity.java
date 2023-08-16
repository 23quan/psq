package com.example.psq.base.activtiy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.example.psq.BR;
import com.example.psq.base.viewmodel.BaseActivityViewModel;

import java.lang.reflect.ParameterizedType;

import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseActivity<VB extends ViewDataBinding, VM extends BaseActivityViewModel> extends AppCompatActivity implements CustomAdapt {
    public VB vb;
    public VM vm;

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    public abstract void onClick();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        vm = new ViewModelProvider(this).get(getTClass());
        vb = DataBindingUtil.setContentView(this, getLayoutId());
        vb.setVariable(BR.vm, vm);
        vb.executePendingBindings();
        getLifecycle().addObserver(vm);
        initView();
        initData();
        onClick();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(vm);
    }
}
