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
public class ArticleCommentActivity extends BaseActivity {
    private String articleId;
    private String replyAuthor;
    private EditText ETcomment;
    private Button   completeBtn;
    private Handler commentHandler;
    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_comment);
        context = this;
        articleId = getIntent().getStringExtra("articleId");
        replyAuthor = getIntent().getStringExtra("replyAuthor");
        initViews();
    }

    private void initViews(){
        initialHeader("回复");
        ETcomment = (EditText)findViewById(R.id.comment);
        if(replyAuthor!=null){
            ETcomment.setText("回复 @"+replyAuthor+" ：");
            ETcomment.setSelection(ETcomment.getText().length());

        }
        completeBtn = (Button)findViewById(R.id.comment_submit);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = ETcomment.getText().toString();
                {
                    RequestParams params = new RequestParams();
                    params.put("uid", uid);
                    params.put("content",content);
                    params.put("article_id",articleId);
                    commentHandler = getResetHandler();
                    ApiClient.doPostComment( commentHandler, params,1);
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
                    UIHelper.showArticleDetailView(context,articleId,"文章详情");
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
