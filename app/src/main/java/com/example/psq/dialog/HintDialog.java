package com.example.psq.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;

import com.example.psq.databinding.DialogHintBinding;

public class HintDialog extends Dialog {
    private DialogHintBinding vb;

    public HintDialog(Context context) {
        super(context);
        initView();
    }

    public HintDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected HintDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        vb = DialogHintBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        setCancelable(true);
        getWindow().setGravity(Gravity.CENTER);

        vb.tvCancel.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onCancel();
            }
            dismiss();
        });

        vb.tvConfirm.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onConfirm();
            }
        });
    }

    public void setContent(String title, String cancel, String confirm) {
        if (!TextUtils.isEmpty(title)) {
            vb.tvTitle.setText(title);
        }

        if (!TextUtils.isEmpty(cancel)) {
            vb.tvCancel.setText(cancel);
        }

        if (!TextUtils.isEmpty(confirm)) {
            vb.tvConfirm.setText(confirm);
        }
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onCancel();

        void onConfirm();
    }
}
