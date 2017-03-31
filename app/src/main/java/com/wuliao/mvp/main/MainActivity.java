package com.wuliao.mvp.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wuliao.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.turing_main);
        initView();
    }

    private void initView(){

    }

    /**
     * 弹出软键盘
     */
    /*private void showKeyBoard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.SHOW_IMPLICIT);
    }*/

    /**
     * 收起软键盘
     */
    /*public void closeKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }*/

}
