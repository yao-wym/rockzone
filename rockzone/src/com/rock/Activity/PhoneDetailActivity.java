package com.rock.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rock.Helper.ApiClient;
import com.rock.Helper.MyImageLoader;
import com.rock.Helper.UIHelper;
import com.rock.Listener.CommentCickListener;
import com.rock.Listener.HeadImageLoadingListener;
import com.rock.Listener.MyImageLoadingProgressListener;
import com.rock.common.JsonUtil;
import com.rock.common.TimeUtil;
import com.rock.model.Article;
import com.rock.model.Comment;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by wym on 2014/11/6.
 */
public class PhoneDetailActivity extends BaseActivity {
    private String htmlContent;
    ArrayList<Comment> commentArrayList;
    private Handler commentHandler;
    private LayoutInflater inflater;
    private String articleId;
    private Comment comment;
    private LinearLayout LLzan;
    private TextView TVdescribe;
    private LinearLayout LLjubao;
    private LinearLayout LLcall;
    private ImageView articleIcon;
    private Article article;
    private ArrayList<Article> articleArrayList;
    private Button BTcollect;
    private TextView TVphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_phone_detail);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.rock_zone_circle_icon) // resource or drawable
                .showImageOnFail(R.drawable.rock_zone_circle_icon) // resource or drawable
                .showImageOnLoading(R.drawable.loading_rotate) // resource or drawable
                .build();
        initialHeader("号码详情");
        context = this;
        UIHelper.showProgressDialog(context);
        initialHeader();
        inflater = LayoutInflater.from(context);
        article = new Article();
        articleId = getIntent().getStringExtra("id");
        initialView(articleId);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    protected void initialView(final String id){
        Handler handler = getHandler();
        LLcall = (LinearLayout) findViewById(R.id.call);
        Button comment = (Button) findViewById(R.id.comment_btn);
        BTcollect = (Button) findViewById(R.id.collect_btn);
        LLjubao = (LinearLayout) findViewById(R.id.jubao);
        LLzan = (LinearLayout) findViewById(R.id.dianzan);
        articleIcon = (ImageView) findViewById(R.id.author_icon);
        TVdescribe = (TextView)findViewById(R.id.describe);
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.comment_btn:
                        UIHelper.showArticleCommentView(context,id);break;
                    case R.id.dianzan:
                        Toast.makeText(context,"暂未开放点赞功能",3).show();break;
                    case R.id.jubao:
                        Toast.makeText(context,"收到报错信息，我们将尽快核实",3).show();break;
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
                if(msg.what==-1){
                    return;
                }
                TVphone = (TextView) findViewById(R.id.phone);
                TextView titleView = (TextView)findViewById(R.id.article_title);
                TextView authorView = (TextView)findViewById(R.id.article_author);
                TextView timeView = (TextView)findViewById(R.id.create_time);
                TextView commentView = (TextView)findViewById(R.id.comment_num);
                jsonArray = (JSONArray) msg.obj;
                try {
                    content = jsonArray.getJSONObject(0);
                    article = JsonUtil.jsonToArticle(content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setCollectBtn();
                LLcall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String strMobile = TVphone.getText().toString();
                        //todo 此处应该对电话号码进行验证。。
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + strMobile));

                        PhoneDetailActivity.this.startActivity(intent);
                    }
                });
                commentView.setText(article.getCommentNum());
                authorView.setText("提交人："+article.getAuthor());
                timeView.setText("更新时间："+TimeUtil.getTime(Integer.parseInt(article.getUpdateTime())));
                titleView.setText(article.getTitle());
                TVdescribe.setText(article.getDescription());
                if(article.getCoverUrl()!=null&&!article.getCoverUrl().equals("")){
                    MyImageLoader.getInstance().displayImage(article.getCoverUrl(),articleIcon);
                }
                TVphone.setText(article.getContent());
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
                    ImageLoader.getInstance().displayImage(comment.getAuthorIcon(),IVuserIcon,options,new HeadImageLoadingListener(),new MyImageLoadingProgressListener());
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
        }else{
            BTcollect.setBackgroundDrawable(getResources().getDrawable(R.drawable.post_collected));
        }
    }
    public void initialHeader(String title){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText(title);
    }
}
