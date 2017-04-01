package com.wuliao.mvp.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wuliao.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MainFragment mainFragment;


    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    public static final String ACTION_SEARCH_WORD="com.wuliao.search";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        //
        if (savedInstanceState!=null){
            mainFragment= (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState,"MainFragment");
        }else {
            mainFragment=MainFragment.newInstance(this);
        }

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.layout_fragment,mainFragment,"MainFragment").commit();
        }
        String action=getIntent().getAction();
        if (action.equals(ACTION_SEARCH_WORD)){

            navigationView.setCheckedItem(R.id.nav_search_word);
        }else {
            showMainFragment();
            navigationView.setCheckedItem(R.id.nav_robot);
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
    }

    private void showMainFragment(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(mainFragment);
//        fragmentTransaction.hide()
        fragmentTransaction.commit();
        toolbar.setTitle(R.string.robot_name);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (mainFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"MainFragment",mainFragment);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        int id=item.getItemId();
        if (id==R.id.nav_robot){
            showMainFragment();
        }else if (id==R.id.nav_search_word){

        }else if (id==R.id.nav_change_theme){

        }else if (id==R.id.nav_settings){

        }else if (id==R.id.nav_about){

        }
        return true;
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
