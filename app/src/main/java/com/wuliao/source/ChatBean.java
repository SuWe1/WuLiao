package com.wuliao.source;

/**
 * Created by Swy on 2017/4/1.
 */

public class ChatBean {
    public static final int TYPE_RIGHT = 1;
    public static final int TYPE_LEFT = 0;

    private int type;
    private String text;


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
