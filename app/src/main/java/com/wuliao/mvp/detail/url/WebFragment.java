package com.wuliao.mvp.detail.url;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wuliao.R;

/**
 * Created by Swy on 2017/4/4.
 */

public class WebFragment extends Fragment implements WebContract.View {
    private static final String TAG = "WebFragment";

    private CoordinatorLayout coordinator;
    private WebView webView;
    private NestedScrollView scrollView;
    private SwipeRefreshLayout refreshLayout;
    private Context context;
    private Toolbar mToolbar;
    private WebContract.Presenter presenter;

    public WebFragment() {
    }

    public static WebFragment newInstance() {

        return new WebFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.web_layout,container,false);
        initView(view);
        setHasOptionsMenu(true);
        presenter.load();
        //点击换回顶部
        view.findViewById(R.id.web_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0,0);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.load();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.web_more,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.action_more:
                final BottomSheetDialog dialog=new BottomSheetDialog(getActivity());
                View view=getActivity().getLayoutInflater().inflate(R.layout.web_bottom_dialog,null);
                view.findViewById(R.id.layout_copy_link).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        presenter.copyUrl();
                    }
                });
                view.findViewById(R.id.layout_open_in_browser).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        presenter.openInBrower();
                    }
                });
                dialog.setContentView(view);
                dialog.show();
        }
        return true;
    }

    @Override
    public void initView(View view) {
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.web_refreshlayout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);//下拉刷新颜色
        webView= (WebView) view.findViewById(R.id.web_view);
        webView.setScrollbarFadingEnabled(true);
        scrollView= (NestedScrollView) view.findViewById(R.id.web_scrollView);
        coordinator= (CoordinatorLayout) view.findViewById(R.id.web_coordinatorlayout);
        mToolbar= (Toolbar) view.findViewById(R.id.web_toolbar);
        mToolbar.setTitle("   ");//默认为空
        WebActivity activity= (WebActivity) getActivity();
        activity.setSupportActionBar((Toolbar) view.findViewById(R.id.web_toolbar));
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //webview设置
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);//缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        /*if (NetworkUtil.networkConnected(context)){
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.getSettings().setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webView.getSettings().setDatabaseEnabled(true);   //开启 database storage API 功能
        webView.getSettings().setAppCacheEnabled(true);//开启 Application Caches 功能

        String cacheDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "webviewcache";
        webView.getSettings().setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录*/
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //解决 net::ERR_UNKNOWN_URL_SCHEME
                //屏蔽掉错误的重定向url
                if (url.startsWith("http:") || url.startsWith("https:")){
                    view.loadUrl(url);
                }
                return true;
            }
        });
    }


    @Override
    public void hideInternetTitle() {
        webView.setWebChromeClient(new MyWebChromeClient());
    }

    private class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            //页面加载到5%的时候 注入JS来隐藏导航栏
            if (newProgress>5){
                hideTitle(view);
            }
        }

        private void hideTitle(WebView webView){
            webView.loadUrl("javascript:(function() " +
                    "{ " +
                    "document.querySelectorAll('.header')[0].style.display='none';"+
                    "document.querySelectorAll('.text-title')[0].style.marginTop='0px';"+
                    "})()");
            //document.getElementsByClassName('header')[0].style.display='none';
        }
    }

    @Override
    public void showResultInWebView(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webView.removeAllViews();
        webView.destroy();
        webView=null;
    }

    @Override
    public void showLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void stopLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void setPresenter(WebContract.Presenter presenter) {
        if (presenter!=null){
            this.presenter=presenter;
        }
    }
    @Override
    public void showLoadingError() {
        Snackbar.make(coordinator,R.string.loaded_failed,Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.load();
                    }
                }).show();
    }

    @Override
    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }

    @Override
    public void showNotNetError() {
        Snackbar.make(coordinator,R.string.not_net_work,Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void showBrowserNotFoundError() {
        Snackbar.make(coordinator, R.string.no_browser_found,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showTextCopied() {
        Snackbar.make(coordinator, R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show();
    }

}
