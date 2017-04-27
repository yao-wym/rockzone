package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rock.Adapter.OrderListAdapter;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
import org.json.JSONArray;
import org.json.JSONException;

public class UserOrderListActivity extends BaseActivity {

    private int pageNum = 1;
    private int showNum = 3;
    private Handler moreHandler;
    private PullToRefreshListView mPullRefreshListView;
    private OrderListAdapter mAdapter;
    private JSONArray jsonContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list);
        context = this;
        getUserOrder();
        initialHeader();
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.message_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//向下拉刷新
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                loadMore();
            }
        });
    }
    private void loadMore(){
        moreHandler = new Handler() {

            public void handleMessage(Message msg) {
                pageNum++;
                JSONArray response = (JSONArray)msg.obj;
                int length = response.length();
                if(length==0){
                    Toast.makeText(context,"没有更多订单",3).show();
                }else {
                    for (int i=0;i<length;i++){
                        try {
                            jsonContent.put(response.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                mAdapter.refresh(jsonContent);
                mPullRefreshListView.onRefreshComplete();
            }
        };

        ApiClient.getUserOrderList(moreHandler, uid, pageNum,showNum);
    }

    public void initListView(JSONArray orderList){
        jsonContent=orderList;
        mAdapter = new OrderListAdapter(context,jsonContent);

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
        head.setText("我的订单");
        headRight.setImageResource(0);
    }
    public void getUserOrder(){
        Handler handler = getOrderListHandler();
        ApiClient.getUserOrderList(handler, uid, pageNum,showNum);
    }
    public Handler getOrderListHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                UIHelper.dismissProgressDialog();
                if(msg.what==-1){
                    return;
                }
                pageNum++;
                JSONArray orderList = (JSONArray) msg.obj;

                initListView(orderList);
            }
        };
    }

}