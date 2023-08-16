package com.example.psq.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.example.psq.TopicView.CompletionTopicView;
import com.example.psq.TopicView.MultiSelectTopicView;
import com.example.psq.TopicView.RadioTopicView;
import com.example.psq.base.adapter.BaseVbAdapter;
import com.example.psq.databinding.AdapterTopicBinding;
import com.example.psq.greendao.dao.AnswerDAO;
import com.example.psq.greendao.dao.TopicDAO;
import com.example.psq.network.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends BaseVbAdapter<TopicDAO, AdapterTopicBinding> {
    private List<AnswerDAO> answerDAOS;

    public TopicAdapter(Context context, List<TopicDAO> list) {
        super(context, list);
        LogUtil.e(JSON.toJSONString(list));
        answerDAOS = new ArrayList<>();
        for (TopicDAO topicDAO : list) {
            AnswerDAO answerDAO = new AnswerDAO();
            answerDAO.setAnswerId(topicDAO.getTopicId());
            answerDAO.setBankId(topicDAO.getBankId());
            answerDAO.setType(topicDAO.getType());
            answerDAO.setTitle(topicDAO.getTitle());
            answerDAO.setContent(topicDAO.getContent());
            answerDAO.setAnswer("");
            answerDAOS.add(answerDAO);
        }
    }

    @Override
    protected BaseViewHolder getViewHolder(FrameLayout parent, LayoutInflater inflater) {
        return new BaseViewHolder(parent, AdapterTopicBinding.inflate(inflater)) {
            @Override
            protected void bindData(TopicDAO data, int position) {
                if (!TextUtils.isEmpty(data.getTitle())) {
                    vb.tvQuestion.setText((position + 1) + "." + data.getTitle());
                } else {
                    vb.tvQuestion.setText((position + 1) + ".");
                }

                if (data.getType().equals("1")) {//单选
                    getRadioTopicView(vb.flAnswerView, data.getContent(), position);
                } else if (data.getType().equals("2")) {//多选
                    getMultiSelectTopicView(vb.flAnswerView, data.getContent(), position);
                } else if (data.getType().equals("3")) {//填空
                    vb.tvQuestion.setVisibility(View.GONE);
                    getCompletionTopicView(vb.flAnswerView, (position + 1) + "." + data.getTitle(), position);
                } else if (data.getType().equals("4")) {//单选评分
                    getRadioTopicView(vb.flAnswerView, data.getContent(), position);
                } else if (data.getType().equals("5")) {//多选评分
                    getMultiSelectTopicView(vb.flAnswerView, data.getContent(), position);
                } else if (data.getType().equals("6")) {//勾选
                    getRadioTopicView(vb.flAnswerView, data.getContent(), position);
                }
            }
        };
    }

    public List<AnswerDAO> getAnswerBeans() {
        return answerDAOS;
    }

    public void getRadioTopicView(FrameLayout parent, String content, int position) {
        RadioTopicView radioTopicView = new RadioTopicView(context, content);
        radioTopicView.setAnswerResultListener(result -> {
            answerDAOS.get(position).setAnswer(result);
        });
        parent.addView(radioTopicView);
    }

    public void getMultiSelectTopicView(FrameLayout parent, String content, int position) {
        MultiSelectTopicView multiSelectTopicView = new MultiSelectTopicView(context, content);
        multiSelectTopicView.setAnswerResultListener(result -> {
            answerDAOS.get(position).setAnswer(result);
        });
        parent.addView(multiSelectTopicView);
    }

    public void getCompletionTopicView(FrameLayout parent, String content, int position) {
        CompletionTopicView completionTopicView = new CompletionTopicView(context, content);
        completionTopicView.setAnswerResultListener(result -> {
            answerDAOS.get(position).setAnswer(result);
        });
        parent.addView(completionTopicView);
    }
}
