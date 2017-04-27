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

public class ResetPasswordActivity extends BaseActivity {
    private EditText passWord;
    private EditText pwdConfirm;
    private Button   completeBtn;
    private Handler resetHandler;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        context = this;
        initialHeader();
        uid = getIntent().getStringExtra("uid");
        initViews();
    }

    private void initViews(){
        passWord = (EditText)findViewById(R.id.password);
        pwdConfirm  = (EditText)findViewById(R.id.password_confirm);
        completeBtn = (Button)findViewById(R.id.reset_submit);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = passWord.getText().toString();
                String confirm  = pwdConfirm.getText().toString();
                if(!CheckUtil.checkPassWord(password)){
                    Toast.makeText(context, "请输入合法密码", Toast.LENGTH_LONG).show();
                }else if(!password.equals(confirm)){
                    Toast.makeText(context, "两次输入密码不同", Toast.LENGTH_LONG).show();
                }else{
                    RequestParams params = new RequestParams();
                    params.put("uid",uid);
                    params.put("new_password",password);
                    resetHandler = getResetHandler();
                    ApiClient.resetPassword(context,resetHandler,params);
                }

            }
        });
    }
    private Handler getResetHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1){
                    Toast.makeText(context, "修改成功", Toast.LENGTH_LONG).show();
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
        head.setText("密码重置");
        headRight.setImageResource(0);
    }
}
