package com.example.psq.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.example.psq.databinding.DialogUploadBinding;

public class UploadDialog extends Dialog {
    private DialogUploadBinding vb;

    public UploadDialog(Context context) {
        super(context);
        initView();
    }

    public UploadDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected UploadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        vb = DialogUploadBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        setCancelable(false);
        getWindow().setGravity(Gravity.CENTER);

        vb.tvFinish.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick();
            }
            dismiss();
        });
    }

    public void setLoad() {
        vb.flHintView.setVisibility(View.GONE);
        vb.llLoadView.setVisibility(View.VISIBLE);
    }

    public void setHint(String hint) {
        vb.llLoadView.setVisibility(View.GONE);
        vb.flHintView.setVisibility(View.VISIBLE);
        vb.tvHint.setHint(hint);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick();
    }
}
