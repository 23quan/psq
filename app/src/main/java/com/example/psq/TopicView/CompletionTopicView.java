package com.example.psq.TopicView;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.example.psq.api.AnswerResultListener;
import com.example.psq.dialog.InputDialog;

/**
 * 填空题
 */
public class CompletionTopicView extends androidx.appcompat.widget.AppCompatTextView {
    private String topic = "";
    private String answer = "";

    private SpannableStringBuilder stringBuilder;
    private InputDialog inputDialog;

    public CompletionTopicView(Context context, String topic) {
        super(context);
        this.topic = topic;
        init();
    }

    public CompletionTopicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CompletionTopicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTextColor(Color.parseColor("#000000"));
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        setAnswer("");

        stringBuilder = new SpannableStringBuilder();

        setOnClickListener(view -> {
            if (inputDialog == null) {
                inputDialog = new InputDialog(getContext());
            }
            if (!TextUtils.isEmpty(answer)) {
                inputDialog.setContent(answer);
            }
            inputDialog.show();

            inputDialog.setOnClickListener(content -> {
                setAnswer(content);
                if (answerResultListener != null) {
                    answerResultListener.onResult(answer);
                }
                inputDialog.dismiss();
            });
        });
    }

    public void setAnswer(String answer) {
        if (TextUtils.isEmpty(answer)) {
            this.answer = "";
            setText(topic + "________________");
        } else {
            this.answer = answer;
            stringBuilder.clear();
            stringBuilder.append(topic + answer);
            stringBuilder.setSpan(new UnderlineSpan(), topic.length(), topic.length() + answer.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(stringBuilder);
        }
    }

    private AnswerResultListener answerResultListener;

    public void setAnswerResultListener(AnswerResultListener answerResultListener) {
        this.answerResultListener = answerResultListener;
    }
}
