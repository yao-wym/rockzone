package com.rock.Listener;

import android.content.Context;
import android.view.View;
import com.rock.Helper.UIHelper;

/**
 * Created by wym on 2014/11/16.
 */
public class MyExploreGridListener implements View.OnClickListener {
    private String id;
    private Context context;
    public MyExploreGridListener(Context context,String itemID){
        this.context = context;
        id = itemID;
    }
    @Override
    public void onClick(View view) {
        UIHelper.showArticleDetailView(context,id,"文章详情");
    }
}
