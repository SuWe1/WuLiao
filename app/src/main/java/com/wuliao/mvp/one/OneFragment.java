package com.wuliao.mvp.one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuliao.R;
import com.wuliao.adapter.OneAdapter;
import com.wuliao.interfaze.OnRecyclerViewOnClickListener;
import com.wuliao.source.one.OneBean;

import java.util.ArrayList;

/**
 * Created by Swy on 2017/4/16.
 */

public class OneFragment extends Fragment implements OneContract.View {
    private static final String TAG = "OneFragment";
    private OneContract.Presenter presenter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private CoordinatorLayout coordinatorLayout;

    private OneAdapter adapter;

    public OneFragment() {
    }

    public static OneFragment newInstance() {
        return new OneFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.one_list,container,false);
        initView(view);
        presenter.loadPosts(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.reflush();
            }
        });
        return view;
    }



    @Override
    public void initView(View view) {
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.one_refreshlayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        recyclerView= (RecyclerView) view.findViewById(R.id.one_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        coordinatorLayout= (CoordinatorLayout) view.findViewById(R.id.one_coordinatorlayout);
    }

    @Override
    public void notifyDataChanged() {
        presenter.loadPosts(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(OneContract.Presenter presenter) {
        if (presenter!=null){
            this.presenter=presenter;
        }
    }

    @Override
    public void showError() {
        Snackbar.make(coordinatorLayout,R.string.loaded_failed,Snackbar.LENGTH_SHORT)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.reflush();
                    }
                })
                .show();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void Stoploading() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showResult(ArrayList<OneBean> list) {
        if (adapter==null){
            adapter=new OneAdapter(list,getContext());
            adapter.setItemOnClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    presenter.StartReading(position);
                }

                @Override
                public void onItemLongClick(View v, int position) {

                }
            });
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showNotNetError() {
        Snackbar.make(coordinatorLayout,R.string.not_net_work,Snackbar.LENGTH_SHORT).show();
    }
}
