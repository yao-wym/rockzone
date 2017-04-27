package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rock.Adapter.ArticleListAdapter;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
;
import com.rock.common.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wym on 2014/11/6.
 */
public class CommunityArticleListActivity extends BaseActivity {
    private int pageNum = 1;
    private int showNum = 8;
    private ArrayList<JSONObject> jsonContent;
    private Handler moreHandler;
    private String id;
    private int titleId;
    private int layoutId;
    private LayoutInflater inflater;
    private PullToRefreshListView mPullRefreshListView;
    private ArticleListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_article_list);
        id = getIntent().getStringExtra("id");
        titleId = getIntent().getIntExtra("titleId", 0);
        inflater = LayoutInflater.from(this);
        initialHeader(titleId);
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.community_article_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//上拉加载
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                    DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label+"-上拉加载更多");
                loadMore();
            }
        });
        Handler handler =  getHandler();
        ApiClient.getArticleList(context,handler,id,pageNum,showNum);
        try {
            UIHelper.showProgressDialog(CommunityArticleListActivity.this);
        } catch(Exception e){
            // WindowManager$BadTokenException will be caught and the app would not display
            // the 'Force Close' message
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    protected Handler getHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                UIHelper.dismissProgressDialog();
                if(msg.what == 1){
                    pageNum++;
                    JSONArray response = (JSONArray)msg.obj;
                    ArrayList<JSONObject> content=new ArrayList<JSONObject>();
                    for (int i=0;i<response.length();i++){
                        try {
                            content.add(response.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    initListView(content);
                }
                else {
                    Toast.makeText(context, String.valueOf(msg.obj), 3).show();
                }
            }
        };
    }
    private void loadMore(){
        moreHandler = new Handler() {

            public void handleMessage(Message msg) {

                if(msg.what!=1){
                    Toast.makeText(context, String.valueOf(msg.obj), 3).show();
                }
                else {
                    JSONArray response = (JSONArray)msg.obj;
                    int length = response.length();
                    for (int i=0;i<length;i++){
                        try {
                            jsonContent.add(response.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mAdapter.refresh(jsonContent);
                    pageNum++;
                }
                mPullRefreshListView.onRefreshComplete();
            }
        };

        ApiClient.getArticleList(context,moreHandler,id, pageNum, showNum);
    }
    public void initListView(ArrayList<JSONObject> article_list){
        jsonContent = article_list;
        switch (titleId){
            case R.string.main_publish:layoutId = R.layout.xiaoqu_publish_item;break;
            case R.string.main_forum:layoutId = R.layout.xiaoqu_forum_item;
//                inflater.inflate(R.layout.post_button,null);
                ImageView postBtn = (ImageView) findViewById(R.id.post);
                postBtn.setImageResource(R.drawable.fatie_button_stl);
                postBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(StringUtils.isObjEmpty(uid)){
                            UIHelper.showLoginView(context,0);
                        }else {
                            UIHelper.showPostView(context,String.valueOf(id));
                        }
                    }
                });
                break;
            case R.string.main_phone:layoutId = R.layout.xiaoqu_phonenum_item;break;
            case R.string.main_wuye:
                layoutId = R.layout.xiaoqu_wuye_item;
                break;
            case R.string.main_youhui:layoutId = R.layout.article_youhui_item;break;
            default:layoutId = R.layout.community_article_item;break;
        }
        mAdapter = new ArticleListAdapter(this,layoutId,article_list);
        ListView articleListView = mPullRefreshListView.getRefreshableView();
        articleListView.setAdapter(mAdapter);
    }
    public void initialHeader(int titleId){
        super.initialHeader();
        TextView middle = (TextView) headMiddle.findViewById(R.id.community_name);
        if(titleId == 0){
            middle.setText("文章列表");
        }
        else
        middle.setText(getResources().getText(titleId));
    }
}
