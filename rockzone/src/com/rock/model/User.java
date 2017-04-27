package com.rock.model;

import java.io.Serializable;

public class User implements Serializable {
    private String uid;
    private String nickname;// 昵称
    private String score; // 性别 1:男 2:女
    private String signature; // 所在城市
    private String avatar128; // 头像
    private String title;// 积分
    private String rank_link;// 注册时间
    private String icons_html;// 绑定邮箱
    private String following;// 是否是第一次第三方登录
    private String weibocount;// 是否是第一次第三方登录
    private String fans;// 是否是第一次第三方登录
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getIcons_html() {
        return icons_html;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadPic() {
        return avatar128;
    }

    public void setAvatar128(String avatar128) {
        this.avatar128 = avatar128;
    }

    public String getWeibocount() {
        return weibocount;
    }

    public void setWeibocount(String weibocount) {
        this.weibocount = weibocount;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public void setRank_link(String rank_link) {
        this.rank_link = rank_link;
    }

    public String getAvatar128() {

        return avatar128;
    }

    public String getFans() {

        return fans;
    }
}

