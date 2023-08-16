package com.example.psq.activity.viewmodel;

import com.example.psq.base.viewmodel.BaseActivityViewModel;
import com.example.psq.greendao.operation.QBankOperation;

public class MainViewModel extends BaseActivityViewModel {
    public QBankOperation qBankOperation;

    public MainViewModel() {
        qBankOperation = new QBankOperation();
    }
}
