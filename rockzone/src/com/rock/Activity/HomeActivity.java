package com.rock.Activity;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.rock.Application.AppManager;
import com.rock.Application.MyApplication;
import com.rock.Helper.UIHelper;
import com.rock.common.StringUtils;

/**
 * Created by wym on 2014/11/5.
 */
public class HomeActivity extends ActivityGroup{

    private RelativeLayout myBtn;
    private RelativeLayout exploreBtn;
    private RelativeLayout scoreShopBtn;
    private RelativeLayout homeBtn;
    private RelativeLayout container;
    private Intent intent;
    private long exitTime;
    private MyApplication myApplication;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_foot);
        context = this;
        intent = new Intent();
        myApplication = (MyApplication) getApplication();
        container = (RelativeLayout)findViewById(R.id.container);
        initActivity(1);
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void initListener(){
        myBtn = (RelativeLayout) findViewById(R.id.bottom_my);
        scoreShopBtn = (RelativeLayout) findViewById(R.id.bottom_account);
        homeBtn = (RelativeLayout) findViewById(R.id.bottom_home);
        exploreBtn = (RelativeLayout) findViewById(R.id.bottom_explore);

        View.OnClickListener listener = new View.OnClickListener()
        {
            public void onClick(View v)
            {
                intent = new Intent();
                switch(v.getId())
                {
                    case R.id.bottom_home:
                        initActivity(1); break;
                    case R.id.bottom_explore:
                        initActivity(2); break;
                    case R.id.bottom_account:
                        initActivity(3); break;
                    case R.id.bottom_my:
                        if(myApplication.getLoginStatus()!=true|| StringUtils.isObjEmpty(myApplication.getUserId())){

                            UIHelper.showLoginView(context,0);
                        }else {
                            initActivity(4); break;
                        }
                }
            }
        };
        myBtn.setOnClickListener(listener);
        scoreShopBtn.setOnClickListener(listener);
        exploreBtn.setOnClickListener(listener);
        homeBtn.setOnClickListener(listener);

    }
    private void initActivity(int position){
        switch (position){
            case 1:
                intent.setClass(this, MainActivity.class);break;
            case 2:
                intent.setClass(this, ExploreActivity.class);break;
            case 3:
                intent.setClass(this, ScoreShopActivity.class);break;
            case 4:
                intent.setClass(this, UserCenterActivity.class);break;
        }

        Window subActivity = getLocalActivityManager().startActivity(position+"", intent);
        container.removeAllViews();
        container.addView(subActivity.getDecorView(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        UIHelper.initFootView(this,position);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK&&event.getAction()!=KeyEvent.ACTION_UP){
            if (System.currentTimeMillis() - exitTime > 2000) {// 如果两次按键时间间隔大于800毫秒，则不退出
                Toast.makeText(HomeActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();// 更新firstTime
            } else {
                AppManager.getAppManager().finishAllActivity();
            }
            return true;
        }

        return super.dispatchKeyEvent(event);
    }
}
