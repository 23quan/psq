package com.example.psq.TopicView;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psq.api.AnswerResultListener;
import com.example.psq.base.adapter.BaseVbAdapter;
import com.example.psq.databinding.AdapterSelectTopicBinding;
import com.example.psq.utils.StrUtil;

import java.util.List;

/**
 * 单选题
 */
public class RadioTopicView extends RecyclerView {
    private String answer = "";
    private String content = "";

    private AnswerAdapter adapter;

    public RadioTopicView(Context context, String content) {
        super(context);
        this.content = content;
        initView();
    }

    public RadioTopicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RadioTopicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (TextUtils.isEmpty(content)) return;

        adapter = new AnswerAdapter(getContext(), StrUtil.splitStr(content, ","));
        setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, false));
        setAdapter(adapter);
    }

    class AnswerAdapter extends BaseVbAdapter<String, AdapterSelectTopicBinding> {

        public AnswerAdapter(Context context, List<String> list) {
            super(context, list);
        }

        @Override
        protected BaseViewHolder getViewHolder(FrameLayout parent, LayoutInflater inflater) {
            return new BaseViewHolder(parent, AdapterSelectTopicBinding.inflate(inflater)) {
                @Override
                protected void bindData(String data, int position) {
                    setViewStyle(vb, position);
                    vb.ivSelect.setSelected(false);
                    if (selectPos != -1 && selectPos == position) {
                        vb.ivSelect.setSelected(true);
                    }
                    if (data.indexOf("|") == -1) {
                        vb.tvScore.setVisibility(GONE);
                        vb.tvAnswer.setText(data);
                    } else {
                        vb.tvScore.setVisibility(VISIBLE);
                        try {
                            String[] str = data.split("\\|");
                            if (str.length < 1) return;
                            vb.tvAnswer.setText(str[0]);
                            if (str.length < 2) return;
                            vb.tvScore.setText(str[1] + "分");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    vb.cflView.setOnClickListener(view -> {
                        selectPos = position;
                        if (answerResultListener != null) {
                            answerResultListener.onResult(String.valueOf(selectPos + 1));
                        }
                        notifyDataSetChanged();
                    });
                }
            };
        }

        private void setViewStyle(AdapterSelectTopicBinding vb, int position) {
            int color = Color.parseColor("#939393");
            vb.cflView.drawWidth = 1;
            vb.cflView.drawColor = color;
            vb.tvAnswer.drawWidth = 1;
            vb.tvAnswer.drawColor = color;
            vb.tvScore.drawWidth = 1;
            vb.tvScore.drawColor = color;
            if (position != (getList().size() - 1)) {
                vb.cflView.drawBorder(true, false, true, true);
            } else {
                vb.cflView.drawBorder(true, true, true, true);
            }
            vb.tvAnswer.drawBorder(false, false, false, true);
            vb.tvScore.drawBorder(false, false, true, false);
        }
    }

    public void setAnswer(String content) {
        if (!TextUtils.isEmpty(content)) {
            int selectPos = -1;
            try {
                selectPos = Integer.valueOf(content);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (selectPos == -1) return;
            adapter.selectPos = (selectPos - 1);
        }
    }

    public String getAnswer() {
        if (adapter != null) {
            answer = String.valueOf(adapter.selectPos + 1);
        }
        return answer;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private AnswerResultListener answerResultListener;

    public void setAnswerResultListener(AnswerResultListener answerResultListener) {
        this.answerResultListener = answerResultListener;
    }
}
