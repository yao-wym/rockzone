package com.rock.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.rock.Application.AppManager;
import com.rock.Application.MyApplication;
import com.rock.Helper.UIHelper;
import com.rock.Listener.MyUMAuthDeleteListener;
import com.rock.common.MySharePreference;
import com.rock.common.StringUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import org.json.JSONArray;
import org.json.JSONObject;
import roboguice.activity.RoboActivity;

/**
 * Created by wym on 2014/10/30.
 */
public class BaseActivity extends RoboActivity {
    public Boolean isLogin;
    public String communityId;
    public String uid;
    public Context context;
    public ImageButton headRight;
    public ImageButton headLeft;
    public RelativeLayout headMiddle;
    public Context homeContext;
    public int showNum = 10;
    private String tempcoor= "gcj02";
    public JSONArray jsonArray;
    public JSONObject content;
    public DisplayImageOptions options;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    public LocationClient mLocationClient;
    public MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getApplication();
        AppManager.getAppManager().addActivity(this);
        mLocationClient = myApplication.mLocationClient;
        context = this;
        homeContext = this.getParent();
        initUserInfo();
        InitLocation();
        mLocationClient.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void initialHeader(){
        headRight = (ImageButton)findViewById(R.id.head_right);
        headLeft = (ImageButton)findViewById(R.id.head_left);
        headMiddle = (RelativeLayout)findViewById(R.id.head_middle);
        headLeft.setImageResource(R.drawable.fanhui);
        headRight.setImageResource(R.drawable.shouye_xiaoxi);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.head_left:
                        finish();
                        break;
                    case R.id.head_right:
                        onRightClick();
                        break;

                }
            }
        };
        headLeft.setOnClickListener(listener);
        headRight.setOnClickListener(listener);
    }
    private void InitLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);//设置定位模式
        option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
        int span=1000;
        option.setScanSpan(span);//设置发起定位请求的间隔时间为1000ms
        mLocationClient.setLocOption(option);
    }

    public void loginOut(){
        MySharePreference.getPreference(context);
        MySharePreference.putBoolean(context,"isLogin",false);
        MySharePreference.putString(context,"uid","");
        uid = "";
        isLogin = false;
        myApplication.setLoginStatus(false);
        myApplication.setUserStatus(null);
        myApplication.setUserId("");
        UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        mController.deleteOauth(context, SHARE_MEDIA.SINA,
                new MyUMAuthDeleteListener(context));
        mController.deleteOauth(context, SHARE_MEDIA.QQ,
                new MyUMAuthDeleteListener(context));
    }
    public void onRightClick(){
        if(!isLogin)
            UIHelper.showLoginView(context,0);
        else{
            UIHelper.showMessageListView(context);
        }
    }
    public void initUserInfo(){

        communityId = MySharePreference.getString(context,"communityId");
        uid = myApplication.getUserId();
        if(StringUtils.isObjEmpty(uid)){
            isLogin = false;
        }else {
            isLogin = true;
        }
    }
}
