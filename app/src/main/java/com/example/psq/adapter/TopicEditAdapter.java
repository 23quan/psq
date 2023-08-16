package com.example.psq.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.psq.TopicView.CompletionTopicView;
import com.example.psq.TopicView.MultiSelectTopicView;
import com.example.psq.TopicView.RadioTopicView;
import com.example.psq.base.adapter.BaseVbAdapter;
import com.example.psq.databinding.AdapterTopicBinding;
import com.example.psq.greendao.dao.AnswerDAO;
import com.example.psq.greendao.dao.TopicDAO;

import java.util.List;

import retrofit2.http.GET;

public class TopicEditAdapter extends BaseVbAdapter<AnswerDAO, AdapterTopicBinding> {

    public TopicEditAdapter(Context context, List<AnswerDAO> list) {
        super(context, list);
    }

    @Override
    protected BaseViewHolder getViewHolder(FrameLayout parent, LayoutInflater inflater) {
        return new BaseViewHolder(parent, AdapterTopicBinding.inflate(inflater)) {
            @Override
            protected void bindData(AnswerDAO data, int position) {
                if (!data.getType().equals("3") && !data.getType().equals("6")) {
                    if (!TextUtils.isEmpty(data.getTitle())) {
                        vb.tvQuestion.setVisibility(View.VISIBLE);
                        vb.tvQuestion.setText((position + 1) + "." + data.getTitle());
                    }
                }

                if (data.getType().equals("1")) {//单选
                    getRadioTopicView(vb.flAnswerView, data.getContent(), position);
                } else if (data.getType().equals("2")) {//多选
                    getMultiSelectTopicView(vb.flAnswerView, data.getContent(), position);
                } else if (data.getType().equals("3")) {//填空
                    getCompletionTopicView(vb.flAnswerView, data.getTitle(), position);
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

    public void getRadioTopicView(FrameLayout parent, String content, int position) {
        RadioTopicView radioTopicView = new RadioTopicView(context, content);
        radioTopicView.setAnswer(getList().get(position).getAnswer());
        radioTopicView.setAnswerResultListener(result -> {
            getList().get(position).setAnswer(result);
        });
        parent.addView(radioTopicView);
    }

    public void getMultiSelectTopicView(FrameLayout parent, String content, int position) {
        MultiSelectTopicView multiSelectTopicView = new MultiSelectTopicView(context, content);
        multiSelectTopicView.setAnswer(getList().get(position).getAnswer());
        multiSelectTopicView.setAnswerResultListener(result -> {
            getList().get(position).setAnswer(result);
        });
        parent.addView(multiSelectTopicView);
    }

    public void getCompletionTopicView(FrameLayout parent, String content, int position) {
        CompletionTopicView completionTopicView = new CompletionTopicView(context, (position + 1) + "." + content);
        completionTopicView.setAnswer(getList().get(position).getAnswer());
        completionTopicView.setAnswerResultListener(result -> {
            getList().get(position).setAnswer(result);
        });
        parent.addView(completionTopicView);
    }
}
