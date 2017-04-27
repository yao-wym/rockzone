package com.rock.model;

import java.io.Serializable;

public class Comment implements Serializable {
    private String id;              //评论id
    private String uid;             //评论用户id
    private String content;           //评论内容
    private String createTime;            //评论时间
    private String authorNickname;         //评论作者
    private String authorUsername;     //评论作者手机号

    public void setAuthorIcon(String authorIcon) {
        this.authorIcon = authorIcon;
    }

    public String getAuthorIcon() {
        return authorIcon;

    }

    private String authorIcon;     //评论作者头像

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getContent() {
        return content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getAuthorNickname() {
        return authorNickname;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthorNickname(String authorNickname) {
        this.authorNickname = authorNickname;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

