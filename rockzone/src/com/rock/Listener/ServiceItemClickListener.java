package com.rock.Listener;

import android.content.Context;
import android.view.View;
import com.rock.Helper.UIHelper;

/**
 * Created by wym on 2014/11/14.
 */
public class ServiceItemClickListener implements View.OnClickListener {
    private String cateId;
    private int titleId;
    private Context context;

    public ServiceItemClickListener(Context context, String id, int titleId) {
        this.cateId = id;
        this.titleId = titleId;
        this.context = context;
    }



    @Override
    public void onClick(View view) {
        UIHelper.showCommunityArticleView(context,cateId,titleId);
    }
}
