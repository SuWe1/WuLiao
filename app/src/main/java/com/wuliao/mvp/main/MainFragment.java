package com.wuliao.mvp.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.wuliao.R;
import com.wuliao.adapter.ChatAdapter;
import com.wuliao.source.ChatBean;

/**
 * Created by Swy on 2017/3/31.
 */

public class MainFragment  extends Fragment implements MainContract.View{
    private Context context;
    private MainContract.Presenter presenter;

    private ChatAdapter adapter;

    private Button btn_turing_voice;
    private TextInputEditText edit_turing;
    private TextInputLayout textinputlayout;
    private ImageView img_turing;
    private Toolbar toobar;
    private RecyclerView recycleview;

    private boolean voiceInput=true;


    public MainFragment(Context context) {
        this.context=context;
    }

    public static MainFragment newInstance(Context context) {
        return  new MainFragment(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new MainPresenter(context,this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.turing_main,container,false);
        initView(view);
        recycleview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter=new ChatAdapter(context);
        recycleview.setAdapter(adapter);
        initFristData();
        //显示菜单栏
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.turing_setting){

        }
        return true;
    }

    private void initFristData(){
        ChatBean bean=new ChatBean();
        bean.setText("你好,我是Turing");
        bean.setType(ChatBean.TYPE_LEFT);
        adapter.addMsg(bean);
    }
    @Override
    public void initView(View view) {
        toobar= (Toolbar) getActivity().findViewById(R.id.toolbar);
        toobar.setTitle(R.string.robot_name);
        recycleview= (RecyclerView) view.findViewById(R.id.recycleView_chat);
        btn_turing_voice= (Button) view.findViewById(R.id.btn_turing_voice);
        img_turing= (ImageView) view.findViewById(R.id.img_turing);
        edit_turing= (TextInputEditText) view.findViewById(R.id.edit_turing);
        textinputlayout= (TextInputLayout) view.findViewById(R.id.textinputlayout);
        img_turing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voiceInput){
                    textinputlayout.setVisibility(View.GONE);
                    btn_turing_voice.setVisibility(View.VISIBLE);
                    img_turing.setBackgroundResource(R.drawable.ic_keyboard);
                }else {
                    btn_turing_voice.setVisibility(View.GONE);
                    textinputlayout.setVisibility(View.VISIBLE);
                    img_turing.setBackgroundResource(R.drawable.ic_voice);
                }
                voiceInput=!voiceInput;
            }
        });
        btn_turing_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatBean bean=new ChatBean();
                bean.setText(edit_turing.getText().toString());
                bean.setType(ChatBean.TYPE_RIGHT);
                adapter.addMsg(bean);
                presenter.sendMsg(edit_turing.getText().toString());
            }
        });
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        if (presenter!=null){
            this.presenter=presenter;
        }
    }

    @Override
    public void showResponse(ChatBean chatBean) {
        ChatBean responseBean=new ChatBean();
        responseBean.setType(ChatBean.TYPE_LEFT);
        responseBean.setText(chatBean.getText());
        adapter.addMsg(responseBean);
        recycleview.smoothScrollToPosition(adapter.getItemCount());
    }

    @Override
    public void showNoNetError() {

    }
}
