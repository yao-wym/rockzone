package com.rock.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.rock.Helper.UIHelper;
import com.rock.common.FileUtils;
import com.rock.common.MySharePreference;

public class SettingActivity extends BaseActivity{
    private RelativeLayout RLchangePwd;
    private RelativeLayout RLchangeCommunity;
    private RelativeLayout RLdeleteCache;
    private ImageButton IBswitcher;
    private RelativeLayout RLversionCheck;
    private RelativeLayout RLaboutUs;
    private Button BTloginOut;
    private Handler handler;
    private boolean isPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_list);
        setSwitcher();
        initialHeader();
        initViews();
    }
    public void initViews(){
        IBswitcher = (ImageButton) findViewById(R.id.push_switcher);
        RLaboutUs = (RelativeLayout) findViewById(R.id.about_us);
        RLchangePwd       = (RelativeLayout) findViewById(R.id.change_pwd);
        RLchangeCommunity = (RelativeLayout) findViewById(R.id.change_community);
        RLversionCheck = (RelativeLayout) findViewById(R.id.version_check);;
        BTloginOut = (Button) findViewById(R.id.login_out);
        RLdeleteCache = (RelativeLayout)findViewById(R.id.delete_cache);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.login_out:
                        loginOut();
                        finish();
                        UIHelper.showLoginView(context,1);
                        break;
                    case R.id.change_pwd:
                        UIHelper.showChangePwdView(context);
                        break;
                    case R.id.change_community:
                        UIHelper.showCommunityListView(context);
                        break;
                    case R.id.about_us:
                        UIHelper.showStaticView(context,R.layout.rock_about_us,"关于我们");
                        break;
                    case R.id.push_switcher:
                        MySharePreference.putBoolean(context, "is_push", !isPush);
                        setSwitcher();
                        break;
                    case R.id.delete_cache:
                        FileUtils.clearFileWithPath(getCacheDir().toString());
                        new AlertDialog.Builder(SettingActivity.this)
                                .setTitle("已清空缓存")
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                dialog.dismiss();
                                            }
                                        }
                                ).create()
                                .show();
                        break;
                    case R.id.version_check:
                        new AlertDialog.Builder(SettingActivity.this)
                                .setTitle("已经是最新版本")
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                dialog.dismiss();
                                            }
                                        }
                                ).create()
                                .show();
                        break;
                }
            }
        };
        RLdeleteCache.setOnClickListener(listener);
        IBswitcher.setOnClickListener(listener);
        RLversionCheck.setOnClickListener(listener);
        RLaboutUs.setOnClickListener(listener);
        RLchangePwd.setOnClickListener(listener);
        BTloginOut.setOnClickListener(listener);
        RLchangeCommunity.setOnClickListener(listener);
    }
    public void initialHeader(){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("设 置");
    }
    public void setSwitcher(){
        isPush = MySharePreference.getBoolean(context,"is_push");
        ImageView switcher = (ImageView)findViewById(R.id.push_switcher);
        if(isPush == true)
            switcher.setImageResource(R.drawable.personal_on);
        else
            switcher.setImageResource(R.drawable.personal_off);
    }
}