package com.rock.Listener;

import android.content.Context;
import android.view.View;
import com.rock.Helper.UIHelper;

/**
 * Created by wym on 2014/11/14.
 */
public class ExploreListClickListener implements View.OnClickListener {
    private Context context;
    private String id;
    public ExploreListClickListener(Context context,String id){
        this.id = id;
        this.context = context;
    }
    @Override
    public void onClick(View view) {
        UIHelper.showArticleDetailView(context,id,"文章详情");
    }
}
