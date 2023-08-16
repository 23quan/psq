package com.example.psq.network.api;

import com.example.psq.bean.QBankBean;
import com.example.psq.bean.UpLoadAnswerBean;
import com.example.psq.bean.UploadResultBean;
import com.example.psq.network.constant.ApiConstant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    //下载题库
    @GET(ApiConstant.QUESTION_BANK)
    Observable<List<QBankBean>> getQBank();

    @POST(ApiConstant.UPLOAD_ANSWER)
    Observable<UploadResultBean> uploadAnswer(@Body List<UpLoadAnswerBean> list);
}
