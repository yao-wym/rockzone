package com.rock.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.rock.Helper.ApiClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wym on 2014/10/28.
 */
public class AdvDetailActivity extends BaseActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adv_detail_view);
        context = this;
        initialHeader();
        Intent intent = getIntent();
        String advId = intent.getStringExtra("advId");
        Handler advDetailHandler = getAdvDetailHandler();
        ApiClient.getAdvDetail(context,advDetailHandler, advId);
    }
    private Handler getAdvDetailHandler(){
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                JSONArray jsonArray = (JSONArray) msg.obj;
                JSONObject advDetail = null;
                try {
                    advDetail = jsonArray.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    initView(advDetail);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        return handler;
    }
    private void initView(JSONObject advDetail) throws JSONException {
        initHeadView();
        RelativeLayout contentLayout = (RelativeLayout)findViewById(R.id.contentRelative);
        WebView advWebView = new WebView(this);
        advWebView.getSettings().setDefaultTextEncodingName("UTF-8") ;
        advWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
        String advWebString = String.valueOf(advDetail.get("content"));

        advWebView.loadData(advWebString,"text/html; charset=UTF-8",null);

        View progress = findViewById(R.id.progressBar);
        contentLayout.removeView(progress);
        contentLayout.addView(advWebView);
    }
    private void initHeaderListener(){

    }
    private void initHeadView(){
        ImageButton head_left = (ImageButton)findViewById(R.id.head_left);
        head_left.setImageResource(R.drawable.fanhui);
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("石头科技");
    }
}
