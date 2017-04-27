package com.rock.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
import com.rock.Listener.MyImageLoadingProgressListener;
import com.rock.common.BitmapUtil;
import com.rock.common.FileUtils;
import com.rock.common.MySharePreference;
import com.rock.model.User;
import org.json.JSONException;

/**
 * Created by wym on 2014/11/4.
 */
public class UserCenterActivity extends BaseActivity implements ImageLoadingListener{
    private ImageButton IBheadPic;
    private TextView TVpost;
    private TextView TVmessage;
    private TextView TVnickname;
    private TextView TVlevel;
    private TextView TVscore;
    private TextView TVweibocount;
    private TextView TVfansNum;
    private TextView TVfollowNum;
    private RelativeLayout RLcollection;
    private RelativeLayout RLorder;
    private RelativeLayout RLsetting;
    private RelativeLayout RLmessage;
    private RelativeLayout RLerweima;
    private RelativeLayout RLgonglve;
    public Handler handler;
    public User userInfo;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isLogin!=true){
            UIHelper.showLoginView(context,1);
            this.finish();
        }else{
            setContentView(R.layout.user_center);
                setContentView(R.layout.user_center);
                initialHeader();
                initViews();
//                User user = (User) FileUtils.readObject(context,"user");
//                updateViews(user);
                getUserInfo();

        }
    }
    public void initialHeader(){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("个人中心");
        headLeft.setOnClickListener(null);
        headLeft.setBackgroundResource(0);
        headLeft.setImageResource(0);
        headRight.setImageResource(R.drawable.refresh);
        headRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInfo();
            }
        });
    }
    public void getUserInfo(){
            handler = getHandler();
            UIHelper.showProgressDialog(homeContext);
            ApiClient.getUserInfo(handler,uid);
    }
    public Handler getHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                UIHelper.dismissProgressDialog();
                if(msg.what == 1){
                    userInfo = (User) msg.obj;
                    try {
                        updateViews(userInfo);
                        FileUtils.saveObject(context,userInfo,"user");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(context, "用户信息拉取失败", 3).show();
                }
            }
        };
    }
    public void updateViews(final User userInfo) throws JSONException {

        TVnickname.setText(userInfo.getNickname());
        TVlevel.setText(userInfo.getTitle());
        TVscore.setText(userInfo.getScore());
        TVweibocount.setText(userInfo.getWeibocount());
        TVfansNum.setText(userInfo.getFans());
        TVfollowNum.setText(userInfo.getFollowing());
        String headPicUrl = userInfo.getHeadPic();
        if(headPicUrl.substring(0,4).equals("http")){
            loadHeadPic(headPicUrl);
        }

    }
    public void loadHeadPic(String headPicUrl){
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_0) // resource or drawable
                .showImageForEmptyUri(R.drawable.empty) // resource or drawable
                .showImageOnFail(R.drawable.loading_0) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(1000)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .build();
        ImageLoader.getInstance().displayImage(headPicUrl, IBheadPic, options, this,new MyImageLoadingProgressListener());
    }
    public void initViews(){
        TVnickname = (TextView) findViewById(R.id.nickname);
        TVlevel = (TextView) findViewById(R.id.level);
        TVscore = (TextView) findViewById(R.id.score);
        TVfansNum = (TextView) findViewById(R.id.fans);
        TVfollowNum = (TextView) findViewById(R.id.follows);
        TVweibocount = (TextView) findViewById(R.id.post);
        RLcollection = (RelativeLayout)findViewById(R.id.my_collection);
        RLmessage = (RelativeLayout)findViewById(R.id.my_message);
        RLorder = (RelativeLayout)findViewById(R.id.my_order);
        RLsetting = (RelativeLayout) findViewById(R.id.my_setting);
        RLerweima = (RelativeLayout) findViewById(R.id.my_erweima);
        RLgonglve = (RelativeLayout) findViewById(R.id.my_gonglve);
        IBheadPic = (ImageButton) findViewById(R.id.head_pic);
        TVmessage = (TextView) findViewById(R.id.message);
        TVpost = (TextView) findViewById(R.id.post);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.login_out:
                        MySharePreference.getPreference(context);
                        MySharePreference.putBoolean(context,"isLogin",false);
                        MySharePreference.putString(context,"uid","");
                        UIHelper.showLoginView(context,1);
                        break;
                    case R.id.head_pic:
                        UIHelper.showUserEditView(context,userInfo);
                        break;
                    case R.id.my_setting:
                        UIHelper.showSettingView(context);
                        break;
                    case R.id.my_collection:
                        UIHelper.showUserCollection(context);
                        break;
                    case R.id.my_order:
                        UIHelper.showOrderListView(context);
                        break;
                    case R.id.my_message:
                        UIHelper.showMessageListView(context);
                        break;
                    case R.id.my_erweima:
                        UIHelper.showStaticView(context,R.layout.rock_erweima,"二维码");
                        break;
                    case R.id.my_gonglve:
                        UIHelper.showStaticView(context,R.layout.rock_about_us,"积分攻略");
                        break;
                }
            }
        };
        RLgonglve.setOnClickListener(listener);
        RLerweima.setOnClickListener(listener);
        IBheadPic.setOnClickListener(listener);
        RLcollection.setOnClickListener(listener);
        RLorder.setOnClickListener(listener);
        RLsetting.setOnClickListener(listener);
        RLmessage.setOnClickListener(listener);
    }


    @Override
    public void onLoadingStarted(String s, View view) {

    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {

    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        Bitmap targetBitmap = BitmapUtil.getCircleImage(bitmap);
        ((ImageView)view).setImageBitmap(targetBitmap);
    }

    @Override
    public void onLoadingCancelled(String s, View view) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        getUserInfo();
    }
}
