package com.wuliao.mvp.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.wuliao.R;
import com.wuliao.mvp.about.AboutPreferenceActivity;
import com.wuliao.mvp.one.OneFragment;
import com.wuliao.mvp.one.OnePresenter;
import com.wuliao.mvp.setting.SettingActivity;

import static com.wuliao.mvp.main.MainPresenter.REQUEST_PERMISSION_CAMERA_CODE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private MainFragment mainFragment;
    private OneFragment oneFragment;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private AppBarLayout appbarLayout;


    public static final String ACTION_ONE_PAGE ="com.wuliao.mvp.one";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        //
        if (savedInstanceState!=null){
            mainFragment= (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState,"MainFragment");
            oneFragment= (OneFragment) getSupportFragmentManager().getFragment(savedInstanceState,"oneFragment");
        }else {
            mainFragment=MainFragment.newInstance();
            mainFragment.setContext(this);
            oneFragment=OneFragment.newInstance();
        }

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.layout_fragment,mainFragment,"MainFragment").commit();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_fragment,oneFragment,"oneFragment").commit();
        }
        new OnePresenter(MainActivity.this,oneFragment);

        /**
         * issue:解决聊天页面toolbar滑动的问题
         *
         */
        AppBarLayout.LayoutParams params= (AppBarLayout.LayoutParams) appbarLayout.getChildAt(0).getLayoutParams();
        String action=getIntent().getAction();
        /**
         * issue:解决fragment重叠的问题
         */
        if (action!=null && action.equals(ACTION_ONE_PAGE)){
            showOneFragment();
            navigationView.setCheckedItem(R.id.nav_one);
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
        }else {
            showMainFragment();
            navigationView.setCheckedItem(R.id.nav_robot);
            //设置不滑动
            params.setScrollFlags(0);
        }
    }

    private void initView(){
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        appbarLayout= (AppBarLayout) findViewById(R.id.main_appbarlayout);
    }

    private void showMainFragment(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.hide(oneFragment);
        fragmentTransaction.commit();
        toolbar.setTitle(R.string.app_name);
    }
    private void showOneFragment(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(oneFragment);
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.commit();
        toolbar.setTitle(R.string.one);
        //第一次加载和以后重新点击时会调用这一步
        if (oneFragment.isAdded()){
            oneFragment.notifyDataChanged();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //如果该Fragment对象被添加到了它的Activity中，那么它返回true，否则返回false
        if (mainFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"MainFragment",mainFragment);
        }
        if (oneFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"oneFragment",oneFragment);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        int id=item.getItemId();
        if (id==R.id.nav_robot){
            showMainFragment();
        }else if (id==R.id.nav_one){
            showOneFragment();
        }else if (id==R.id.nav_change_theme){
            drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, slideOffset);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    SharedPreferences sp=getSharedPreferences("user_setting", Context.MODE_PRIVATE);
                    //检车当前主题模式
                    int mode =getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    if (mode==Configuration.UI_MODE_NIGHT_YES){
                        sp.edit().putInt("theme",0).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }else if (mode== Configuration.UI_MODE_NIGHT_NO){
                        sp.edit().putInt("theme",1).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    getWindow().setWindowAnimations(R.style.WindowsAnimationonChange);
//                    recreate();
                    Intent intent=getIntent();
                    finish();
                    startActivity(intent);
                }

                @Override
                public void onDrawerStateChanged(int newState) {
                    super.onDrawerStateChanged(newState);
                }
            });
        }else if (id==R.id.nav_settings){
            startActivity(new Intent(this, SettingActivity.class));
        }else if (id==R.id.nav_about){
            startActivity(new Intent(this, AboutPreferenceActivity.class));
        }
        return true;
    }

    //用户对请求作出响应后的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
            } else {
                // Permission Denied
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
