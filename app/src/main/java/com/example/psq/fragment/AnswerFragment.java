package com.example.psq.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.psq.R;
import com.example.psq.activity.AnswerDetailActivity;
import com.example.psq.base.BaseApplication;
import com.example.psq.base.BaseConstant;
import com.example.psq.base.fragment.BaseFragment;
import com.example.psq.bean.QBankBean;
import com.example.psq.databinding.FragmentAnswerBinding;
import com.example.psq.dialog.SelectQBankDialog;
import com.example.psq.fragment.viewmodel.AnswerViewModel;
import com.example.psq.greendao.dao.QBankDAO;
import com.example.psq.utils.TShow;
import com.example.psq.utils.TimeUtil;
import com.example.psq.view.timeselector.TimeConfig;
import com.example.psq.view.timeselector.TimeSelectorDialog;
import com.example.psq.view.timeselector.api.DateListener;

/**
 * 我要回答
 */
public class AnswerFragment extends BaseFragment<FragmentAnswerBinding, AnswerViewModel> {
    private int selectPosition;
    private TimeSelectorDialog timeSelectorDialog;
    private SelectQBankDialog selectQBankDialog;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_answer;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick() {
        vb.tvSelectTime.setOnClickListener(view -> {
            if (timeSelectorDialog == null) {
                timeSelectorDialog = new TimeSelectorDialog(getContext());
                //设置标题
                timeSelectorDialog.setTimeTitle("选择时间：");
                //显示类型
                timeSelectorDialog.setIsShowtype(TimeConfig.YEAR_MONTH_DAY_HOUR_MINUTE);
                //设置起始时间
                timeSelectorDialog.setStartYear(2000);
                //隐藏清除按钮
                timeSelectorDialog.setEmptyIsShow(false);
                timeSelectorDialog.setDialogSize(280, 0);
            }
            //默认时间
            timeSelectorDialog.setCurrentDate(TimeUtil.getCurrentTime(TimeUtil.TIME_FORMAT2));
            timeSelectorDialog.show();

            timeSelectorDialog.setDateListener(new DateListener() {
                @Override
                public void onReturnDate(String time, int year, int month, int day, int hour, int minute, int isShowType) {
                    vb.tvSelectTime.setText(time);
                }

                @Override
                public void onReturnDate(String empty) {

                }
            });
        });

        vb.tvQBankName.setOnClickListener(view -> {
            if (BaseApplication.getQBanks != null) {
                if (selectQBankDialog == null) {
                    selectQBankDialog = new SelectQBankDialog(getContext());
                }
                selectQBankDialog.show();

                selectQBankDialog.setOnClickListener((data, position) -> {
                    selectPosition = position;
                    vb.tvQBankName.setText(data.getTitle());
                    selectQBankDialog.dismiss();
                });
            } else {
                TShow.show("没有题库,请下载题库！");
            }
        });

        vb.tvAnswer.setOnClickListener(view -> {
            if (TextUtils.isEmpty(vb.edtName.getText().toString().trim())) {
                TShow.show("请填写姓名！");
            } else if (TextUtils.isEmpty(vb.tvSelectTime.getText().toString().trim())) {
                TShow.show("请选择时间！");
            } else if (TextUtils.isEmpty(vb.tvQBankName.getText().toString().trim())) {
                TShow.show("请选择题库！");
            } else {
                if (BaseApplication.getQBanks == null) {
                    TShow.show("没有题库,请下载题库！");
                    return;
                }
                Intent intent = new Intent(getActivity(), AnswerDetailActivity.class);
                intent.putExtra(BaseConstant.NAME, vb.edtName.getText().toString().trim());
                intent.putExtra(BaseConstant.TIME, vb.tvSelectTime.getText().toString().trim());
                intent.putExtra(BaseConstant.SELECT_POSITION, selectPosition);
                getActivity().startActivity(intent);
                vb.edtName.setText("");
                vb.tvSelectTime.setText("");
                vb.tvQBankName.setText("");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (selectQBankDialog != null) {
            selectQBankDialog = null;
        }
    }
}
