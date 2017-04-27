package com.rock.Listener;

import android.content.Context;
import android.view.View;
import com.rock.Helper.UIHelper;

/**
 * Created by wym on 2014/11/17.
 */
public class CommentCickListener implements View.OnClickListener {
    private String articleId;
    private String replyAuthor;
    private Context context;
    public CommentCickListener(Context context,String articleId,String replyAuthor){
        this.context = context;
        this.articleId = articleId;
        this.replyAuthor = replyAuthor;
    }
    @Override
    public void onClick(View view) {
        UIHelper.showCommentView(context, articleId, replyAuthor);
    }
}
