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

import java.util.ArrayList;
import java.util.List;

/**
 * 多选题
 */
public class MultiSelectTopicView extends RecyclerView {
    private String answer = "";
    private String content;

    private List<AnswerMultiSelectBean> selectBeans;
    private AnswerAdapter adapter;

    public MultiSelectTopicView(Context context, String content) {
        super(context);
        this.content = content;
        initView();
    }

    public MultiSelectTopicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MultiSelectTopicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (TextUtils.isEmpty(content)) return;

        selectBeans = new ArrayList<>();
        List<String> stringList = StrUtil.splitStr(content, ",");
        for (int i = 0; i < stringList.size(); i++) {
            AnswerMultiSelectBean bean = new AnswerMultiSelectBean();
            bean.isSelect = false;
            bean.answer = stringList.get(i);
            selectBeans.add(bean);
        }

        adapter = new AnswerAdapter(getContext(), selectBeans);
        setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, false));
        setAdapter(adapter);
    }

    public void setContent(String content) {
        this.content = content;
    }

    class AnswerAdapter extends BaseVbAdapter<AnswerMultiSelectBean, AdapterSelectTopicBinding> {

        public AnswerAdapter(Context context, List<AnswerMultiSelectBean> list) {
            super(context, list);
        }

        @Override
        protected BaseViewHolder getViewHolder(FrameLayout parent, LayoutInflater inflater) {
            return new BaseViewHolder(parent, AdapterSelectTopicBinding.inflate(inflater)) {
                @Override
                protected void bindData(AnswerMultiSelectBean data, int position) {
                    setViewStyle(vb, position);

                    if (data.isSelect) {
                        vb.ivSelect.setSelected(true);
                    } else {
                        vb.ivSelect.setSelected(false);
                    }

                    if (data.answer.indexOf("|") == -1) {
                        vb.tvScore.setVisibility(GONE);
                        vb.tvAnswer.setText(data.answer);
                    } else {
                        vb.tvScore.setVisibility(VISIBLE);
                        try {
                            String[] str = data.answer.split("\\|");
                            if (str.length < 1) return;
                            vb.tvAnswer.setText(str[0]);
                            if (str.length < 2) return;
                            vb.tvScore.setText(str[1] + "分");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    vb.cflView.setOnClickListener(view -> {
                        data.isSelect = data.isSelect ? false : true;
                        if (answerResultListener != null) {
                            answerResultListener.onResult(getAnswer());
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

    public void setAnswer(String answer) {
        if (TextUtils.isEmpty(answer)) return;
        this.answer = answer;
        int selectPos = -1;
        List<String> strList = StrUtil.splitStr(answer, ",");
        if (strList != null && strList.size() > 0) {
            for (int i = 0; i < strList.size(); i++) {
                try {
                    selectPos = Integer.valueOf(strList.get(i));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (selectPos == -1) return;
                if (adapter.getList().size() > (selectPos - 1)) {
                    adapter.getList().get(selectPos - 1).isSelect = true;
                }
            }
        }
    }

    public String getAnswer() {
        if (adapter == null) return "";
        answer = "";
        for (int i = 0; i < adapter.getList().size(); i++) {
            if (adapter.getList().get(i).isSelect) {
                if ((i + 1) != adapter.getList().size()) {
                    answer = answer + (i + 1) + ",";
                } else {
                    answer = answer + (i + 1) + "";
                }
            }
        }
        return answer;
    }

    class AnswerMultiSelectBean {
        boolean isSelect;
        String answer;
    }

    private AnswerResultListener answerResultListener;

    public void setAnswerResultListener(AnswerResultListener answerResultListener) {
        this.answerResultListener = answerResultListener;
    }
}
