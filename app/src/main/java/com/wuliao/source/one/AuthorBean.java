package com.wuliao.source.one;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Swy on 2017/4/27.
 */

public  class AuthorBean extends RealmObject{
    /**
     * user_id : 7784221
     * user_name : 理想国imaginist
     * desc : 知名文化品牌“理想国”旗下微信公号。想象文化与生活的另一种可能。
     * wb_name :
     * is_settled : 0
     * settled_type : 0
     * summary : id：lixiangguo2013
     * fans_total : 828
     * web_url : http://image.wufazhuce.com/FqwlLLGAIhgRlE4wh2_8V4WhJ0Q9
     */
    @PrimaryKey
    private String user_id;
    private String user_name;
    @Ignore
    private String desc;
    @Ignore
    private String wb_name;
    @Ignore
    private String is_settled;
    @Ignore
    private String settled_type;
    @Ignore
    private String summary;
    @Ignore
    private String fans_total;
    @Ignore
    private String web_url;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWb_name() {
        return wb_name;
    }

    public void setWb_name(String wb_name) {
        this.wb_name = wb_name;
    }

    public String getIs_settled() {
        return is_settled;
    }

    public void setIs_settled(String is_settled) {
        this.is_settled = is_settled;
    }

    public String getSettled_type() {
        return settled_type;
    }

    public void setSettled_type(String settled_type) {
        this.settled_type = settled_type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFans_total() {
        return fans_total;
    }

    public void setFans_total(String fans_total) {
        this.fans_total = fans_total;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }
}
