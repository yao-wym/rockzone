package com.rock.Listener;

import android.content.Context;
import android.view.View;
import com.rock.Helper.UIHelper;

/**
 * Created by wym on 2014/11/14.
 */
public class MyArticleListItemClickListener implements View.OnClickListener {
    Context context;
    String  id;
    public MyArticleListItemClickListener(Context context, String id){
        this.context = context;
        this.id = id;
    }
    @Override
    public void onClick(View view) {
        UIHelper.showArticleDetailView(context, id,"文章详情");
    }
}
