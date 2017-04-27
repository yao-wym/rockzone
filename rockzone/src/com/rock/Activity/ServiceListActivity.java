package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
import org.json.JSONArray;

/**
 * Created by wym on 2014/10/30.
 */
public class ServiceListActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler serviceListHandler = getServiceListHandler();
        ApiClient.getServiceList(serviceListHandler, 17, 5);
        setContentView(R.layout.community_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private Handler getServiceListHandler(){
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                UIHelper.dismissProgressDialog();
                if(msg.what==-1){
                    return;
                }
                JSONArray communityServiceList = (JSONArray) msg.obj;
                initView(communityServiceList);
            }
        };
        return handler;
    }
    private void initView(JSONArray communityServiceList){

    }
}
