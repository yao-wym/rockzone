package com.rock.model;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String uid;
    private String nickname;// 昵称

    public String getUsername() {
        return username;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSex() {

        return sex;
    }

    private String username;// 昵称
    private String job; // 工作
    private String hometown; // 家乡
    private String xingzuo; // 星座
    private String qinggan;// 情感状态
    private String hobby;// 爱好
    private String introduction;// 个人介绍
    private String sex;// 性别
    private String signature;// 个人签名
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getJob() {
        return job;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getXingzuo() {
        return xingzuo;
    }

    public void setXingzuo(String xingzuo) {
        this.xingzuo = xingzuo;
    }

    public String getQinggan() {
        return qinggan;
    }
    public void setQinggan(String qinggan) {
        this.qinggan = qinggan;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }
}

