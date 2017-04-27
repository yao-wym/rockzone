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

/**
 * Created by wym on 2014/11/16.
 */
public class GoodsCommentActivity extends BaseActivity {
    private String goodsId;
    private String replyAuthor;
    private EditText ETcomment;
    private Button   completeBtn;
    private Handler goodsHandler;
    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_comment);
        context = this;
        goodsId = getIntent().getStringExtra("goodsId");
        initViews();
    }

    private void initViews(){
        initialHeader("商品评论");
        ETcomment = (EditText)findViewById(R.id.comment);
        completeBtn = (Button)findViewById(R.id.comment_submit);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = ETcomment.getText().toString();
                {
                    RequestParams params = new RequestParams();
                    params.put("uid", uid);
                    params.put("content",content);
                    params.put("goodsId",goodsId);
                    goodsHandler = getResetHandler();
                    ApiClient.doPostComment( goodsHandler, params,2);
                }

            }
        });
    }
    private Handler getResetHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    Toast.makeText(context, "评论成功", Toast.LENGTH_LONG).show();
                    UIHelper.showGoodsDetailView(context,goodsId);
                    finish();
                }
                else {
                    Toast.makeText(context,"评论失败", Toast.LENGTH_LONG).show();
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
