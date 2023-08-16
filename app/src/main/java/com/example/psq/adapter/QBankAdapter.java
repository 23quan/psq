package com.example.psq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.psq.base.adapter.BaseVbAdapter;
import com.example.psq.databinding.AdapterFooterviewBinding;
import com.example.psq.databinding.AdapterQbankBinding;
import com.example.psq.greendao.dao.QBankDAO;

import java.util.List;

public class QBankAdapter extends BaseVbAdapter<QBankDAO, AdapterQbankBinding> {

    public QBankAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    protected BaseViewHolder getViewHolder(FrameLayout parent, LayoutInflater inflater) {
        return new BaseViewHolder(parent, AdapterQbankBinding.inflate(inflater)) {
            @Override
            protected void bindData(QBankDAO data, int position) {
                vb.tvTitle.setText(data.getTitle());
                vb.tvQBankAmount.setText("题目数量：" + data.getTopicCount());
                vb.tvUploadTime.setText("上传时间：" + data.getCreated_at());
            }
        };
    }

    @Override
    public void setList(List<QBankDAO> list) {
        super.setList(list);
        if (list != null && list.size() > 0) {
            setFooterText("共" + getList().size() + "套题");
        } else {
            setFooterText("没有题库");
        }
    }

    public void setFooterText(String footerText) {
        ((AdapterFooterviewBinding) fv).tvFooter.setText(footerText);
    }
}
