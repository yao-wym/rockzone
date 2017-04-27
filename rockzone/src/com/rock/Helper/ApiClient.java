package com.rock.Helper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rock.common.BaiduMapUtil;
import com.rock.common.JsonUtil;
import com.rock.http.HttpRequestUtil;
import com.rock.model.User;
import com.rock.model.UserInfo;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wym on 2014/10/29.
 */
public class ApiClient {

    static  String url;

    /**
     * 轮播图获取
     * @author wym
     *
     */
    public static void getExploreAdvList(final Context context,final Handler handler, final String url) throws JSONException{
        HttpHelper.client.get(url, null, new HttpRequestUtil(context,handler,url));

    }
    /**
     * 发现按钮去id获取
     * @author wym
     *
     */
    public static void getExploreBarList(Context context,final Handler handler) throws JSONException{
        HttpHelper.client.get(URL.EXPLORE_BAR_LIST, null, new HttpRequestUtil(context,handler,URL.EXPLORE_BAR_LIST));}    /**
     * 发现页轮播图获取
     * @author wym
     *
     */
    public static void getExploreList(Context context,final Handler handler,int page,int num) throws JSONException{
        String url = URL.EXPLORE_BOTTOM_LIST+"/page/"+page+"/r/"+num;
        HttpHelper.client.get(url, null, new HttpRequestUtil(context,handler,url));

    }
    public static void getMainList(Context context,final Handler handler,int page,int num) throws JSONException{
        String url = URL.MAIN_BOTTOM_LIST+"/page/"+page+"/r/"+num;
        HttpHelper.client.get(url, null, new HttpRequestUtil(context,handler,url));

    }
    /**
     *获取广告详情
     * by wym
     */
    public static void getAdvDetail(Context context,final Handler handler,String adv_id){
        String advDetailUrl = URL.HOST_ARTICLE_DETAIL+adv_id;
        HttpHelper.client.get(advDetailUrl, null, new HttpRequestUtil(context,handler,advDetailUrl));
    }

    /**
     *获取社区列表API
     * by wym
     */
    public static void getCommunityList(final Handler handler,int page,int num){
        BaiduMapUtil.getInstance().getNearCommunity("",300000);
//        String communityList = URL.HOST_COMMUNITY_LIST+"/page/"+page+"/r/"+num;
//        HttpHelper.client.get(communityList, null, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                JSONArray communityList;
//                try {
//                    communityList = response.getJSONArray("content");
//                    Message message = new Message();
//                    message.obj = communityList;
//                    handler.sendMessage(message);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
    }
    /**
     *获取社区详细信息API
     * by wym
     */
    public static void getCommunityDetail(Context context,final Handler handler,String id){
        String url = URL.HOST_COMMUNITY_DETAIL+"/id/"+id;
            HttpHelper.client.get(url, null, new HttpRequestUtil(context,handler,id));
    }

