package com.example.psq.activity;

import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.psq.R;
import com.example.psq.activity.viewmodel.AnswerDetailViewModel;
import com.example.psq.adapter.TopicAdapter;
import com.example.psq.base.BaseApplication;
import com.example.psq.base.BaseConstant;
import com.example.psq.base.activtiy.BaseActivity;
import com.example.psq.databinding.ActivityAnswerdetailBinding;
import com.example.psq.dialog.HintDialog;
import com.example.psq.greendao.dao.AnswerDAO;
import com.example.psq.greendao.dao.AnsweredDAO;
import com.example.psq.greendao.dao.QBankDAO;
import com.example.psq.network.utils.LogUtil;
import com.example.psq.utils.TShow;

import java.util.List;

public class AnswerDetailActivity extends BaseActivity<ActivityAnswerdetailBinding, AnswerDetailViewModel> {
    private QBankDAO qBankDAO;
    private AnsweredDAO answeredDAO;

    private HintDialog submitDialog;
    private HintDialog backDialog;
    private TopicAdapter topicAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_answerdetail;
    }

    @Override
    public void initView() {
        if (BaseApplication.getQBanks == null) {
            TShow.show("没有题库,请下载题库！");
            return;
        }
        answeredDAO = new AnsweredDAO();
        if (getIntent() != null) {
            int position = getIntent().getIntExtra(BaseConstant.SELECT_POSITION, 0);
            qBankDAO = BaseApplication.getQBanks.get(position);
            answeredDAO.setBankId(qBankDAO.getBankId());
            answeredDAO.setTitle(qBankDAO.getTitle());
            answeredDAO.setName(getIntent().getStringExtra(BaseConstant.NAME));
            answeredDAO.setAnswerTime(getIntent().getStringExtra(BaseConstant.TIME));
            answeredDAO.setUpload(false);
        }

        if (qBankDAO == null || qBankDAO.getTopic() == null) return;
        if (!TextUtils.isEmpty(qBankDAO.getTitle())) {
            vb.tvTitle.setVisibility(View.VISIBLE);
            vb.tvTitle.setText(qBankDAO.getTitle());
        }
        if (!TextUtils.isEmpty(qBankDAO.getAbstractX())) {
            vb.tvDescribe.setVisibility(View.VISIBLE);
            vb.tvDescribe.setText(qBankDAO.getAbstractX());
        }

        topicAdapter = new TopicAdapter(this, qBankDAO.getTopic());
        vb.rvAnswerList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        vb.rvAnswerList.setAdapter(topicAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick() {
        vb.tvSubmit.setOnClickListener(view -> {
            if (submitDialog == null) {
                submitDialog = new HintDialog(this);
                submitDialog.setContent("确定提交答题？", "", "");
            }
            submitDialog.show();

            submitDialog.setOnClickListener(new HintDialog.OnClickListener() {
                @Override
                public void onCancel() {

                }

                @Override
                public void onConfirm() {
                    LogUtil.e(JSON.toJSONString(topicAdapter.getAnswerBeans()));
                    List<AnswerDAO> answerDAOS = topicAdapter.getAnswerBeans();
                    for (int i = 0; i < answerDAOS.size(); i++) {
                        if (TextUtils.isEmpty(answerDAOS.get(i).getAnswer())) {
                            int sort = i + 1;
                            TShow.show("第" + sort + "道题未回答！");
                            return;
                        }
                    }
                    vm.operation.insert(answeredDAO, answerDAOS);
                    submitDialog.dismiss();
                    finish();
                }
            });
        });

        vb.flBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        if (backDialog == null) {
            backDialog = new HintDialog(this);
            backDialog.setContent("答题未提交确定退出？", "", "");
        }
        backDialog.show();

        backDialog.setOnClickListener(new HintDialog.OnClickListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm() {
                backDialog.dismiss();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backDialog != null) {
            backDialog = null;
        }
        if (submitDialog != null) {
            submitDialog = null;
        }
    }
}
