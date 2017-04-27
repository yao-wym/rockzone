package com.rock.model;

import java.io.Serializable;

public class Article implements Serializable {
    private String ArticleId;              //文章id
    private String uid;             //文章发表用户id
    private String title;           //标题
    private String description;           //文章简介
    private String viewNum;            //浏览量
    private String commentNum;         //评价数
    private String createTime;     //创建时间
    private String updateTime;     //上次更新时间
    private String content;         //文章内容
    private String coverUrl;           //文章封面pic链接
    private String category;        //文章父类
    private String author;          //文章作者
    private String authorId;          //文章作者id
    private boolean isCollected;          //是否被收藏

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getArticleId() {
        return ArticleId;
    }

    public void setArticleId(String ArticleId) {
        this.ArticleId = ArticleId;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setviewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }
}

