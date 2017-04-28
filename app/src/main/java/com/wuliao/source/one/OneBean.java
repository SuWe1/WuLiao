package com.wuliao.source.one;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Swy on 2017/4/16.
 */

public class OneBean extends RealmObject{

    /**
     * id : 11166
     * category : 1
     * display_category : 6
     * item_id : 2252
     * title : 我们活着，却宛若死了
     * forward : 我们很有可能正在叩响另一个世界的大门，那个世界名叫地狱。
     * img_url : http://image.wufazhuce.com/FuTBFfE6KvhpiDmQQ5AJxWAvgQMc
     * like_count : 138
     * post_date : 2017-04-16 07:00:00
     * last_update_date : 2017-04-15 20:58:30
     * author : {"user_id":"7784221","user_name":"理想国imaginist","desc":"知名文化品牌\u201c理想国\u201d旗下微信公号。想象文化与生活的另一种可能。","wb_name":"","is_settled":"0","settled_type":"0","summary":"id：lixiangguo2013","fans_total":"828","web_url":"http://image.wufazhuce.com/FqwlLLGAIhgRlE4wh2_8V4WhJ0Q9"}
     */

    @PrimaryKey
    private String id;
    @Ignore
    private String category;
    @Ignore
    private int display_category;
    private String item_id;
    private String title;
    private String forward;
    private String img_url;
    private int like_count;
    private String post_date;
    @Ignore
    private String last_update_date;
    private String share_url;
    private AuthorBean author;

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDisplay_category() {
        return display_category;
    }

    public void setDisplay_category(int display_category) {
        this.display_category = display_category;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

}