    /**
     *获取社区分类服务列表API
     * by wym
     */
    public static void getServiceList(final Handler handler,int catId,int id){
        String serviceList = URL.HOST_COMMUNITY_SERVICE_LIST+"/cate/"+catId+"/id/"+id;
        HttpHelper.client.get(serviceList, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray serviceList;
                try {
                    serviceList = response.getJSONArray("content");
                    Message message = new Message();
                    message.obj = serviceList;
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     *获取社区分类服务详情API
     * by wym
     */
    public static void getCommunityServiceDetail(int id,int catId){
        String communityDetail = URL.HOST_COMMUNITY_SERVICE_DETAIL+"/id/"+id;
        HttpHelper.client.get(communityDetail, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                JSONObject firstEvent = null;
                try {
                    firstEvent = (JSONObject) timeline.get(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String tweetText = null;
                try {
                    tweetText = firstEvent.getString("text");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Do something with the response
                System.out.println(tweetText);
            }
        });
    }
    /**
     * 获取文章列表
     * by wym
     */
    public static void getArticleList(Context context,final Handler handler,String id,int page,int r){

        String url = URL.HOST_ARTICLE_LIST+id+"/page/"+page+"/r/"+r;
        HttpHelper.client.get(url,null,new HttpRequestUtil(context,handler,url));
    }
    /**
     * 获取文章详情
     * by wym
     */
    public static void getArticleDetail(Context context,final Handler handler,String id,String uid){

        String url = URL.HOST_ARTICLE_DETAIL+id+"/uid/"+uid;
        HttpHelper.client.get(url,null,new HttpRequestUtil(context,handler,url));
    }
    /**
     * 获取文章评论
     * by wym
     */
    public static void getArticleComment(final Handler handler,String id){

        String url = URL.HOST_ARTICLE_REPLY+id;
        HttpHelper.client.get(url,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray articleContent = response.getJSONArray("content");
                    Message message = new Message();
                    message.obj = articleContent;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
    /**
     * 获取文章评论
     * by wym
     */
//    public static void getGoodsCommentList(final Handler handler,String goodsId,int page){
//
//        String url = URL.HOST_GOODS_COMMENT_LIST+goodsId+"/page/"+page;
//        HttpHelper.client.get(url,null,new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                try {
//                    JSONArray articleContent = response.getJSONArray("content");
//                    Message message = new Message();
//                    message.obj = articleContent;
//                    handler.sendMessage(message);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
//    }

    /**
     * 获取评论
     * @param handler  回调接口
     * @param id  请求对象id
     * @param type 请求对象类别
     * @param page 页数
     */
    public static void getCommentList(final Handler handler,String id,int type,int page,int showNum){
        switch (type){
            case 1:url = URL.HOST_ARTICLE_REPLY+id+"/page/"+page+"/r/"+showNum;break;
            case 2:url = URL.HOST_GOODS_COMMENT_LIST+id+"/page/"+page+"/r/"+showNum;break;
        }

        HttpHelper.client.get(url,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray articleContent = response.getJSONArray("content");
                    Message message = new Message();
                    message.obj = articleContent;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
    /**
     *获取商品列表API
     * by wym
     */
    public static void getGoodsList(Context context,final Handler handler,int pageNum){
        String url = URL.HOST_GOODS_LIST+pageNum;
        HttpHelper.client.get(url, null, new HttpRequestUtil(context, handler, url));
    }
    /**
     *获取评论列表API
     * 1为文章评论
     * 2为商品评论
     * by wym
     */
    public static void doPostComment(final Handler handler,RequestParams params,int type){
        switch (type){
            case 1:url = URL.HOST_ARTICLE_COMMENT;break;
            case 2:url = URL.HOST_GOODS_COMMENT;break;
            default:url = URL.HOST_ARTICLE_COMMENT;
        }

        HttpHelper.client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = (String) response.get("status");
                    Message message = new Message();
                    if(code.equals("ok")){
                        message.what = 1;
                    }
                    else {
                        message.what = 0;
                    }
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
//    public static void goodsComment(final Handler handler,RequestParams params){
//            String url = URL.HOST_GOODS_COMMENT;
//        HttpHelper.client.post(url, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//                    String code = (String) response.get("status");
//                    Message message = new Message();
//                    if(code.equals("ok")){
//                        message.what = 1;
//                    }
//                    else {
//                        message.what = 0;
//                    }
//                    handler.sendMessage(message);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
    public static void collectGoods(Context context,final Handler handler,RequestParams params){
        String url = URL.HOST_GOODS_COLLECT;
        HttpHelper.client.post(url, params, new HttpRequestUtil(context,handler,url,0));
    }
    public static void collectArticle(Context context,final Handler handler,RequestParams params){
        String url = URL.HOST_ARTICLE_COLLECT;
        HttpHelper.client.post(url, params, new HttpRequestUtil(context,handler,url,0));
    }
    public static void forumPost(Context context,final Handler handler,RequestParams params){
        String url = URL.HOST_FORUM_POST;
        HttpHelper.client.post(url, params, new HttpRequestUtil(context) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = (String) response.get("status");
                    Message message = new Message();
                    if(code.equals("ok")){
                        message.what = 1;
                    }
                    else {
                        message.what = 0;
                    }
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void orderSubmit(Context context,final Handler handler,RequestParams params){
        String url = URL.HOST_GOODS_BUY;
        HttpHelper.client.post(url, params, new HttpRequestUtil(context) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = (String) response.get("status");
                    Message message = new Message();
                    if(code.equals("ok")){
                        message.what = 1;
                    }
                    else {
                        message.what = 0;
                        message.obj = response.getString("content");
                    }
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 登陆
     * @param name
     * @param password
     * @param handler
     */
   public static void login(String name,String password, final Handler handler){
	       try {
			
	    	   String loginURL = URL.HOST_LOGIN;
	    	   final RequestParams params = new RequestParams();
	    	   params.put("username", name);
	    	   params.put("password", password);
	    	   HttpHelper.client.post(loginURL,params, new JsonHttpResponseHandler() {
	    		   @Override
	    		   public void onSuccess(int statusCode, Header[] headers,
	    				   JSONObject response) {
                       Message message = new Message();
                       try {
                           String status = response.getString("status");
                           if(status.equals("ok")){
                               message.what = 1;
                               JSONArray temp = response.getJSONArray("content");
                               message.obj = temp.getJSONObject(0).getString("uid");
                           }
                           else{
                               message.what = -1;
                               message.obj = response.getString("content");
                           }
                           handler.sendMessage(message);
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }
	    		   @Override
	    		public void onFailure(int statusCode, Header[] headers,
	    				String responseString, Throwable throwable) {
	    			super.onFailure(statusCode, headers, responseString, throwable);
	    			Message message = new Message();
                       message.obj = "网络错误";
                       message.what = -1;
                       handler.sendMessage(message);
	    		}
	    		   
	    	   });
	    	   
		} catch (Exception e) {
			e.printStackTrace();
		}
			
   }
    public static void reg_valid(String url, final Handler handler,String phoneNum){
        RequestParams params = new RequestParams();
        params.put("phone",phoneNum);
        HttpHelper.client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = (String) response.get("status");
                    Message message = new Message();
                    if(code.equals("ok")){
                        message.what = 1;
                    }
                    else {
                        message.what = 0;
                    }
                    message.obj = response.get("content");
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void register(Context context, final Handler handler,RequestParams params){
        String url = URL.HOST_REG;
        HttpHelper.client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = (String) response.get("status");
                    Message message = new Message();
                    if(code.equals("ok")){
                        message.what = 1;
                    }
                    else {
                        message.what = 0;
                    }
                    message.obj = response;
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void resetValid(String url, final Handler handler,String phoneNum){
        RequestParams params = new RequestParams();
        params.put("phone",phoneNum);
        HttpHelper.client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = (String) response.get("status");
                    Message message = new Message();
                    if(code.equals("ok")){
                        message.what = 1;
                    }
                    else {
                        message.what = 0;
                    }
                    message.obj = response.get("content");
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void resetPassword(Context context, final Handler handler,RequestParams params){
        String url = URL.HOST_RESET_PWD;
        HttpHelper.client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = (String) response.get("status");
                    Message message = new Message();
                    if(code.equals("ok")){
                        message.what = 1;
                    }
                    else {
                        message.what = 0;
                    }
                    message.obj = response;
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void changePassword(Context context, final Handler handler,RequestParams params){
        String url = URL.HOST_CHANGE_PWD;
        HttpHelper.client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = (String) response.get("status");
                    Message message = new Message();
                    if(code.equals("ok")){
                        message.what = 1;
                    }
                    else {
                        message.what = 0;
                    }
                    message.obj = response;
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void getUserInfo(final Handler handler,String uid){
        String url = URL.HOST_USERCENTER_INFO+uid;
        HttpHelper.client.post(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = (String) response.get("status");
                    Message message = new Message();
                    JSONObject content = response.getJSONObject("content");
                    User user = JsonUtil.jsonToUser(content);
                    message.obj = user;
                    if(code.equals("ok")){
                        message.what = 1;
                    }
                    else {
                        message.what = 0;
                    }
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void getUserDetail(final Handler handler,String uid ){
        String url = URL.HOST_USERINFO_DETAIL+"/uid/"+uid;
        HttpHelper.client.post(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = (String) response.get("status");
                    Message message = new Message();
                    JSONObject content = response.getJSONObject("content");
                    UserInfo user = JsonUtil.jsonToUserDetail(content);
                    message.obj = user;
                    if(code.equals("ok")){
                        message.what = 1;
                    }
                    else {
                        message.what = 0;
                    }
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void userInfoEdit(Context context,final Handler handler,RequestParams requestParams){
        String url = URL.HOST_USERINFO_EDIT;
        HttpHelper.client.post(url, requestParams,new HttpRequestUtil(context,handler,url) );
    }
    public static void getUserMessage(final Handler handler,String uid,int pageNum,int showNum){
        String url = URL.HOST_USERCENTER_MESSAGE+uid+"/page/"+pageNum+"/r/"+showNum;
        HttpHelper.client.post(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = (String) response.get("status");
                    Message message = new Message();
                    JSONArray content = response.getJSONArray("content");
                    message.obj = content;
                    if(code.equals("ok")){
                        message.what = 1;
                    }
                    else {
                        message.what = 0;
                    }
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void getArticleCollection(Context context,final Handler handler,String uid,int pageNum){
        String url = URL.HOST_ARTICLE_COLLECT_LIST+"/uid/"+uid+"/page/"+pageNum;
        HttpHelper.client.post(url, new HttpRequestUtil(context,handler,url) );
    }
    public static void getGoodsCollection(Context context,final Handler handler,String uid,int pageNum){
        String url = URL.HOST_GOODS_COLLECT_LIST+"/uid/"+uid+"/page/"+pageNum;
        HttpHelper.client.post(url, new HttpRequestUtil(context,handler,url) );
    }
    public static void getUserOrderList(final Handler handler,String uid,int pageNum,int r){

        String url = URL.HOST_ORDER_LIST+"/uid/"+uid+"/page/"+pageNum+"/r/"+r;
        HttpHelper.client.post(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String code = (String) response.get("status");
                    Message message = new Message();
                    JSONArray content = response.getJSONArray("content");
                    message.obj = content;
                    if (code.equals("ok")) {
                        message.what = 1;
                    } else {
                        message.obj = content;
                        message.what = 0;
                    }
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 获取商品详情
     * by wym
     */
    public static void getGoodsDetail(Context context,final Handler handler,String id,String uid){

        String url = URL.HOST_GOODS_DETAIL+id+"/uid/"+uid;
        HttpHelper.client.get(url,null,new HttpRequestUtil(context,handler,url));
    }
    /**
     * 设置用户头像
     * by wym
     */
    public static void setUserIcon(Context context,final Handler handler,RequestParams requestParams){

        String url = URL.HOST_USERINFO_ICON_EDIT;
        HttpHelper.client.post(url,requestParams,new HttpRequestUtil(context,handler,url));
    }
}
