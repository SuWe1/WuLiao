package com.wuliao.source;

/**
 * Created by Swy on 2017/4/1.
 */

public class TuringBean<T> {
    private int code;
    private String text;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
