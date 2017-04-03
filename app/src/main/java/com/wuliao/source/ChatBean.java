package com.wuliao.source;

/**
 * Created by Swy on 2017/4/1.
 */

public class ChatBean {
    public static final int TYPE_LEFT = 0;
    public static final int TYPE_RIGHT = 1;

    public static final int VIEW_TEXT=0;
    public static final int VIEW_URL=1;
    public static final int VIEW_LIST=2;

    private int type;
    private String text;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private int view;

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
