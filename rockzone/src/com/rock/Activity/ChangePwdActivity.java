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
import org.json.JSONException;
import org.json.JSONObject;

public class ChangePwdActivity extends BaseActivity {
    private EditText oldPwd;
    private EditText newPwd;
    private EditText comfirmPwd;
    private Button   completeBtn;
    private Handler changeHandler;
    private String  uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        context = this;
        initialHeader();
        initViews();
    }

    private void initViews(){
        oldPwd = (EditText)findViewById(R.id.old_password);
        newPwd = (EditText)findViewById(R.id.new_password);
        comfirmPwd  = (EditText)findViewById(R.id.confirm_password);
        completeBtn = (Button)findViewById(R.id.pwd_submit);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword  = oldPwd.getText().toString();
                String newPassword = newPwd.getText().toString();
                String confirm  = comfirmPwd.getText().toString();
                if(!CheckUtil.checkPassWord(oldPassword)){
                    Toast.makeText(context, "原密码错误", Toast.LENGTH_LONG).show();
                }else if(!CheckUtil.checkPassWord(newPassword)){
                    Toast.makeText(context, "请输入合法密码", Toast.LENGTH_LONG).show();
                }else if(!newPassword.equals(confirm)){
                    Toast.makeText(context, "两次输入密码不同", Toast.LENGTH_LONG).show();
                }else{
                    RequestParams params = new RequestParams();
                    params.put("uid",uid);
                    params.put("old_password",oldPassword);
                    params.put("new_password",newPassword);
                    changeHandler = getChangeHandler();
                    ApiClient.changePassword(context, changeHandler, params);
                }

            }
        });
    }
    private Handler getChangeHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1){
                    Toast.makeText(context, "修改成功成功", Toast.LENGTH_LONG).show();
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
        head.setText("修改密码");
        headRight.setImageResource(0);
    }
}
