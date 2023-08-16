package com.example.psq.fragment.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.example.psq.base.viewmodel.BaseFragmentViewModel;
import com.example.psq.bean.UpLoadAnswerBean;
import com.example.psq.bean.UploadResultBean;
import com.example.psq.greendao.operation.AnsweredOperation;
import com.example.psq.network.BaseObserver;
import com.example.psq.network.NetworkApi;
import com.example.psq.network.api.ApiService;
import com.example.psq.network.utils.LogUtil;
import com.example.psq.utils.TShow;

import java.util.List;

public class AnsweredViewModel extends BaseFragmentViewModel {
    public MutableLiveData<Boolean> uploadResult;
    public AnsweredOperation operation;

    public AnsweredViewModel() {
        uploadResult = new MutableLiveData<>();
        operation = new AnsweredOperation();
    }

    public void uploadAnswer(List<UpLoadAnswerBean> loadAnswerBeans) {
        if(TextUtils.isEmpty(NetworkApi.getBaseUrl())){
            TShow.show("请到设置页保存API！");
            return;
        }
        NetworkApi.createService(ApiService.class)
                .uploadAnswer(loadAnswerBeans)
                .compose(NetworkApi.applySchedulers(new BaseObserver<UploadResultBean>() {
                    @Override
                    public void onSuccess(UploadResultBean uploadResultBean) {
                        LogUtil.e(JSON.toJSONString(uploadResultBean));
                        //code:1成功，2失败
                        if (uploadResultBean.code == 1) {
                            uploadResult.setValue(true);
                        } else {
                            uploadResult.setValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        uploadResult.setValue(false);
                    }
                }));
    }
}
