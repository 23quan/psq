package com.example.psq.fragment.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.example.psq.base.viewmodel.BaseFragmentViewModel;
import com.example.psq.bean.QBankBean;
import com.example.psq.greendao.operation.QBankOperation;
import com.example.psq.network.BaseObserver;
import com.example.psq.network.NetworkApi;
import com.example.psq.network.api.ApiService;
import com.example.psq.utils.TShow;

import java.util.List;

public class QBankViewModel extends BaseFragmentViewModel {
    public QBankOperation operation;
    public MutableLiveData<List<QBankBean>> qBankBeans;

    public QBankViewModel() {
        operation = new QBankOperation();
        qBankBeans = new MutableLiveData<>();
    }

    //下载题库
    public void getQBank() {
        if(TextUtils.isEmpty(NetworkApi.getBaseUrl())){
            TShow.show("请到设置页保存API！");
            return;
        }
        NetworkApi.createService(ApiService.class)
                .getQBank()
                .compose(NetworkApi.applySchedulers(new BaseObserver<List<QBankBean>>() {
                    @Override
                    public void onSuccess(List<QBankBean> qBankDAO) {
                        if (qBankDAO == null || qBankDAO.size() == 0) {
                            return;
                        }
                        qBankBeans.setValue(qBankDAO);
                        TShow.show("题库下载成功");
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        TShow.show("题库下载失败：" + e.getMessage());
                    }
                }));
    }
}
