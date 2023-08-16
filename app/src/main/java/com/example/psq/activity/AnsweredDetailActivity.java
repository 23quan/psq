package com.example.psq.activity;

import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.psq.R;
import com.example.psq.activity.viewmodel.AnsweredDetailViewModel;
import com.example.psq.adapter.TopicEditAdapter;
import com.example.psq.base.BaseApplication;
import com.example.psq.base.BaseConstant;
import com.example.psq.base.activtiy.BaseActivity;
import com.example.psq.databinding.ActivityAnswereddetailBinding;
import com.example.psq.dialog.HintDialog;
import com.example.psq.greendao.dao.AnswerDAO;
import com.example.psq.greendao.dao.QBankDAO;
import com.example.psq.network.utils.LogUtil;
import com.example.psq.utils.TShow;

import java.util.List;

public class AnsweredDetailActivity extends BaseActivity<ActivityAnswereddetailBinding, AnsweredDetailViewModel> {
    private HintDialog submitDialog;
    private HintDialog backDialog;
    private TopicEditAdapter topicEditAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_answereddetail;
    }

    @Override
    public void initView() {
        if (BaseApplication.getAnswerDAOs == null) {
            TShow.show("找不到题库！");
            return;
        }

        if (getIntent() != null) {
            String bankTitle = getIntent().getStringExtra(BaseConstant.BANKTiTle);
            vb.tvTitle.setText(bankTitle);
        }

        topicEditAdapter = new TopicEditAdapter(this, BaseApplication.getAnswerDAOs);
        vb.rvTopicList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        vb.rvTopicList.setAdapter(topicEditAdapter);
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
                    LogUtil.e(JSON.toJSONString(topicEditAdapter.getList()));
                    List<AnswerDAO> answerDAOS = topicEditAdapter.getList();
                    for (int i = 0; i < answerDAOS.size(); i++) {
                        if (TextUtils.isEmpty(answerDAOS.get(i).getAnswer())) {
                            int sort = i + 1;
                            TShow.show("第" + sort + "道题未回答！");
                            return;
                        }
                    }
                    vm.operation.update(answerDAOS);
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
