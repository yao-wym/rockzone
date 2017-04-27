package com.rock.Listener;

import android.content.Context;
import android.widget.Toast;
import com.rock.Helper.UIHelper;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;

import java.util.Map;

/**
 * Created by wym on 2014/10/30.
 */
public class MyUMdataListener implements SocializeListeners.UMDataListener {
    private UMSocialService mController;
    private Context context;
    private SHARE_MEDIA platform;
    public MyUMdataListener(Context context,UMSocialService mController,SHARE_MEDIA platform){
        this.context = context;
        this.mController = mController;
        this.platform = platform;
    }
    @Override
    public void onStart() {

    }

    @Override
    public void onComplete(int status, Map<String, Object> info) {
        if(status == 200 && info != null){
            String nickname = (String) info.get("screen_name");
            Toast.makeText(context,"授权完成",3).show();
            UIHelper.showRegisterView(context,nickname);
        }
        else{
            mController.doOauthVerify(context, platform, new MyUMAuthListener(context,mController,platform)); //todo 第三方登陆
        }
    }
}
