package com.rock.model;

import org.json.JSONObject;

import java.io.Serializable;

public class UserMessage implements Serializable {
    private String id;              //评论id
    private String fromUid;             //用户id
    private String content;             //用户id

    public String getContent() {
        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    private String title;           //标题
    private String iconUrl;            //消息图片
    private String createTime;         //时间
    private String isRead;     //是否已读
    private String type;     //消息类型
    private FromUser fromUser;     //发送消息的人的类

    public class FromUser{
        private String username;         //消息源用户username
        private String headIcon;     //消息源用户头像
        private String uid;     //消息源用户uid
        public String getUid() {
            return uid;
        }

        public String getUsername() {
            return username;
        }

        public String getHeadIcon() {
            return headIcon;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setHeadIcon(String headIcon) {
            this.headIcon = headIcon;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserMessage() {
        this.fromUser = new FromUser();
    }

    public void setFromUser(JSONObject jsonfromUser) {
        if(jsonfromUser!=null){
            if(this.fromUser == null){
                this.fromUser = new FromUser();
            }

            this.fromUser.setHeadIcon(jsonfromUser.optString("avatar64"));
            this.fromUser.setUid( jsonfromUser.optString("uid"));
            this.fromUser.setUsername(jsonfromUser.optString("username"));
        }
    }

    public String getId() {

        return id;
    }

    public String getFromUid() {
        return fromUid;
    }

    public String getTitle() {
        return title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getIsRead() {
        return isRead;
    }

    public String getType() {
        return type;
    }

    public FromUser getFromUser() {
        if(fromUser == null){
            fromUser = new FromUser();
        }
        return fromUser;
    }
}

