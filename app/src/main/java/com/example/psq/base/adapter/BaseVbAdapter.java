package com.example.psq.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseVbAdapter<T, V extends ViewBinding> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_NORMAL = 0X1111;
    public static final int ITEM_TYPE_HEADER = 0X1112;
    public static final int ITEM_TYPE_FOOTER = 0X1113;

    public ViewBinding hv;
    public ViewBinding fv;

    public Context context;
    public int selectPos = -1;
    private boolean isHasHeader = false;
    private boolean isHasFooter = false;
    private List<T> list = new ArrayList<>();

    public BaseVbAdapter(Context context, List<T> list) {
        this.context = context;
        if (list != null) {
            this.list.addAll(list);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(new FrameLayout(context), hv);
        } else if (viewType == ITEM_TYPE_FOOTER) {
            return new FooterViewHolder(new FrameLayout(context), fv);
        } else {
            return getViewHolder(new FrameLayout(context), LayoutInflater.from(context));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseVbAdapter.BaseViewHolder) {
            BaseViewHolder baseViewHolder = (BaseViewHolder)holder;
            if (isHasHeader && isHasFooter) {
                if (position == 0 || position == list.size() + 1) {
                    return;
                }
                baseViewHolder.bindData(list.get(position - 1), position - 1);
            }
            if (position != 0 && isHasHeader && !isHasFooter) {
                baseViewHolder.bindData(list.get(position - 1), position - 1);
            }
            if (!isHasHeader && isHasFooter) {
                if (position == list.size()) {
                    return;
                }
                baseViewHolder.bindData(list.get(position), position);
            }
            if (!isHasHeader && !isHasFooter) {
                baseViewHolder.bindData(list.get(position), position);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isHasHeader && position == 0) {
            return ITEM_TYPE_HEADER;
        } else if (!isHasHeader && isHasFooter && position == list.size()) {
            return ITEM_TYPE_FOOTER;
        } else if (isHasHeader && isHasFooter && position == list.size() + 1) {
            return ITEM_TYPE_FOOTER;
        }
        return ITEM_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        int size = list.size();
        if (isHasFooter) {
            size++;
        }
        if (isHasHeader) {
            size++;
        }
        return size;
    }

    public void addHeaderView(ViewBinding header) {
        this.hv = header;
        isHasHeader = true;
        notifyDataSetChanged();
    }

    public void addFooterView(ViewBinding footer) {
        this.fv = footer;
        isHasFooter = true;
        notifyDataSetChanged();
    }

    public void addList(List<T> list) {
        if (list == null) {
            return;
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        if (list == null) {
            return;
        }
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void refresh(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    protected abstract BaseViewHolder getViewHolder(FrameLayout parent, LayoutInflater inflater);

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        public V vb;

        protected abstract void bindData(T data, int position);

        public BaseViewHolder(FrameLayout parent, V view) {
            super(parent);
            if (view.getRoot().getParent() == null) {
                parent.addView(view.getRoot());
            }
            vb = view;
            FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            parent.setLayoutParams(fl);
            vb.getRoot().setLayoutParams(fl);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(FrameLayout parent, ViewBinding view) {
            super(parent);
            if (view.getRoot().getParent() == null) {
                parent.addView(view.getRoot());
            }
            hv = view;
            FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            parent.setLayoutParams(fl);
            hv.getRoot().setLayoutParams(fl);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(FrameLayout parent, ViewBinding view) {
            super(parent);
            if (view.getRoot().getParent() == null) {
                parent.addView(view.getRoot());
            }
            fv = view;
            FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            parent.setLayoutParams(fl);
            fv.getRoot().setLayoutParams(fl);
        }
    }
}

