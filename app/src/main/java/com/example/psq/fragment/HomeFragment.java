package com.example.psq.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.psq.R;
import com.example.psq.activity.MainActivity;
import com.example.psq.activity.SettingActivity;
import com.example.psq.base.fragment.BaseFragment;
import com.example.psq.databinding.FragmentHomeBinding;
import com.example.psq.fragment.viewmodel.HomeViewModel;

/**
 * 主页
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {
    private MainActivity mainActivity;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mainActivity = ((MainActivity) getActivity());
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick() {
        //我的题库
        vb.ivQBank.setOnClickListener(view -> {
            if (mainActivity != null) {
                mainActivity.vb.viewPager.setCurrentItem(1, false);
            }
        });

        //我要答题
        vb.ivAnswer.setOnClickListener(view -> {
            if (mainActivity != null) {
                mainActivity.vb.viewPager.setCurrentItem(2, false);
            }
        });

        //我已答题
        vb.ivAnswered.setOnClickListener(view -> {
            if (mainActivity != null) {
                mainActivity.vb.viewPager.setCurrentItem(3, false);
            }
        });

        //设置
        vb.llSet.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), SettingActivity.class));
        });
    }
}
