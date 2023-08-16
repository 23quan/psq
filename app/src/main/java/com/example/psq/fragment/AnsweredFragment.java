package com.example.psq.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.example.psq.R;
import com.example.psq.adapter.AnsweredAdapter;
import com.example.psq.base.fragment.BaseFragment;
import com.example.psq.bean.UpLoadAnswerBean;
import com.example.psq.databinding.AdapterFooterviewBinding;
import com.example.psq.databinding.FragmentAnsweredBinding;
import com.example.psq.dialog.HintDialog;
import com.example.psq.dialog.UploadDialog;
import com.example.psq.fragment.viewmodel.AnsweredViewModel;
import com.example.psq.greendao.dao.AnswerDAO;
import com.example.psq.greendao.dao.AnsweredDAO;
import com.example.psq.network.utils.LogUtil;
import com.example.psq.utils.DpUtil;
import com.example.psq.utils.TShow;

import java.util.ArrayList;
import java.util.List;

/**
 * 我已回答
 */
public class AnsweredFragment extends BaseFragment<FragmentAnsweredBinding, AnsweredViewModel> implements SwipeRefreshLayout.OnRefreshListener {
    private HintDialog deleteDialog;
    private HintDialog uploadHintDialog;
    private UploadDialog uploadDialog;

    private List<UpLoadAnswerBean> upLoadAnswerBeans;
    private List<AnsweredDAO> updateAnswered;

    private Handler handler;
    private List<AnsweredDAO> answeredDAOs;
    private AnsweredAdapter answeredAdapter;
    private AdapterFooterviewBinding footerView;

    private int page;
    private int oldSize, newSize;
    //是否向上滑动
    private boolean isSlidingUpward;
    private boolean isSearch;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_answered;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        answeredDAOs = vm.operation.loadPage(0);

        handler = new Handler();
        upLoadAnswerBeans = new ArrayList<>();
        updateAnswered = new ArrayList<>();

        vb.refreshView.setColorSchemeColors(getResources().getColor(R.color.color_2776FF));
        vb.refreshView.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
        vb.refreshView.setOnRefreshListener(this);

        setStyle();

        answeredAdapter = new AnsweredAdapter(getContext(), new ArrayList<>());
        setFooterView();
        vb.rvList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        vb.rvList.setAdapter(answeredAdapter);

        answeredAdapter.setOnClickListener((data, position) -> {
            setDeleteDialog(true, position, data);
        });

        vb.rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastItem = lm.findLastCompletelyVisibleItemPosition();
                    int itemCount = lm.getItemCount();

