package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.RequestParams;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
import com.rock.Helper.URL;
import com.rock.common.CheckUtil;
;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends BaseActivity {
    private Button nextStep;
    private EditText numberET;
    private EditText inviterET;
    private EditText messageET;
    private EditText username;
    private EditText password;
    private EditText pwdConfirm;
    private Button   completeBtn;
    private String validUrl;
    private int timer;
    private int flag;
    private TextView sendMessageTV;
    private Handler timeerHandler;
    private Handler regHandler;
    private Handler validHandler;
    private String smsCode;
    private String phoneNum;
    private LayoutInflater inflater;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rock_register);
        context = this;
        inviterET = (EditText) findViewById(R.id.reg_et_inviter);
        nextStep = (Button) findViewById(R.id.reg_next_step);
        nickname = getIntent().getStringExtra("nickname");
        if(nickname != null&&!nickname.equals("")){
            nextStep.setText("完成注册");
            inviterET.setHint("请输入密码");
        }
        initialHeader();
        inflater = LayoutInflater.from(context);
        initViews();
        addListeners();
    }

    private void initViews() {
        numberET = (EditText) findViewById(R.id.reg_et_phone);
        messageET = (EditText) findViewById(R.id.reg_et_message);
        sendMessageTV = (TextView) findViewById(R.id.reg_tv_sendMessage);
    }

    private void addListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.reg_tv_sendMessage:
                        phoneNum = numberET.getText().toString().trim();
                        if(checkNum(phoneNum)){
                            getMessage(phoneNum);
                        }
                        break;
                    case R.id.reg_next_step:
                        String inputCode = messageET.getText().toString();
                        //todo 第三方登陆用户昵称解决
                        if(checkAuthNum(inputCode)){
                           // Toast.makeText(context, "验证成功", Toast.LENGTH_LONG).show();
                            if(nickname !=null && !nickname.equals("")){
                                String password = inviterET.getText().toString();
                                if(!CheckUtil.checkPassWord(password)) {
                                    Toast.makeText(context, "请输入合法密码", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    RequestParams params = new RequestParams();
                                    params.put("username",phoneNum);
                                    params.put("nickname",nickname+"#"+System.currentTimeMillis());
                                    params.put("password",password);
                                    regHandler = getRegHandler();
                                    ApiClient.register(context,regHandler,params);
                                }


                            }else {
                                UIHelper.showNextRegView(context, phoneNum);
                            }

                        }
                        else {
                            Toast.makeText(context, "验证码输入错误", Toast.LENGTH_LONG).show();
                        }
                }
            }
        };
        sendMessageTV.setOnClickListener(listener);
        nextStep.setOnClickListener(listener);
    }

    private boolean checkNum(String phoneNum) {
        if ("".equals(phoneNum)) {
            Toast.makeText(context, "请输入手机号码", Toast.LENGTH_LONG).show();
            return false;
        } else if (CheckUtil.checkPhoneNumber(phoneNum)) {
            Toast.makeText(context, "手机格式不对", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }
    private Handler getRegHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1){
                    Toast.makeText(context, "注册成功", Toast.LENGTH_LONG).show();
//                    MySharePreference.putString("uid",);
                    UIHelper.showCommunityListView(context);
                }
                else {
                    JSONObject response = (JSONObject) msg.obj;
                    try {
                        String errInfo = response.getString("content");
                        Toast.makeText(context, errInfo, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
    private void getMessage(String phoneNum) {
        timer = 60;
        timeerHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        if (timer > 0) {
                            timer--;
                            sendMessageTV.setEnabled(false);
                            timeerHandler.sendEmptyMessageDelayed(1, 1000);
                            sendMessageTV.setText("（" + timer + "s）");
                        } else {
                            timer = 60;
                            sendMessageTV.setText("获取验证码");
                            sendMessageTV.setEnabled(true);
                        }

                }
            }
        };
        timeerHandler.sendEmptyMessageDelayed(1, 1000);
        /**
         * 获取listview的初始化Handler
         *
         * @return
         */
        validHandler = new Handler() {
            public void handleMessage(Message msg) {
                JSONArray returnCode = (JSONArray)msg.obj;
                if(msg.what == 1){
                    Toast.makeText(context, "验证码已发送", Toast.LENGTH_LONG).show();
                    try {
                        smsCode = ((JSONObject)returnCode.get(0)).getString("smsCode");
                        nextStep.setClickable(true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(context, "验证码发送失败", Toast.LENGTH_LONG).show();
                }
            }
        };
        String url = URL.HOST_VALID;
        ApiClient.reg_valid(url, validHandler, phoneNum);

    }
    private boolean checkAuthNum(String inputCode){

        if(!inputCode.equals(smsCode)){
            return false;
        }
        else {
            return true;
        }
    }

    public void initialHeader(){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("手机验证");
        headRight.setImageResource(0);
    }

}
