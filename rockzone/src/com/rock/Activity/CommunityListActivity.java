package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.cloud.CloudListener;
import com.baidu.mapapi.cloud.CloudPoiInfo;
import com.baidu.mapapi.cloud.CloudSearchResult;
import com.baidu.mapapi.cloud.DetailSearchResult;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rock.Adapter.CommunityListAdapter;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
import com.rock.common.BaiduMapUtil;
;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommunityListActivity extends BaseActivity implements CloudListener{

    private int pageNum = 1;
    private int showNum = 5;
    private Handler handler;
    private Handler moreHandler;
    private PullToRefreshListView mPullRefreshListView;
    private CommunityListAdapter mAdapter;
    private ArrayList<JSONObject> jsonContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_list);
        initialHeader();
        try {
            initCommunityList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.community_choose_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//向下拉刷新
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                Toast.makeText(context,"没有更多小区",3).show();
                mPullRefreshListView.onRefreshComplete();

//        loadMore();
            }
        });
    }
    private void loadMore(){
        moreHandler = new Handler() {

            public void handleMessage(Message msg) {
                UIHelper.dismissProgressDialog();
                if(msg.what==-1){
                    return;
                }
                pageNum++;
                JSONArray response = (JSONArray)msg.obj;
                int length = response.length();
                if(length==0){
                    Toast.makeText(context,"没有更多小区",3).show();
                }
                else {
                    for (int i=0;i<length;i++){
                        try {
                            jsonContent.add(response.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            //todo 加载更多的实现
//                mAdapter.refresh(jsonContent);
                mPullRefreshListView.onRefreshComplete();
            }
        };

        ApiClient.getCommunityList(moreHandler,pageNum,showNum);
    }
    private void initCommunityList() throws Exception {
        /**
         * 获取listview的初始化Handler
         *
         * @return
         */
        handler = new Handler() {

            public void handleMessage(Message msg) {
                UIHelper.dismissProgressDialog();
                if(msg.what == 0){
                    Toast.makeText(context,"接口异常，错误码"+msg.obj,3).show();
                }else if(msg.what == -2){
                    Toast.makeText(context,"超时错误",3).show();
                }
                else if(msg.what == -1){
                    Toast.makeText(context,"没有更多了",3).show();
                }else {
                    pageNum++;
                    ArrayList<CloudPoiInfo> response = (ArrayList<CloudPoiInfo>) msg.obj;
                    if(response!=null){
                        initListView(response);

                    }
                }

            }
        };
        BaiduMapUtil baiduMapUtil = BaiduMapUtil.getInstance();
        baiduMapUtil.setContext(context);
        baiduMapUtil.setHandler(handler);
        baiduMapUtil.getNearCommunity("", 300000);
        try {
            UIHelper.showProgressDialog(CommunityListActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }
//        ApiClient.getCommunityList(handler,pageNum,showNum);
    }
    public void initListView(ArrayList<CloudPoiInfo> community_list){
//        jsonContent=community_list;
        mAdapter = new CommunityListAdapter(this, R.layout.community_list_item,community_list);

        //这两个绑定方法用其一
        // 方法一
//		 mPullRefreshListView.setAdapter(mAdapter);
        //方法二
        ListView actualListView = mPullRefreshListView.getRefreshableView();
        actualListView.setAdapter(mAdapter);
    }
    public void initialHeader(){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("选择小区");
    }

    @Override
    public void onGetSearchResult(CloudSearchResult cloudSearchResult, int i) {

    }

    @Override
    public void onGetDetailSearchResult(DetailSearchResult detailSearchResult, int i) {

    }
}