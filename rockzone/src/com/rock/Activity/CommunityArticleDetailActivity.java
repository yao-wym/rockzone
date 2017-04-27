package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
import com.rock.Listener.CommentCickListener;
import com.rock.Listener.HeadImageLoadingListener;
import com.rock.Listener.MyImageLoadingProgressListener;
import com.rock.common.JsonUtil;
import com.rock.common.TimeUtil;
import com.rock.model.Article;
import com.rock.model.Comment;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by wym on 2014/11/6.
 */
public class CommunityArticleDetailActivity extends BaseActivity implements MenuItem.OnMenuItemClickListener {
    private String htmlContent;
    ArrayList<Comment> commentArrayList;
    private Handler commentHandler;
    private LayoutInflater inflater;
    private String articleId;
    private Comment comment;
    private String title;
    private LinearLayout LLzan;
    private LinearLayout LLjubao;
    private Article article;
    private ArrayList<Article> articleArrayList;
    private Button BTcollect;
    private TextView TVcollect;
    private UMSocialService mController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_article_detail);
        article = new Article();
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.rock_zone_circle_icon) // resource or drawable
                .showImageOnFail(R.drawable.rock_zone_circle_icon) // resource or drawable
                .showImageOnLoading(R.drawable.loading_rotate) // resource or drawable
                .build();
        mController = UMServiceFactory.getUMSocialService(getString(R.string.share_service));
