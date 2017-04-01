package com.wuliao.mvp.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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

/**
 * Created by Swy on 2017/3/31.
 */

public class MainFragment  extends Fragment implements MainContract.View{
    private Context context;
    private MainContract.Presenter presenter;


    private Button btn_turing_voice;
    private TextInputEditText edit_turing;
    private TextInputLayout textinputlayout;
    private ImageView img_turing;
    private Toolbar toobar;

    private boolean voiceInput=true;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return  new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.turing_main,container,false);
        initView(view);
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

    @Override
    public void initView(View view) {
        toobar= (Toolbar) getActivity().findViewById(R.id.toolbar);
        toobar.setTitle(R.string.robot_name);
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
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        if (presenter!=null){
            this.presenter=presenter;
        }
    }

    @Override
    public void showResponse() {

    }

    @Override
    public void showNoNetError() {

    }
}
