package com.rock.Listener;

import android.content.Context;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.listener.SocializeListeners;

/**
 * Created by wym on 2014/10/30.
 */
public class MyUMAuthDeleteListener implements SocializeListeners.SocializeClientListener {
    public Context context;
    public MyUMAuthDeleteListener(Context context){
        this.context = context;
    }


    @Override
    public void onStart() {
    }
    @Override
    public void onComplete(int status, SocializeEntity entity) {

    }
}
