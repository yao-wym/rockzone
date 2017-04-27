package com.rock.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.rock.Application.AppManager;
import com.rock.Application.MyApplication;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
import com.rock.Listener.MyUMdataListener;
import com.rock.common.MySharePreference;
;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMQQSsoHandler;
import roboguice.activity.RoboActivity;

public class LoginActivity extends RoboActivity {
    private Context context;
    private TextView visitor_btn;
    private Button submitBtn;
    private ImageView IVqq;
    private ImageView IVweibo;
    private EditText accountET;
    private EditText passwordET;
    private ImageView IVfroget;
    private ImageButton registerTV;
	private EditText userET;
	private Handler loginHandler;
	private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String username;
    private String password;
    private long exitTime;

    @Override
            protected void onCreate(Bundle savedInstanceState) {
            	super.onCreate(savedInstanceState);
                context = this;
                setContentView(R.layout.rock_login);
                sharedPreferences = MySharePreference.getPreference(this);
            	initViews();
            	addListeners();
                ImageButton registerBtn = (ImageButton)findViewById(R.id.login_tv_register);
                registerBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UIHelper.showRegisterView(context,null);
                    }
                });
            }

			private void addListeners() {
                visitor_btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 游客登录时跳过小区选择
//                        MySharePreference.putBoolean(context,"visitor",true);
//                        MySharePreference.putString(context, "communityId", "1");
//                        MyApplication application = (MyApplication)getApplication();
//                        application.setCommunityId("1");
//                        UIHelper.showHomeView(context);
                        UIHelper.showCommunityListView(context);
                        finish();
                    }
                });
				registerTV.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						register();
					}
				});
				submitBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						login();
					}
				});
                IVfroget.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UIHelper.showResetValidView(context);
                    }
                });
                IVweibo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        weiboLogin();
                    }
                });
                IVqq.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qqLogin();
                    }
                });
			}

		
			

			private void initViews() {
                visitor_btn = (TextView) findViewById(R.id.visitor_btn);
                submitBtn = (Button) findViewById(R.id.login_btn_submit);
                IVqq = (ImageView) findViewById(R.id.login_btn_qqdenglu);
                IVweibo = (ImageView) findViewById(R.id.login_btn_weibodenglu);
                IVfroget = (ImageView) findViewById(R.id.login_tv_froget);
                userET =  (EditText)findViewById(R.id.login_et_account);
                passwordET = (EditText) findViewById(R.id.login_et_password);
                registerTV = (ImageButton) findViewById(R.id.login_tv_register);
			}

		
			
			private void register() {
				startActivity(new Intent(this,RegisterActivity.class));
				
			}

			private void login() {
                View view = LoginActivity.this.getCurrentFocus();
                if(view == null){
                    view = submitBtn;
                     }
                IBinder iBinder = view.getWindowToken();
                InputMethodManager inputMethodManager = ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE));
                inputMethodManager.hideSoftInputFromWindow(iBinder,InputMethodManager.HIDE_NOT_ALWAYS);
                username = userET.getText().toString();
					password = passwordET.getText().toString();
                    if(checkInput(username,password)){
                        loginHandler = getLoginHandler();
                        UIHelper.showProgressDialog(context);
                        ApiClient.login(username, password,loginHandler);
                    }
			}


            private Handler getLoginHandler(){
                return new Handler(){
                    public void handleMessage(Message msg) {
                        UIHelper.dismissProgressDialog();
                        switch (msg.what) {
                            case -1:
                                Toast.makeText(context, (String)msg.obj, Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                Toast.makeText(context,"登陆成功" , Toast.LENGTH_SHORT).show();
                                MyApplication ac = (MyApplication)getApplication();

                                editor = sharedPreferences.edit();
                                editor.putBoolean("isLogin", true);
                                editor.putString("uid", String.valueOf(msg.obj));
                                editor.commit();
                                ac.setLoginStatus(true);
                                ac.setUserId(String.valueOf(msg.obj));
                                String communityId = MySharePreference.getString(context,"communityId");
                                //todo 用户所在小区优化
                                if(communityId == null || communityId.equals("null")||communityId.equals("")){
                                    UIHelper.showCommunityListView(context);
                                }else {
                                    UIHelper.showHomeView(context);
                                }
                                finish();
                                break;

                            default:
                                break;
                        }
                    };
                };
            }
            private boolean checkInput(String username,String password){
                if("".equals(username)){
                    Toast.makeText(getApplication(), "请输入用户名", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else if("".equals(password)){
                    Toast.makeText(getApplication(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else {
                    return true;
                }
            }

    public void weiboLogin(){

        UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
      //  mController.getUserInfo(context,new MyFetchUserListener(mController));
        mController.getPlatformInfo(LoginActivity.this,SHARE_MEDIA.SINA,new MyUMdataListener(context,mController,SHARE_MEDIA.SINA));

    }
    public void qqLogin(){
        //getDeviceInfo(context);
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.this, "1101842068",
                "bY2h2VaT9D4kwfnx");
        qqSsoHandler.addToSocialSDK();
        final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
      //  mController.getUserInfo(context,new MyFetchUserListener(mController));
        mController.getPlatformInfo(LoginActivity.this,SHARE_MEDIA.SINA,new MyUMdataListener(context,mController,SHARE_MEDIA.QQ));


    }

    public  String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int final_flag = getIntent().getIntExtra("final_flag",0);
        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK&&event.getAction()!=KeyEvent.ACTION_UP&&final_flag == 1){
            if (System.currentTimeMillis() - exitTime > 2000) {// 如果两次按键时间间隔大于800毫秒，则不退出
                Toast.makeText(LoginActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();// 更新firstTime
            } else {
                AppManager.getAppManager().finishAllActivity();
            }
            return true;
        }else if(event.getKeyCode()==KeyEvent.KEYCODE_BACK&&event.getAction()!=KeyEvent.ACTION_UP&&final_flag == 0){
               finish();
            return true;
        }

        return super.dispatchKeyEvent(event);
    }

}
