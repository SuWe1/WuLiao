package com.wuliao.source;

/**
 * Created by Swy on 2017/4/1.
 * 目前只对文本、链接格式的数据进行获取 没有对list数据(新闻列表等)进行处理
 */

public class TuringBean {
    private int code;
    private String text;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
