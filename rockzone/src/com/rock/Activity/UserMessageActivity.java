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
import com.rock.Adapter.MessageListAdapter;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
import com.rock.common.JsonUtil;
import com.rock.model.UserMessage;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class UserMessageActivity extends BaseActivity {

    private int pageNum = 1;
    private int showNum = 10;
    private PullToRefreshListView mPullRefreshListView;
    private MessageListAdapter mAdapter;
    private ArrayList<UserMessage> userMessageArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        initialHeader();
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.message_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//向上拉加载
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                getUserMessage();
            }
        });
        initListView();
        getUserMessage();
    }


    public void initListView(){
        mAdapter = new MessageListAdapter(context,null);

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
        head.setText("我的消息");
        headRight.setImageResource(0);
    }
    public void getUserMessage(){
//        uid = MySharePreference.getString(this, "uid");
        Handler handler = getMessageHandler();
        //todo 用户消息接口暂时没有分页
        ApiClient.getUserMessage(handler,uid,pageNum,showNum);
    }
    public Handler getMessageHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                UIHelper.dismissProgressDialog();
                if(msg.what==-1){
                    return;
                }
                mPullRefreshListView.onRefreshComplete();
                pageNum++;
                JSONArray messages = (JSONArray) msg.obj;
                if(messages == null||messages.equals("")||messages.length()==0){
                    Toast.makeText(context, "没有更多消息", 3).show();
                    return;
                }
                try {
                    userMessageArrayList = JsonUtil.jsonArrToMessageList(messages);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter.refresh(userMessageArrayList);
            }
        };
    }

}