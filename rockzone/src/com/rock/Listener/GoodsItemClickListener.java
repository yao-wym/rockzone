package com.rock.Listener;

import android.content.Context;
import android.view.View;
import com.rock.Helper.UIHelper;
import com.rock.model.Goods;

/**
 * Created by wym on 2014/11/25.
 */
public class GoodsItemClickListener implements View.OnClickListener {
    private Goods goods;
    private Context context;
    public GoodsItemClickListener(Context context, Goods goods){
        this.goods = goods;
        this.context = context;
    }
    @Override
    public void onClick(View view) {
        String id=  goods.getgoodsId();
        UIHelper.showGoodsDetailView(context, id);
    }
}
