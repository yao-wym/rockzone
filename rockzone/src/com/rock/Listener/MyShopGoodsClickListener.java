package com.rock.Listener;

import android.content.Context;
import android.view.View;
import com.rock.Helper.UIHelper;

/**
 * Created by wym on 2014/11/14.
 */
public class MyShopGoodsClickListener implements View.OnClickListener {
    Context context;
    String  id;
    public MyShopGoodsClickListener(Context context, String id){
        this.context = context;
        this.id = id;
    }
    @Override
    public void onClick(View view) {
        UIHelper.showGoodsDetailView(context, id);
    }
}
