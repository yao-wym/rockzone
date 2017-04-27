package com.rock.Listener;

import com.umeng.socialize.bean.SocializeUser;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;

/**
 * Created by wym on 2014/10/30.
 */
public class MyFetchUserListener implements SocializeListeners.FetchUserListener {
    UMSocialService mController;
    public MyFetchUserListener(UMSocialService mController){
        this.mController = mController;
    }
    @Override
    public void onStart() {

    }

    @Override
    public void onComplete(int i, SocializeUser socializeUser) {

    }


}
