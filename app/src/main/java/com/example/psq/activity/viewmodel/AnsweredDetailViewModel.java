package com.example.psq.activity.viewmodel;

import com.example.psq.base.viewmodel.BaseActivityViewModel;
import com.example.psq.greendao.operation.AnsweredOperation;

public class AnsweredDetailViewModel extends BaseActivityViewModel {
    public AnsweredOperation operation;

    public AnsweredDetailViewModel() {
        operation = new AnsweredOperation();
    }
}
