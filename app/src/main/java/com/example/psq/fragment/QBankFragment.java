package com.example.psq.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSON;
import com.example.psq.R;
import com.example.psq.adapter.QBankAdapter;
import com.example.psq.base.BaseApplication;
import com.example.psq.base.fragment.BaseFragment;
import com.example.psq.bean.QBankBean;
import com.example.psq.databinding.AdapterFooterviewBinding;
import com.example.psq.databinding.FragmentQbankBinding;
import com.example.psq.dialog.HintDialog;
import com.example.psq.fragment.viewmodel.QBankViewModel;
import com.example.psq.network.utils.LogUtil;
import com.example.psq.utils.TShow;

import java.util.ArrayList;

/**
 * 我的题库
 */
public class QBankFragment extends BaseFragment<FragmentQbankBinding, QBankViewModel> {
    private HintDialog deleteDialog;
    private HintDialog downloadDialog;
    public QBankAdapter qBankAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_qbank;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        qBankAdapter = new QBankAdapter(getContext(), new ArrayList());
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        vb.rvList.setLayoutManager(lm);
        vb.rvList.setAdapter(qBankAdapter);
        AdapterFooterviewBinding footerView = AdapterFooterviewBinding.inflate(getLayoutInflater());
        qBankAdapter.addFooterView(footerView);
    }

    @Override
    public void initData() {
        if (BaseApplication.getQBanks != null) {
            qBankAdapter.setList(BaseApplication.getQBanks);
        } else {
            qBankAdapter.setFooterText("没有题库");
            TShow.show("没有题库,请下载题库！");
        }

        vm.qBankBeans.observe(this, qBankBeans -> {
            LogUtil.e(JSON.toJSONString(qBankBeans));
            if (qBankBeans == null || qBankBeans.size() == 0) return;
            vm.operation.deleteAllQBank();
            for (QBankBean qBankBean : qBankBeans) {
                vm.operation.insertQBank(qBankBean);
            }
            BaseApplication.getQBanks = vm.operation.getAllQBank();
            qBankAdapter.setList(BaseApplication.getQBanks);
        });
    }

    @Override
    public void onClick() {
        vb.llDelete.setOnClickListener(view -> {
            if (deleteDialog == null) {
                deleteDialog = new HintDialog(getContext());
                deleteDialog.setContent("确定删除题库？", "", "");
            }
            deleteDialog.show();

            deleteDialog.setOnClickListener(new HintDialog.OnClickListener() {
                @Override
                public void onCancel() {

                }

                @Override
                public void onConfirm() {
                    vm.operation.deleteAllQBank();
                    BaseApplication.getQBanks = null;
                    qBankAdapter.setList(new ArrayList<>());
                    deleteDialog.dismiss();
                }
            });
        });

        vb.llDownload.setOnClickListener(view -> {
            if (downloadDialog == null) {
                downloadDialog = new HintDialog(getContext());
                downloadDialog.setContent("确定下载题库？", "", "");
            }
            downloadDialog.show();

            downloadDialog.setOnClickListener(new HintDialog.OnClickListener() {
                @Override
                public void onCancel() {

                }

                @Override
                public void onConfirm() {
                    vm.getQBank();
                    downloadDialog.dismiss();
                }
            });
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (deleteDialog != null) {
            deleteDialog = null;
        }

        if (downloadDialog != null) {
            downloadDialog = null;
        }
    }
}
