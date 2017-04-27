package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.rock.Adapter.CommentListAdapter;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
import com.rock.common.JsonUtil;
import com.rock.common.StringUtils;
import com.rock.model.Comment;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ShowCommentListActivity extends BaseActivity {

    private int pageNum = 1;
    private int showNum = 15;
    private Handler moreHandler;
    private PullToRefreshListView mPullRefreshListView;
    private CommentListAdapter mAdapter;
    private LayoutInflater inflater;
    private ArrayList<Comment> commentArrayList;
    private String requestId;
    private String replyAuthor;
    private EditText ETcomment;
    private Button   completeBtn;
    private String parameter;
    private int type;
    private RelativeLayout RLcomment;
    private Handler commentHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_list);
        initialHeader();
        requestId = getIntent().getStringExtra("goodsId");
        if(requestId == null){
            requestId = getIntent().getStringExtra("articleId");
            parameter = "article_id";
            type = 1;
        }else {
            parameter = "good_id";
            type = 2;
        }
        loadComment();
        inflater = LayoutInflater.from(this);
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.comment_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//向上拉加载
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                loadMore();
            }
        });
        mAdapter = new CommentListAdapter(context,null,ETcomment);

        //这两个绑定方法用其一
        // 方法一
//		 mPullRefreshListView.setAdapter(mAdapter);
        //方法二
        initViews();
        ListView actualListView = mPullRefreshListView.getRefreshableView();
        actualListView.setAdapter(mAdapter);
        loadComment();
    }
    private void loadMore(){
        moreHandler = new Handler() {

            public void handleMessage(Message msg) {
                pageNum++;
                JSONArray response = (JSONArray)msg.obj;
                int length = response.length();
                if(length==0){
                    Toast.makeText(context,"没有更多回复",3).show();
                }
                else {
                    try {
                        commentArrayList = JsonUtil.jsonArrToCommentList(response);
                        mAdapter.refresh(commentArrayList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                mPullRefreshListView.onRefreshComplete();
            }
        };

        ApiClient.getCommentList(moreHandler, requestId,type, pageNum,showNum);
    }

    public void initListView(ArrayList<Comment> community_list){
        mAdapter = new CommentListAdapter(context,community_list,ETcomment);

        //这两个绑定方法用其一
        // 方法一
//		 mPullRefreshListView.setAdapter(mAdapter);
        //方法二
        ListView actualListView = mPullRefreshListView.getRefreshableView();
        actualListView.setAdapter(mAdapter);
    }
//    public void initialHeader(){
//        super.initialHeader();
//        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
//        head.setText("评 论");
//        headRight.setImageResource(0);
//    }

    private void loadComment(){
        commentHandler = getCommentHander();
        if(!ShowCommentListActivity.this.isFinishing()){
            UIHelper.showProgressDialog(ShowCommentListActivity.this);
        }
        ApiClient.getCommentList(commentHandler,requestId,type,pageNum,showNum);
    }
    protected Handler getCommentHander(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                UIHelper.dismissProgressDialog();
                JSONArray comments = (JSONArray) msg.obj;
                try {
                    pageNum++;
                   commentArrayList = JsonUtil.jsonArrToCommentList(comments);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initListView(commentArrayList);

            }
        };
    }
    private void initViews(){
        initialHeader("评论列表");

        ETcomment = (EditText)findViewById(R.id.comment_edit);
        completeBtn = (Button)findViewById(R.id.comment_submit);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = ETcomment.getText().toString();
                {
                    if(StringUtils.isEmpty(content)){
                        Toast.makeText(context,"请输入评论内容",3).show();
                        return;
                    }
                    RequestParams params = new RequestParams();
                    params.put("uid", uid);
                    params.put("content",content);
                    params.put(parameter,requestId);
                    commentHandler = getResetHandler();
                    ApiClient.doPostComment(commentHandler, params, type);
                }

            }
        });
    }
    private Handler getResetHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    Toast.makeText(context, "评论成功", Toast.LENGTH_LONG).show();
                    ETcomment.setText("");
                    pageNum = 1;
                    if(commentArrayList != null){
                        commentArrayList.clear();
                    }
                    mAdapter.reset();
                    loadComment();
                }
                else {
                    Toast.makeText(context,"评论失败", Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    public void initialHeader(String title){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText(title);
    }
}