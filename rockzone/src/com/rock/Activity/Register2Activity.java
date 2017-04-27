package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.RequestParams;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
import com.rock.common.CheckUtil;
;
import org.json.JSONException;
import org.json.JSONObject;

public class Register2Activity extends BaseActivity {
    private EditText nickName;
    private EditText passWord;
    private EditText pwdConfirm;
    private Button   completeBtn;
    private Handler regHandler;
    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rock_register_2);
        context = this;
        initialHeader();
        phoneNum = getIntent().getStringExtra("phoneNum");
        initViews();
    }

    private void initViews(){
        nickName = (EditText)findViewById(R.id.reg_nickname);
        passWord = (EditText)findViewById(R.id.reg_password);
        pwdConfirm  = (EditText)findViewById(R.id.reg_confirm);
        completeBtn = (Button)findViewById(R.id.reg_submit);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = phoneNum;
                String nickname = nickName.getText().toString();
                String password = passWord.getText().toString();
                String confirm  = pwdConfirm.getText().toString();
                if("".equals(username)){
                    Toast.makeText(context, "昵称不能为空", Toast.LENGTH_LONG).show();
                }else if("".equals(nickname)){

                }else if(!CheckUtil.checkPassWord(password)){
                    Toast.makeText(context, "请输入合法密码", Toast.LENGTH_LONG).show();
                }else if(!password.equals(confirm)){
                    Toast.makeText(context, "两次输入密码不同", Toast.LENGTH_LONG).show();
                }else{
                    RequestParams params = new RequestParams();
                    params.put("username",username);
                    params.put("nickname",nickname);
                    params.put("password",password);
                    regHandler = getRegHandler();
                    ApiClient.register(context,regHandler,params);
                }

            }
        });
    }
    private Handler getRegHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1){
                    Toast.makeText(context, "注册成功", Toast.LENGTH_LONG).show();
                    UIHelper.showLoginView(context,1);
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
    public void initialHeader(){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("注 册");
        headRight.setImageResource(0);
    }
}
