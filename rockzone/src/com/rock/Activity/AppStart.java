package com.rock.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import com.rock.Application.MyApplication;
import com.rock.common.MySharePreference;
import com.rock.common.StringUtils;

/**
 * 应用程序启动类：显示欢迎界面并跳转到主界面
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class AppStart extends Activity {
    
	private static final String TAG  = "AppStart";
    public static Boolean isLogin;
    public static Boolean ISVISITOR;
    public String uid;
    public String communityId;
    public Context context;
    private MyApplication myApplication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        final View view = View.inflate(this, R.layout.start, null);
		LinearLayout wellcome = (LinearLayout) view.findViewById(R.id.app_start_view);
		//check(wellcome);
		setContentView(view);
        
		//渐变展示启动屏
		AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}
			
		});

		//兼容低版本cookie（1.5版本以下，包括1.5.0,1.5.1）

    }
    
    /**
     * 检查是否需要换图片
     * @param view

    private void check(LinearLayout view) {
    	String path = FileUtils.getAppCache(this, "welcomeback");
    	List<File> files = FileUtils.listPathFiles(path);
    	if (!files.isEmpty()) {
    		File f = files.get(0);
    		long time[] = getTime(f.getName());
    		long today = StringUtils.getToday();
    		if (today >= time[0] && today <= time[1]) {
    			view.setBackgroundDrawable(Drawable.createFromPath(f.getAbsolutePath()));
    		}
    	}
    }
     */
    /**
     * 分析显示的时间
     * @param time
     * @return
     */
    private long[] getTime(String time) {
    	long res[] = new long[2];
    	try {
    		time = time.substring(0, time.indexOf("."));
        	String t[] = time.split("-");
        	res[0] = Long.parseLong(t[0]);
        	if (t.length >= 2) {
        		res[1] = Long.parseLong(t[1]);
        	} else {
        		res[1] = Long.parseLong(t[0]);
        	}
		} catch (Exception e) {
		}
    	return res;
    }
    
    /**
     * 跳转到...
     */
    private void redirectTo(){
        Intent intent;
        initUserInfo();
        if(isLogin == true){
            intent = new Intent(this, HomeActivity.class);
        }
        else
        {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

    public void initUserInfo(){
            try {
                communityId = MySharePreference.getString(context,"communityId");
                uid         = MySharePreference.getString(context,"uid");
//                uid= "67";
//                communityId="1";
//                MySharePreference.putString(context, "uid", "67");
//                MySharePreference.putString(context,"communityId","1");
            }catch (Exception e){
                MySharePreference.clear(this);
                MySharePreference.putString(context, "uid", "");
                MySharePreference.putString(context,"communityId","");
            }


        if(StringUtils.isObjEmpty(uid)){
            isLogin = false;
        }else {
            myApplication = (MyApplication) getApplication();
            myApplication.setLoginStatus(true);
            myApplication.setUserId(uid);
            if(!StringUtils.isObjEmpty(communityId))
            myApplication.setCommunityId(communityId);
            isLogin = true;
        }
    }

}