// 设置分享内容
        title = getIntent().getStringExtra("title");
        initialHeader(title);
        inflater = LayoutInflater.from(context);
        articleId = getIntent().getStringExtra("id");
        initialView(articleId);
        if(!CommunityArticleDetailActivity.this.isFinishing()){
            UIHelper.showProgressDialog(CommunityArticleDetailActivity.this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    protected void initialView(final String id){
        Handler handler = getHandler();
        Button comment = (Button) findViewById(R.id.comment_btn);
        BTcollect = (Button) findViewById(R.id.collect_btn);
        TVcollect = (TextView) findViewById(R.id.collect_text);
        LLjubao = (LinearLayout) findViewById(R.id.jubao);
        LLzan = (LinearLayout) findViewById(R.id.dianzan);
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.comment_btn:
                        UIHelper.showArticleCommentView(context,id);break;
                    case R.id.dianzan:
                        Toast.makeText(context,"暂未开放点赞功能",3).show();break;
                    case R.id.jubao:
                        Toast.makeText(context,"已经收到举报",3).show();break;
                    case R.id.collect_btn:
                        if(isLogin!=true){
                            Toast.makeText(context,"请先登陆",3).show();
                            return;
                        }
                        RequestParams requestParams = new RequestParams();
                        requestParams.put("article_id",article.getArticleId());
                        requestParams.put("uid",uid);
                        if(article.getIsCollected()==false){
                            requestParams.put("add","1");
                        }else{
                            requestParams.put("add","0");
                        }
                        ApiClient.collectArticle(context, getCollectHandler(), requestParams);
                }
            }
        };
        LLjubao.setOnClickListener(listener);
        LLzan.setOnClickListener(listener);
        comment.setOnClickListener(listener);
        BTcollect.setOnClickListener(listener);
        ApiClient.getArticleDetail(context,handler,id,uid);
    }
    public Handler getCollectHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1){
                    Toast.makeText(context, (String) msg.obj, 3).show();
                    article.setIsCollected(!article.getIsCollected());
                    setCollectBtn();
                }else {
                    Toast.makeText(context, (String) msg.obj, 3).show();
                }
            }
        };
    }
    protected Handler getHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                UIHelper.dismissProgressDialog();
                if (msg.what == -1){
                    return;
                }
                WebView articleContent = (WebView) findViewById(R.id.article_content);
                TextView titleView = (TextView)findViewById(R.id.article_title);
                TextView createTime = (TextView)findViewById(R.id.create_time);
                TextView authorView = (TextView)findViewById(R.id.article_author);
                TextView commentView = (TextView)findViewById(R.id.comment_num);
                jsonArray = (JSONArray) msg.obj;
                try {
                    content = jsonArray.getJSONObject(0);
                    article = JsonUtil.jsonToArticle(content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setCollectBtn();
//                commentView.setText(article.getCommentNum());
                authorView.setText(article.getAuthor());
                titleView.setText(article.getTitle());
                createTime.setText(TimeUtil.getTime(Integer.parseInt(article.getCreateTime())));
                articleContent.loadDataWithBaseURL(null,article.getContent(), "text/html", "UTF-8", null);
                loadComment();
            }
        };
    }
    private void loadComment(){
        commentHandler = getCommentHandler();
        ApiClient.getArticleComment(commentHandler,articleId);
    }
    protected Handler getCommentHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                JSONArray comments = (JSONArray) msg.obj;
                try {
                     commentArrayList = JsonUtil.jsonArrToCommentList(comments);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LinearLayout commentList = (LinearLayout) findViewById(R.id.comment_list);
                for(Comment comment : commentArrayList){
                        RelativeLayout commentItem = (RelativeLayout) inflater.inflate(R.layout.article_comment_item,null);

                        TextView commentContent = (TextView) commentItem.findViewById(R.id.comment_content);
                        TextView createTime = (TextView) commentItem.findViewById(R.id.create_time);
                        TextView replyUid = (TextView) commentItem.findViewById(R.id.comment_author);
                        ImageView IVuserIcon = (ImageView)commentItem.findViewById(R.id.comment_user_icon);
//                        comment = commentArrayList.get(i);
                        String timeStr = TimeUtil.getTime(Integer.parseInt(comment.getCreateTime()));
                        commentContent.setText(comment.getContent());
                        String author = comment.getAuthorNickname();
                    if(author!=null&&!author.equals("null")&&!author.equals("")){
                        replyUid.setText(author);
                    }else {
                        replyUid.setText("游客");
                    }
                    ImageLoader.getInstance().displayImage(comment.getAuthorIcon(), IVuserIcon,options,new HeadImageLoadingListener(),new MyImageLoadingProgressListener());
                        createTime.setText(timeStr);
                        commentItem.setOnClickListener(new CommentCickListener(context,articleId,comment.getAuthorNickname()));
                        commentList.addView(commentItem);

                }
            }
        };
    }
    public void setCollectBtn(){
        if(article.getIsCollected()==false){
            BTcollect.setBackgroundDrawable(getResources().getDrawable(R.drawable.post_collect));
            TVcollect.setText("收藏");
        }else{
            BTcollect.setBackgroundDrawable(getResources().getDrawable(R.drawable.post_collected));
            TVcollect.setText("已收藏");
        }
    }
    public void initialHeader(String title){
        headRight = (ImageButton)findViewById(R.id.head_right);
        headLeft = (ImageButton)findViewById(R.id.head_left);
        headMiddle = (RelativeLayout)findViewById(R.id.head_middle);
        headLeft.setImageResource(R.drawable.fanhui);
        headRight.setImageResource(R.drawable.share);
        headRight.setBackgroundResource(R.drawable.red_btn_stl);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.head_left:
                        finish();
                        break;
                    case R.id.head_right:
                    {

                        mController = UMServiceFactory.getUMSocialService(getString(R.string.share_login));
                        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
                        mController.getConfig().setSsoHandler(new SinaSsoHandler());
                        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(CommunityArticleDetailActivity.this,getString(R.string.qq_app_id),
                                getString(R.string.qq_app_key));
                        qZoneSsoHandler.addToSocialSDK();
                        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(CommunityArticleDetailActivity.this, getString(R.string.qq_app_id),
                                getString(R.string.qq_app_key));
                        qqSsoHandler.addToSocialSDK();
// 添加微信平台
                        UMWXHandler wxHandler = new UMWXHandler(CommunityArticleDetailActivity.this,getString(R.string.weixin_app_id),getString(R.string.weixin_app_key));
                        wxHandler.addToSocialSDK();
// 支持微信朋友圈
                        UMWXHandler wxCircleHandler = new UMWXHandler(CommunityArticleDetailActivity.this,getString(R.string.weixin_app_id),getString(R.string.weixin_app_key));
                        wxCircleHandler.setToCircle(true);
                        wxCircleHandler.addToSocialSDK();
//设置微信朋友圈分享内容
                        CircleShareContent circleMedia = new CircleShareContent();
                        circleMedia.setShareContent(getString(R.string.share_content));
//设置朋友圈title
                        circleMedia.setTitle(getString(R.string.share_title));
                        circleMedia.setTargetUrl(getString(R.string.share_url));
                        mController.setShareMedia(circleMedia);




                        mController.setShareContent(getString(R.string.share_content));
// 设置分享图片, 参数2为图片的url地址
                        mController.setShareMedia(new UMImage(context, getString(R.string.share_icon)));
                        mController.openShare((android.app.Activity) context, false);

                        //todo 弹出式菜单分享
//                View menu = inflater.inflate(R.layout.popmenu, null);
//
//
//                PopupWindow popupWindow = new PopupWindow(menu,200, LinearLayout.LayoutParams.WRAP_CONTENT);
//                // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
//                popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
//                popupWindow.showAsDropDown(view,0, 1);
//                // 使其聚集
//                popupWindow.setFocusable(true);
//// 设置允许在外点击消失
//                popupWindow.setOutsideTouchable(true);
////刷新状态（必须刷新否则无效）
//                popupWindow.update();
                    }

                }
            }
        };
        headLeft.setOnClickListener(listener);
        headRight.setOnClickListener(listener);
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText(title);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @Override
    public void onRightClick() {
        mController = UMServiceFactory.getUMSocialService(getString(R.string.share_login));
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(CommunityArticleDetailActivity.this,getString(R.string.qq_app_id),
                getString(R.string.qq_app_key));
        qZoneSsoHandler.addToSocialSDK();
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(CommunityArticleDetailActivity.this, getString(R.string.qq_app_id),
                getString(R.string.qq_app_key));
        qqSsoHandler.addToSocialSDK();
// 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(CommunityArticleDetailActivity.this,getString(R.string.weixin_app_id),getString(R.string.weixin_app_key));
        wxHandler.addToSocialSDK();
// 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(CommunityArticleDetailActivity.this,getString(R.string.weixin_app_id),getString(R.string.weixin_app_key));
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
//设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(getString(R.string.share_content));
//设置朋友圈title
        circleMedia.setTitle(getString(R.string.share_title));
        circleMedia.setTargetUrl(getString(R.string.share_url));
        mController.setShareMedia(circleMedia);




        mController.setShareContent(getString(R.string.share_content));
// 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(context, getString(R.string.share_icon)));
        mController.openShare((android.app.Activity) context, false);
    }
}
