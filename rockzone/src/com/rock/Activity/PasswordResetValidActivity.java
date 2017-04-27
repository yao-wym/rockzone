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
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
import com.rock.Helper.URL;
import com.rock.common.CheckUtil;
;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PasswordResetValidActivity extends BaseActivity {
    private Button nextStep;
    private EditText numberET;
    private EditText inviterET;
    private EditText messageET;
    private EditText nickName;
    private EditText userName;
    private EditText passWord;
    private EditText pwdConfirm;
    private Button   completeBtn;
    private String validUrl;
    private int timer;
    private String uid;
    private TextView sendMessageTV;
    private Handler timeerHandler;
    private Handler regHandler;
    private Handler validHandler;
    private String smsCode;
    private String phoneNum;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_valid);
        context = this;
        initialHeader();
        inflater = LayoutInflater.from(context);
        initViews();
        addListeners();
    }

    private void initViews() {
        nextStep = (Button) findViewById(R.id.reset_next_step);
        numberET = (EditText) findViewById(R.id.reset_et_phone);
        messageET = (EditText) findViewById(R.id.reset_et_message);
        sendMessageTV = (TextView) findViewById(R.id.reset_tv_sendMessage);
    }

    private void addListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.reset_tv_sendMessage:
                        phoneNum = numberET.getText().toString().trim();
                        if(checkNum(phoneNum)){
                            getMessage(phoneNum);
                        }
                        break;
                    case R.id.reset_next_step:
                        String inputCode = messageET.getText().toString();
                        if(checkAuthNum(inputCode)){
                            Toast.makeText(context, "验证成功", Toast.LENGTH_LONG).show();
                                UIHelper.showNextResetView(context, uid);
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
                        uid = ((JSONObject)returnCode.get(0)).getString("uid");
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
        String url = URL.HOST_RESET_VALID;
        ApiClient.resetValid(url, validHandler, phoneNum);

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
