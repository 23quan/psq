package com.example.psq.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.example.psq.databinding.DialogInputBinding;

public class InputDialog extends Dialog {
    private DialogInputBinding vb;

    private String content;

    public InputDialog(Context context) {
        super(context);
        initView();
    }

    public InputDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected InputDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        vb = DialogInputBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        setCancelable(true);
        getWindow().setGravity(Gravity.CENTER);

        vb.tvConfirm.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onConfirm(vb.edtContent.getText().toString().trim());
            }
        });
    }

    public void setContent(String content) {
        this.content = content;
        if (vb != null) {
            vb.edtContent.setText(content);
            vb.edtContent.setSelection(content.length());
        }
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onConfirm(String content);
    }
}
