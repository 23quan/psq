package com.example.psq.activity;

import android.text.TextUtils;

import com.example.psq.R;
import com.example.psq.activity.viewmodel.SettingViewModel;
import com.example.psq.base.BaseConstant;
import com.example.psq.base.activtiy.BaseActivity;
import com.example.psq.databinding.ActivitySettingBinding;
import com.example.psq.network.constant.ApiConstant;
import com.example.psq.utils.SpUtil;
import com.example.psq.utils.TShow;

import org.apache.commons.validator.routines.UrlValidator;

public class SettingActivity extends BaseActivity<ActivitySettingBinding, SettingViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick() {
        vb.tvSave.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(vb.edtApi.getText().toString().trim())) {
                if (isConnection(vb.edtApi.getText().toString().trim() + ApiConstant.QUESTION_BANK)) {
                    SpUtil.getInstance().putString(BaseConstant.API_ADDRESS, vb.edtApi.getText().toString().trim());
                    TShow.show("设置成功:" + vb.edtApi.getText().toString().trim());
                } else {
                    TShow.show("API地址不可用！");
                }
            } else {
                TShow.show("API地址不能为空！");
            }
        });
    }

    private boolean isConnection(String url) {
        String[] schemas = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemas);
        return urlValidator.isValid(url);
    }
}
