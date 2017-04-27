package com.rock.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.rock.Activity.R;
import com.rock.Helper.MyImageLoader;
import com.rock.Listener.GoodsItemClickListener;
import com.rock.model.Goods;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wym on 2014/11/3.
 */
public class CollectionGoodsAdapter extends BaseAdapter {
    private ArrayList<Goods> goodsArrayList;
    private int layoutId;
    JSONArray content;
    LayoutInflater inflater;
    Context context;
    DisplayImageOptions options;
    Intent intent;
    public CollectionGoodsAdapter(Context context, ArrayList<Goods> goodsArrayList) {
        intent = new Intent();
        this.context = context;
        this.goodsArrayList = goodsArrayList;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_rotate) // resource or drawable
                .showImageForEmptyUri(R.drawable.empty_photo) // resource or drawable
                .showImageOnFail(R.drawable.rock_zone_default_icon) // resource or drawable
                .build();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View goodsItemView = inflater.inflate(R.layout.collection_goods_item, null);
        Goods goodsItem = goodsArrayList.get(position);
        TextView TVgoodsName = (TextView) goodsItemView.findViewById(R.id.goods_name);
        TextView TVdescribe = (TextView) goodsItemView.findViewById(R.id.goods_instruct);
        ImageView IVicon = (ImageView)goodsItemView.findViewById(R.id.goods_icon);
        TVgoodsName.setText(goodsItem.getgoodsName());
        TVdescribe.setText(goodsItem.getgoodsInstruct());
        MyImageLoader.getInstance().displayImage(goodsItem.getgoodsIconUrl(), IVicon, options, 200,200);
        goodsItemView.setOnClickListener(new GoodsItemClickListener(context,goodsItem));
        return goodsItemView;
    }

    @Override
    public int getCount() {
        if(goodsArrayList == null ){
            return 0;
        }
        return goodsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        JSONObject item=null;
        try {
            item = content.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void refresh(ArrayList<Goods> list) {
        goodsArrayList = list;
        notifyDataSetChanged();
    }
}