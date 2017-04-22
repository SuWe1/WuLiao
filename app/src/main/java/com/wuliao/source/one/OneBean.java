package com.wuliao.source.one;

/**
 * Created by Swy on 2017/4/16.
 */

public class OneBean {

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

    private String id;
    private String category;
    private int display_category;
    private String item_id;
    private String title;
    private String forward;
    private String img_url;
    private int like_count;
    private String post_date;
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

    public static class AuthorBean {
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

        private String user_id;
        private String user_name;
        private String desc;
        private String wb_name;
        private String is_settled;
        private String settled_type;
        private String summary;
        private String fans_total;
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
}
