package com.example.psq.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.psq.base.BaseApplication;
import com.example.psq.base.adapter.BaseVbAdapter;
import com.example.psq.databinding.AdapterSelectQbankBinding;
import com.example.psq.databinding.DialogHintBinding;
import com.example.psq.databinding.DialogSelectQbankBinding;
import com.example.psq.greendao.dao.QBankDAO;

import java.util.List;

public class SelectQBankDialog extends Dialog {
    private DialogSelectQbankBinding vb;
    private SelectQBankAdapter selectQBankAdapter;

    public SelectQBankDialog(Context context) {
        super(context);
        initView();
    }

    public SelectQBankDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected SelectQBankDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        vb = DialogSelectQbankBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        setCancelable(true);
        getWindow().setGravity(Gravity.CENTER);

        selectQBankAdapter = new SelectQBankAdapter(getContext(), BaseApplication.getQBanks);
        vb.rvQBankList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        vb.rvQBankList.setAdapter(selectQBankAdapter);
    }

    class SelectQBankAdapter extends BaseVbAdapter<QBankDAO, AdapterSelectQbankBinding> {

        public SelectQBankAdapter(Context context, List<QBankDAO> list) {
            super(context, list);
        }

        @Override
        protected BaseViewHolder getViewHolder(FrameLayout parent, LayoutInflater inflater) {
            return new BaseViewHolder(parent, AdapterSelectQbankBinding.inflate(inflater)) {
                @Override
                protected void bindData(QBankDAO data, int position) {
                    vb.ctvTitle.drawWidth = 1;
                    vb.ctvTitle.drawColor = Color.parseColor("#939393");
                    if (position != (getList().size() - 1)) {
                        vb.ctvTitle.drawBorder(true, false, true, true);
                    } else {
                        vb.ctvTitle.drawBorder(true, true, true, true);
                    }
                    vb.ctvTitle.setText(data.getTitle());

                    itemView.setOnClickListener(view -> {
                        if (onClickListener != null) {
                            onClickListener.onClick(data,position);
                        }
                    });
                }
            };
        }
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(QBankDAO data,int position);
    }
}
