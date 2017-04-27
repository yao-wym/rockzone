package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rock.Adapter.ScoreShopAdapter;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
import com.rock.model.User;
;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wym on 2014/11/5.
 */
public class ScoreShopActivity extends BaseActivity {
    private int pageNum = 1;
    private ArrayList<JSONObject> jsonContent;
    private Handler moreHandler;
    private PullToRefreshListView mPullRefreshListView;
    private ScoreShopAdapter mAdapter;
    private LayoutInflater inflater;
    private Handler scorehandler;
    private User userInfo;
    private TextView TVscore;
    private LinearLayout LLscoreLogo;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_score_shop);
        UIHelper.showProgressDialog(ScoreShopActivity.this.getParent());
        context = this;
        inflater = LayoutInflater.from(context);
        initialHeader();
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.score_shop_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//向拉加载
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
        loadMore();
            }
        });
        Handler handler =  getHandler();
        ApiClient.getGoodsList(context,handler,pageNum);
        getUserInfo();
    }
    private Handler getHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                UIHelper.dismissProgressDialog();
                if(msg.what == 1){
                    JSONArray response = (JSONArray)msg.obj;
                    ArrayList<JSONObject> content=new ArrayList<JSONObject>();
                    for (int i=0;i<response.length();i++){
                        try {
                            content.add(response.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    pageNum++;
                    initListView(content);
                }
                else {
                    Toast.makeText(context, "商品暂时为空", 3).show();
                }
            }
        };
    }
    public void initListView(ArrayList<JSONObject> goodsList){
        jsonContent = goodsList;
        mAdapter = new ScoreShopAdapter(this, R.layout.score_shop_item,goodsList);

        //这两个绑定方法用其一
        // 方法一
//		 mPullRefreshListView.setAdapter(mAdapter);
        //方法二
        ListView actualListView = mPullRefreshListView.getRefreshableView();
        actualListView.addHeaderView(LLscoreLogo);
        actualListView.setAdapter(mAdapter);
    }
    private void loadMore(){
        moreHandler = new Handler() {

            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    JSONArray response = (JSONArray)msg.obj;
                    int length = response.length();
                    for (int i=0;i<length;i++){
                        try {
                            jsonContent.add(response.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    pageNum++;
                    mAdapter.refresh(jsonContent);
                }
                else {
                    Toast.makeText(context, "没有更多商品", 3).show();
                }
                mPullRefreshListView.onRefreshComplete();
            }
        };

        ApiClient.getGoodsList(context,moreHandler,pageNum);
    }
    public void initialHeader(){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("积分商城");
        headLeft.setOnClickListener(null);
        headLeft.setImageResource(0);
        headLeft.setBackgroundResource(0);
    }
    public void getUserInfo(){
        LLscoreLogo = (LinearLayout) inflater.inflate(R.layout.score_shop_logo, null);
        TVscore = (TextView) LLscoreLogo.findViewById(R.id.my_score);
        TextView textView = (TextView) LLscoreLogo.findViewById(R.id.my_score);
        textView.setText("0");
        scorehandler = getScoreHandler();
        ApiClient.getUserInfo(scorehandler,uid);
    }
    public Handler getScoreHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1){
                    userInfo = (User) msg.obj;
                    TVscore.setText(userInfo.getScore());
                }
            }
        };
    }
}