package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.loopj.android.http.RequestParams;
import com.rock.Helper.ApiClient;
import com.rock.Helper.UIHelper;
;

/**
 * Created by wym on 2014/11/16.
 */
public class ForumPostActivity extends BaseActivity {
    private String forumId;
    private String category_id;
    private EditText ETtitle;
    private EditText ETcontent;
    private EditText ETdescription;
    private Button   completeBtn;
    private Handler postHandler;
    private ArrayAdapter spinnerAdapter;
    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_post);
        context = this;
        forumId = getIntent().getStringExtra("forumId");
        initViews();
    }

    private void initViews(){
        initialHeader("发 帖");
//        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.post_category, android.R.layout.simple_spinner_item);
//
//        //设置下拉列表的风格
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        //将adapter2 添加到spinner中
//        spinner.setAdapter(spinnerAdapter);
//
//        //添加事件Spinner事件监听
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                category_id = String.valueOf(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        //设置默认值
//        spinner.setVisibility(View.VISIBLE);
        ETtitle = (EditText)findViewById(R.id.title);
        ETcontent = (EditText)findViewById(R.id.content);
        ETdescription = (EditText) findViewById(R.id.description);
        completeBtn = (Button)findViewById(R.id.post_submit);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.showProgressDialog(context);
                String title = ETtitle.getText().toString();
                String description = ETdescription.getText().toString();
                String content = ETcontent.getText().toString();
                {
                    RequestParams params = new RequestParams();
                    params.put("uid", uid);
                    params.put("content",content);
                    params.put("title",title);
                    params.put("type",2);
                    params.put("category_id",forumId);
                    params.put("description",description);
                    postHandler = getPostHandler();
                    ApiClient.forumPost(context, postHandler, params);
                }

            }
        });
    }
    private Handler getPostHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    UIHelper.dismissProgressDialog();
                    Toast.makeText(context, "发表成功", Toast.LENGTH_LONG).show();
                    UIHelper.showCommunityArticleView(context, forumId, R.string.main_forum);
                    finish();
                }
                else {UIHelper.dismissProgressDialog();
                    Toast.makeText(context,"发表失败", Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    public void initialHeader(String title){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText(title);
    }
}
