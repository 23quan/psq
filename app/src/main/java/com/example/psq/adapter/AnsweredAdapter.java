package com.example.psq.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.psq.activity.AnsweredDetailActivity;
import com.example.psq.base.BaseApplication;
import com.example.psq.base.BaseConstant;
import com.example.psq.base.adapter.BaseVbAdapter;
import com.example.psq.databinding.AdapterAnsweredBinding;
import com.example.psq.databinding.AdapterFooterviewBinding;
import com.example.psq.greendao.dao.AnsweredDAO;

import java.util.List;

public class AnsweredAdapter extends BaseVbAdapter<AnsweredDAO, AdapterAnsweredBinding> {
    public AnsweredAdapter(Context context, List<AnsweredDAO> list) {
        super(context, list);
    }

    @Override
    protected BaseViewHolder getViewHolder(FrameLayout parent, LayoutInflater inflater) {
        return new BaseViewHolder(parent, AdapterAnsweredBinding.inflate(inflater)) {
            @Override
            protected void bindData(AnsweredDAO data, int position) {
                setItemViewStyle(vb, false);

                if (data.getUpload()) {
                    vb.tvEdit.setText("已传");
                    vb.tvEdit.setTextColor(Color.parseColor("#939393"));
                } else {
                    vb.tvEdit.setText("编辑");
                    vb.tvEdit.setTextColor(Color.parseColor("#2776ff"));
                }

                vb.tvSort.setText(String.valueOf(position + 1));
                vb.tvTitle.setText(data.getTitle());
                vb.tvName.setText(data.getName());
                vb.tvTime.setText(data.getAnswerTime());

                vb.tvEdit.setOnClickListener(view -> {
                    if (data.getUpload()) return;
                    BaseApplication.getAnswerDAOs = data.getAnswerDAOS();
                    Intent intent = new Intent(context, AnsweredDetailActivity.class);
                    intent.putExtra(BaseConstant.BANKTiTle, data.getTitle());
                    context.startActivity(intent);
                });

                vb.tvDelete.setOnClickListener(view -> {
                    if (onClickListener != null) {
                        onClickListener.onClick(data, position);
                    }
                });
            }
        };
    }

    public void deleteItem(int position) {
        getList().remove(position);
        if (getList().size() == 0) {

        }
        notifyDataSetChanged();
    }

    public void setFooterText(String footerText) {
        ((AdapterFooterviewBinding) fv).tvFooter.setText(footerText);
    }

    public static void setItemViewStyle(AdapterAnsweredBinding vb, boolean isTop) {
        vb.cflView.setDrawStyle(1, Color.parseColor("#939393"));
        vb.tvSort.setDrawStyle(1, Color.parseColor("#939393"));
        vb.tvTitle.setDrawStyle(1, Color.parseColor("#939393"));
        vb.tvName.setDrawStyle(1, Color.parseColor("#939393"));
        vb.tvTime.setDrawStyle(1, Color.parseColor("#939393"));

        vb.tvSort.drawBorder(false, false, false, true);
        vb.tvTitle.drawBorder(false, false, false, true);
        vb.tvName.drawBorder(false, false, false, true);
        vb.tvTime.drawBorder(false, false, false, true);
        if (isTop) {
            vb.cflView.drawBorder(true, true, true, true);
        } else {
            vb.cflView.drawBorder(false, true, true, true);
        }
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(AnsweredDAO data, int position);
    }
}
