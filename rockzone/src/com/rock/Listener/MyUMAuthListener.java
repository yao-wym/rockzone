package com.rock.Listener;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;

/**
 * Created by wym on 2014/10/30.
 */
public class MyUMAuthListener implements SocializeListeners.UMAuthListener {
    private UMSocialService mController;
    private Context context;
    private SHARE_MEDIA platform;
    public  MyUMAuthListener(Context context,UMSocialService mController,SHARE_MEDIA platform){
        this.context = context;
        this.mController = mController;
        this.platform = platform;
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    public void onComplete(Bundle value, SHARE_MEDIA platform) {
        if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
            Toast.makeText(context, "授权成功.", Toast.LENGTH_SHORT).show();
            mController.getPlatformInfo(context,platform,new MyUMdataListener(context,mController,platform));

        } else {
            Toast.makeText(context, "授权失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(SocializeException e, SHARE_MEDIA share_media) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }

}
