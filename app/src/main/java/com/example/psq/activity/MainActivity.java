package com.example.psq.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.psq.R;
import com.example.psq.activity.viewmodel.MainViewModel;
import com.example.psq.adapter.FragmentAdapter;
import com.example.psq.base.BaseApplication;
import com.example.psq.base.activtiy.BaseActivity;
import com.example.psq.databinding.ActivityMainBinding;
import com.example.psq.fragment.AnswerFragment;
import com.example.psq.fragment.AnsweredFragment;
import com.example.psq.fragment.HomeFragment;
import com.example.psq.fragment.QBankFragment;
import com.example.psq.greendao.DaoManager;
import com.example.psq.greendao.dao.QBankDAO;
import com.example.psq.greendao.operation.QBankOperation;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    private List<Fragment> fragments;
    private FragmentAdapter fragmentAdapter;

    private HomeFragment homeFragment;
    private QBankFragment qBankFragment;
    private AnswerFragment answerFragment;
    private AnsweredFragment answeredFragment;

    private List<QBankDAO> qBankDAOS;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        qBankDAOS = vm.qBankOperation.getAllQBank();
        if (qBankDAOS != null && qBankDAOS.size() > 0) {
            BaseApplication.getQBanks = qBankDAOS;
        }

        homeFragment = new HomeFragment();
        qBankFragment = new QBankFragment();
        answerFragment = new AnswerFragment();
        answeredFragment = new AnsweredFragment();

        fragments = new ArrayList<>();
        fragments.add(homeFragment);
        fragments.add(qBankFragment);
        fragments.add(answerFragment);
        fragments.add(answeredFragment);

        fragmentAdapter = new FragmentAdapter(this, fragments);
        vb.viewPager.setAdapter(fragmentAdapter);
        //禁止左右滑动
        vb.viewPager.setUserInputEnabled(false);
        vb.viewPager.setOffscreenPageLimit(3);

        vb.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setBottomNavigation(position);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick() {
        //主页
        vb.llHome.setOnClickListener(view -> {
            vb.viewPager.setCurrentItem(0, false);
        });

        //我的题库
        vb.llQBank.setOnClickListener(view -> {
            vb.viewPager.setCurrentItem(1, false);
        });

        //我要答题
        vb.llAnswer.setOnClickListener(view -> {
            vb.viewPager.setCurrentItem(2, false);
        });

        //我已答题
        vb.llAnswered.setOnClickListener(view -> {
            vb.viewPager.setCurrentItem(3, false);
        });
    }

    private void setBottomNavigation(int i) {
        vb.ivHome.setColorFilter(getResources().getColor(R.color.color_191919));
        vb.tvHome.setTextColor(getResources().getColor(R.color.color_191919));
        vb.ivQBank.setColorFilter(getResources().getColor(R.color.color_191919));
        vb.tvQBank.setTextColor(getResources().getColor(R.color.color_191919));
        vb.ivAnswer.setColorFilter(getResources().getColor(R.color.color_191919));
        vb.tvAnswer.setTextColor(getResources().getColor(R.color.color_191919));
        vb.ivAnswered.setColorFilter(getResources().getColor(R.color.color_191919));
        vb.tvAnswered.setTextColor(getResources().getColor(R.color.color_191919));
        switch (i) {
            case 0:
                vb.ivHome.setColorFilter(getResources().getColor(R.color.color_2776FF));
                vb.tvHome.setTextColor(getResources().getColor(R.color.color_2776FF));
                break;
            case 1:
                vb.ivQBank.setColorFilter(getResources().getColor(R.color.color_2776FF));
                vb.tvQBank.setTextColor(getResources().getColor(R.color.color_2776FF));
                break;
            case 2:
                vb.ivAnswer.setColorFilter(getResources().getColor(R.color.color_2776FF));
                vb.tvAnswer.setTextColor(getResources().getColor(R.color.color_2776FF));
                break;
            case 3:
                vb.ivAnswered.setColorFilter(getResources().getColor(R.color.color_2776FF));
                vb.tvAnswered.setTextColor(getResources().getColor(R.color.color_2776FF));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onQBankBeanEvent(List<QBankDAO> qBankDAOS) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DaoManager.getInstance().closeConnection();
    }
}