                    if (lastItem == (itemCount - 1) && isSlidingUpward) {
                        //加载更多
                        if (oldSize < 10) return;
                        if (page == 0) {
                            loadData();
                        } else {
                            if (oldSize == newSize) return;
                            oldSize = newSize;
                            loadData();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //大于0表示正在向上滑动，小于等于0表示停止或向下滑动
                isSlidingUpward = dy > 0;
            }
        });

        vm.uploadResult.observe(this, aBoolean -> {
            if (aBoolean) {
                if (updateAnswered == null || updateAnswered.size() == 0) return;
                for (int i = 0; i < updateAnswered.size(); i++) {
                    updateAnswered.get(i).setUpload(true);
                    vm.operation.update(updateAnswered.get(i));
                }
                uploadDialog.setHint("答案上传成功!");
            } else {
                uploadDialog.setHint("答案上传失败，请重试!");
            }
        });
    }

    @Override
    public void initData() {
        if (answeredDAOs != null && answeredDAOs.size() > 0) {
            oldSize = answeredDAOs.size();
            answeredAdapter.setList(answeredDAOs);
        }
    }

    @Override
    public void onClick() {
        //删除全部
        vb.llDelete.setOnClickListener(view -> {
            setDeleteDialog(false, 0, null);
        });

        //上传答题
        vb.llUpload.setOnClickListener(view -> {
            if (uploadHintDialog == null) {
                uploadHintDialog = new HintDialog(getContext());
                uploadHintDialog.setContent("确定上传所有答题?", "", "");
            }
            uploadHintDialog.show();

            uploadHintDialog.setOnClickListener(new HintDialog.OnClickListener() {
                @Override
                public void onCancel() {

                }

                @Override
                public void onConfirm() {
                    uploadAnswer();
                    uploadHintDialog.dismiss();
                }
            });
        });

        //搜索
        vb.tvSearch.setOnClickListener(view -> {
            if (TextUtils.isEmpty(vb.edtName.getText().toString().trim())) {
                isSearch = false;
            } else {
                isSearch = true;
            }
            onRefresh();
        });
    }

    /**
     * 上传答案
     */
    public void uploadAnswer() {
        List<AnsweredDAO> uploadList = vm.operation.loadAll();
        if (uploadList == null || uploadList.size() == 0) {
            TShow.show("没有答题~");
            return;
        }

        if (uploadDialog == null) {
            uploadDialog = new UploadDialog(getContext());
        }
        uploadDialog.setLoad();
        uploadDialog.show();

        uploadDialog.setOnClickListener(() -> {
            onRefresh();
        });

        handler.post(() -> uploadData(uploadList));
    }

    public void uploadData(List<AnsweredDAO> uploadList) {
        updateAnswered.clear();
        upLoadAnswerBeans.clear();
        for (AnsweredDAO answeredDAO : uploadList) {
            if (!answeredDAO.getUpload()) {
                updateAnswered.add(answeredDAO);
                UpLoadAnswerBean bean = new UpLoadAnswerBean();
                List<UpLoadAnswerBean.AnswerDao> answerList = new ArrayList<>();
                bean.name = answeredDAO.getName();
                bean.bankId = answeredDAO.getBankId();
                bean.answerTime = answeredDAO.getAnswerTime();
                bean.upload = answeredDAO.getUpload();

                for (AnswerDAO answerDAO : answeredDAO.getAnswerDAOS()) {
                    UpLoadAnswerBean.AnswerDao answer = new UpLoadAnswerBean.AnswerDao();
                    answer.answeredId = Integer.valueOf(answerDAO.getAnswerId());
                    answer.content = answerDAO.getAnswer();
                    answerList.add(answer);
                }
                bean.answerDAOS = answerList;
                upLoadAnswerBeans.add(bean);
            }
        }
        vm.uploadAnswer(upLoadAnswerBeans);
    }

    /**
     * 加载答题
     */
    public void loadData() {
        page++;
        List<AnsweredDAO> list;
        if (isSearch) {
            list = vm.operation.load(vb.edtName.getText().toString().trim(), page);
        } else {
            list = vm.operation.loadPage(page);
        }
        if (list != null && list.size() != 0) {
            if (list.size() < 10) {
                answeredAdapter.setFooterText("没有更多");
            } else {
                answeredAdapter.setFooterText("滑动加载更多");
            }
            answeredAdapter.addList(list);
            newSize = answeredAdapter.getList().size();
        } else {
            page--;
            answeredAdapter.setFooterText("没有更多");
        }
    }

    private void setStyle() {
        vb.cflView.setDrawStyle(1, Color.parseColor("#939393"));
        vb.tvSort.setDrawStyle(1, Color.parseColor("#939393"));
        vb.tvTitle.setDrawStyle(1, Color.parseColor("#939393"));
        vb.tvName.setDrawStyle(1, Color.parseColor("#939393"));
        vb.tvTime.setDrawStyle(1, Color.parseColor("#939393"));

        vb.cflView.drawBorder(true, true, true, true);
        vb.tvSort.drawBorder(false, false, false, true);
        vb.tvTitle.drawBorder(false, false, false, true);
        vb.tvName.drawBorder(false, false, false, true);
        vb.tvTime.drawBorder(false, false, false, true);
    }

    public void setFooterView() {
        footerView = AdapterFooterviewBinding.inflate(getLayoutInflater());
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) footerView.llFooterView.getLayoutParams();
        lp.height = DpUtil.dp2px(40);
        footerView.llFooterView.setLayoutParams(lp);
        answeredAdapter.addFooterView(footerView);
        setFooterTile();
    }

    public void setFooterTile() {
        if (answeredDAOs != null && answeredDAOs.size() > 0) {
            if (answeredDAOs.size() < 10) {
                answeredAdapter.setFooterText("没有更多");
            } else {
                answeredAdapter.setFooterText("滑动加载更多");
            }
        } else {
            answeredAdapter.setFooterText("没有答题问卷");
        }
    }

    private void setDeleteDialog(boolean isSingle, int position, AnsweredDAO answeredDAO) {
        if (deleteDialog == null) {
            deleteDialog = new HintDialog(getContext());
        }
        if (isSingle) {
            deleteDialog.setContent("确定删除" + answeredDAO.getTitle() + "?", "取消", "删除");
        } else {
            deleteDialog.setContent("确定删除全部答题？", "取消", "删除");
        }
        deleteDialog.show();

        deleteDialog.setOnClickListener(new HintDialog.OnClickListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm() {
                if (isSingle) {
                    answeredAdapter.deleteItem(position);
                    vm.operation.delete(answeredDAO);
                } else {
                    vm.operation.deleteAll();
                    onRefresh();
                }
                deleteDialog.dismiss();
            }
        });
    }

    @Override
    public void onRefresh() {
        vb.refreshView.setRefreshing(true);
        if (isSearch) {
            answeredDAOs = vm.operation.load(vb.edtName.getText().toString().trim(), 0);
        } else {
            answeredDAOs = vm.operation.loadPage(0);
        }
        if (answeredDAOs != null) {
            page = 0;
            oldSize = answeredDAOs.size();
            newSize = 0;
            answeredAdapter.setList(answeredDAOs);
        }
        setFooterTile();
        vb.refreshView.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (deleteDialog != null) {
            deleteDialog = null;
        }
        if (uploadHintDialog != null) {
            uploadHintDialog = null;
        }
    }
}
