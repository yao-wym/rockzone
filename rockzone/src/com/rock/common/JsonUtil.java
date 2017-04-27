package com.rock.common;
import com.rock.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wym on 2014/11/24.
 */
public class JsonUtil{
    public static User jsonToUser(JSONObject jsonObject){
        User user = new User();
        user.setUid(jsonObject.optString("uid"));
        user.setAvatar128(jsonObject.optString("avatar128"));
        user.setFans(jsonObject.optString("fans"));
        user.setFollowing(jsonObject.optString("following"));
        user.setNickname(jsonObject.optString("nickname"));
        user.setScore(jsonObject.optString("score"));
        user.setSignature(jsonObject.optString("signature"));
        user.setTitle(jsonObject.optString("title"));
        user.setWeibocount(jsonObject.optString("weibocount"));
        return user;
    }
    public static UserInfo jsonToUserDetail(JSONObject jsonObject){
        UserInfo user = new UserInfo();
        user.setUid(jsonObject.optString("uid"));
        user.setHobby(jsonObject.optString("hobby"));
        user.setHometown(jsonObject.optString("jiaxiang"));
        user.setIntroduction(jsonObject.optString("introduction"));
        user.setNickname(jsonObject.optString("nickname"));
        user.setQinggan(jsonObject.optString("qinggan"));
        user.setSex(jsonObject.optString("sex"));
        user.setSignature(jsonObject.optString("signature"));
        user.setXingzuo(jsonObject.optString("xingzuo"));
        return user;
    }
    public static Goods jsonToGoods(JSONObject jsonObject){
        Goods goods = new Goods();
        goods.setgoodsId(jsonObject.optString("id"));
        goods.setgoodsName(jsonObject.optString("goods_name"));
        goods.setSellNum(jsonObject.optString("sell_num"));
        goods.setGoodsNum(jsonObject.optString("goods_num"));
        goods.setgoodsInstruct(jsonObject.optString("goods_introduct"));
        goods.setgoodsIconUrl(jsonObject.optString("goods_ico"));
        goods.setgoodsCost(jsonObject.optString("tox_money_need"));
        goods.setgoodsDetail(jsonObject.optString("goods_detail"));
        goods.setCategory(jsonObject.optString("category"));
        goods.setCreateTime(jsonObject.optString("createtime"));
        goods.setUpdateTime(jsonObject.optString("changetime"));
        goods.setIsCollect(jsonObject.optString("is_collect"));
        return goods;
    }
    public static ArrayList<Goods> jsonArrToGoodsList(JSONArray jsonArray) throws JSONException {
        ArrayList<Goods> goodsList = new ArrayList<Goods>();
        if(jsonArray !=null){
            for(int i=0;i<jsonArray.length();i++){
                goodsList.add(jsonToGoods(jsonArray.getJSONObject(i)));
            }
        }
        return goodsList;
    }
    public static Article jsonToArticle(JSONObject jsonObject){
        Article article = new Article();
        article.setArticleId(jsonObject.optString("id"));
        article.setAuthorId(jsonObject.optString("uid"));
        article.setCommentNum(jsonObject.optString("comment"));
//        article.(jsonObject.optString("name"));
        article.setTitle(jsonObject.optString("title"));
        article.setDescription(jsonObject.optString("description"));
//        article.setgoodsDetail(jsonObject.optString("type"));
        article.setviewNum(jsonObject.optString("view"));
        article.setCreateTime(jsonObject.optString("create_time"));
        article.setUpdateTime(jsonObject.optString("update_time"));
        article.setContent(jsonObject.optString("content"));
//        article.setB(jsonObject.optString("bookmark"));
        article.setCoverUrl(jsonObject.optString("cover_id"));
        article.setCategory(jsonObject.optString("category"));
        article.setAuthor(jsonObject.optString("author"));
        if(jsonObject.optString("is_collect").equals("-1")){
            article.setIsCollected(false);
        }else {
            article.setIsCollected(true);
        }
        return article;
    }
    public static ArrayList<Article> jsonArrToArticleList(JSONArray jsonArray) throws JSONException {
        ArrayList<Article> articleList = new ArrayList<Article>();
        if(jsonArray == null){
            return null;
        }
        for(int i=0;i<jsonArray.length();i++){
            articleList.add(jsonToArticle(jsonArray.getJSONObject(i)));
        }
        return articleList;
    }
    public static Comment jsonToComment(JSONObject jsonObject){
        Comment comment = new Comment();
        comment.setContent(jsonObject.optString("content"));
        comment.setAuthorNickname(jsonObject.optString("author_nickname"));
//        article.(jsonObject.optString("name"));
        comment.setAuthorUsername(jsonObject.optString("author_username"));
        comment.setCreateTime(jsonObject.optString("create_time"));
//        article.setgoodsDetail(jsonObject.optString("type"));
        comment.setId(jsonObject.optString("id"));
        comment.setUid(jsonObject.optString("uid"));
        comment.setAuthorIcon(jsonObject.optString("author_logo"));
        return comment;
    }
    public static ArrayList<Comment> jsonArrToCommentList(JSONArray jsonArray) throws JSONException {
        ArrayList<Comment> commentArrayList = new ArrayList<Comment>();
        if(jsonArray == null){
            return null;
        }
        for(int i=0;i<jsonArray.length();i++){
            commentArrayList.add(jsonToComment(jsonArray.getJSONObject(i)));
        }
        return commentArrayList;
    }
    public static UserMessage jsonToMessage(JSONObject jsonObject){
        UserMessage userMessage = new UserMessage();
        userMessage.setId(jsonObject.optString("id"));
        userMessage.setFromUid(jsonObject.optString("from_uid"));
        userMessage.setTitle(jsonObject.optString("title"));
        userMessage.setContent(jsonObject.optString("content"));
        userMessage.setIconUrl(jsonObject.optString("url"));
        userMessage.setCreateTime(jsonObject.optString("create_time"));
        userMessage.setIsRead(jsonObject.optString("is_read"));
        userMessage.setType(jsonObject.optString("type"));
        userMessage.setFromUser(jsonObject.optJSONObject("from_user"));

        return userMessage;
    }
    public static ArrayList<UserMessage> jsonArrToMessageList(JSONArray jsonArray) throws JSONException {
        ArrayList<UserMessage> userMessageArrayList = new ArrayList<UserMessage>();
        if(jsonArray == null){
            return null;
        }
        for(int i=0;i<jsonArray.length();i++){
            userMessageArrayList.add(jsonToMessage(jsonArray.getJSONObject(i)));
        }
        return userMessageArrayList;
    }
}

