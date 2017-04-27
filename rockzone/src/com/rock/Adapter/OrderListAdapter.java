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
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.rock.Activity.R;
import com.rock.Helper.MyImageLoader;
import com.rock.Listener.GoodsOrderClickListener;
import com.rock.common.JsonUtil;
import com.rock.common.TimeUtil;
import com.rock.model.Goods;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

;

/**
 * Created by wym on 2014/11/3.
 */
public class OrderListAdapter extends BaseAdapter {
    private Goods goods;
    JSONArray content;
    LayoutInflater inflater;
    Context UIContext;
    ImageLoaderConfiguration config;
    DisplayImageOptions options;
    Intent intent;
    public OrderListAdapter(Context context, JSONArray objects) {
        intent = new Intent();
        UIContext = context;
        content = objects;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_rotate) // resource or drawable
                .showImageForEmptyUri(R.drawable.rock_zone_default_icon) // resource or drawable
                .showImageOnFail(R.drawable.rock_zone_default_icon) // resource or drawable
                .build();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View goodsItem = inflater.inflate(R.layout.order_list_item, null);
        TextView title = (TextView) goodsItem.findViewById(R.id.title);
        TextView createTime = (TextView) goodsItem.findViewById(R.id.create_time);
        TextView goodsCost = (TextView) goodsItem.findViewById(R.id.tox_money_need);
        TextView buyNum = (TextView) goodsItem.findViewById(R.id.buy_num);
        ImageView goodsIcon = (ImageView) goodsItem.findViewById(R.id.goods_icon);
        try {
            goods = JsonUtil.jsonToGoods(content.getJSONObject(position).getJSONObject("goods"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            goodsCost.setText(goods.getgoodsCost());
            title.setText(goods.getgoodsName());
            String time = content.getJSONObject(position).getString("createtime");
            MyImageLoader.getInstance().displayImage(goods.getgoodsIconUrl(),goodsIcon);
            createTime.setText(TimeUtil.getTime(Integer.parseInt(time)));
            buyNum.setText(content.getJSONObject(position).getString("goods_num"));
               } catch (JSONException e) {
            e.printStackTrace();
        }
        //todo 用户头像
//        ImageLoader.getInstance().displayImage(logoUrl, logo, options, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//
//            }
//
//            @Override
//            public void onLoadingFailed(String s, View view, FailReason failReason) {
//
//            }
//
//            @Override
//            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//
//            }
//
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//
//            }
//        }, new ImageLoadingProgressListener() {
//            @Override
//            public void onProgressUpdate(String imageUri, View view, int current, int total) {
//
//            }
//        });
//        goodsItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    String id=  (String)content.get(position).get("id");
//                    UIHelper.showCommunityDetailView(UIContext,id);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        goodsItem.setOnClickListener(new GoodsOrderClickListener(UIContext,goods));
        return goodsItem;
    }

    @Override
    public int getCount() {
        return content.length();
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

    public void refresh(JSONArray list) {
        content = list;
        notifyDataSetChanged();
    }

}