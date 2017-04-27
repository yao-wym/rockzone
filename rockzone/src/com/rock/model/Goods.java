package com.rock.model;

import java.io.Serializable;

public class Goods implements Serializable {
    private String goodsId;                 //商品id
    private String goodsName;               //商品名
    private String goodsIconUrl;            //商品缩略图
    private String goodsInstruct;           //商品介绍
    private String goodsDetail;             //商品描述
    private String goodsCost;               //商品所需积分
    private String goodsNum;                //商品剩余
    private String updateTime;              //更新时间
    private String createTime;              //创建时间
    private String isNew;                   //是否为新商品
    private String sellNum;                 //商品销量
    private String category;                //商品所属类别
    private String status;                  //商品状态
    private String isCollect;              //收藏状态（没有收藏为-1，否则返回>0）


    public String getgoodsId() {
        return goodsId;
    }

    public void setgoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getgoodsName() {
        return goodsName;
    }

    public void setgoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getgoodsIconUrl() {
        return goodsIconUrl;
    }

    public void setgoodsIconUrl(String goodsIconUrl) {
        this.goodsIconUrl = goodsIconUrl;
    }

    public String getgoodsInstruct() {
        return goodsInstruct;
    }

    public void setgoodsInstruct(String goodsInstruct) {
        this.goodsInstruct = goodsInstruct;
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

    public String getgoodsDetail() {
        return goodsDetail;
    }

    public void setgoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getisNew() {
        return isNew;
    }

    public void setContent(String isNew) {
        this.isNew = isNew;
    }

    public String getgoodsCost() {
        return goodsCost;
    }

    public void setgoodsCost(String goodsCost) {
        this.goodsCost = goodsCost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getSellNum() {
        return sellNum;
    }

    public void setSellNum(String sellNum) {
        this.sellNum = sellNum;
    }
}

