package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
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
;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by wym on 2014/11/6.
 */
public class ForumPostDetailActivity extends BaseActivity {
    private String articleContent;
    private String htmlContent;
    private Handler commentHandler;
    private LayoutInflater inflater;
    private String postId;
    private Article article;
    private TextView TVcommentNum;
    private LinearLayout LLzan;
    private LinearLayout LLjubao;
    private Comment comment;
    private ArrayList<Article> articleArrayList;
    private Button BTcollect;
    private TextView TVcollect;
    private ArrayList<Comment> commentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_post_detail);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.rock_zone_circle_icon) // resource or drawable
                .showImageOnFail(R.drawable.rock_zone_circle_icon) // resource or drawable
                .showImageOnLoading(R.drawable.loading_rotate) // resource or drawable
                .build();
        initialHeader("帖 子");
        article = new Article();
        inflater = LayoutInflater.from(this);
        postId = getIntent().getStringExtra("postId");
        initialView(postId);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    protected void initialView(final String id){
        Handler handler = getHandler();
        Button comment = (Button)findViewById(R.id.comment_btn);
        TVcommentNum = (TextView) findViewById(R.id.comment_num);
        BTcollect = (Button) findViewById(R.id.collect_btn);
        TVcollect = (TextView) findViewById(R.id.collect_text);
        LLjubao = (LinearLayout) findViewById(R.id.jubao);
        LLzan = (LinearLayout) findViewById(R.id.dianzan);
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.comment_btn:
                        UIHelper.showArticleCommentView(context,postId);break;
                    //todo 点赞与举报功能
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
                if(msg.what==-1){
                    return;
                }
                WebView articleContent = (WebView) findViewById(R.id.article_content);
                TextView titleView = (TextView)findViewById(R.id.article_title);
                TextView createView = (TextView)findViewById(R.id.create_time);
                TextView authorView = (TextView)findViewById(R.id.article_author);
                jsonArray = (JSONArray) msg.obj;
                try {
                    content = jsonArray.getJSONObject(0);
                    article = JsonUtil.jsonToArticle(content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setCollectBtn();
                createView.setText(TimeUtil.getTime(Integer.parseInt(article.getCreateTime())));
                authorView.setText(article.getAuthor());
                titleView.setText(article.getTitle());
                articleContent.loadData(article.getContent(), "text/html; charset=UTF-8", null);
                loadComment();
            }
        };
    }
    private void loadComment(){
        commentHandler = getCommentHandler();
        ApiClient.getArticleComment(commentHandler,postId);
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
                for(int i=0;i<comments.length();i++){
                    RelativeLayout commentItem = (RelativeLayout) inflater.inflate(R.layout.article_comment_item,null);
                    ImageView IVuserIcon = (ImageView) commentItem.findViewById(R.id.comment_user_icon);
                    TextView commentContent = (TextView) commentItem.findViewById(R.id.comment_content);
                    TextView createTime = (TextView) commentItem.findViewById(R.id.create_time);
                    TextView replyUid = (TextView) commentItem.findViewById(R.id.comment_author);
                    comment = commentArrayList.get(i);
                    String timeStr = TimeUtil.getTime(Integer.parseInt(comment.getCreateTime()));
                    commentContent.setText(comment.getContent());
                    String author = comment.getAuthorNickname();
                    if(author!=null&&!author.equals("null")&&!author.equals("")){
                        replyUid.setText(author);
                    }else {
                        replyUid.setText("游客");
                    }
                    createTime.setText(timeStr);
                    ImageLoader.getInstance().displayImage(comment.getAuthorIcon(),IVuserIcon,options,new HeadImageLoadingListener(),new MyImageLoadingProgressListener());
                    commentItem.setOnClickListener(new CommentCickListener(context,postId,comment.getAuthorNickname()));
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
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText(title);
    }
}
