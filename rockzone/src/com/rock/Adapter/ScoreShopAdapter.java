package com.rock.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rock.Activity.R;
import com.rock.Helper.MyImageLoader;
import com.rock.Listener.MyImageLoadingListener;
import com.rock.Listener.MyImageLoadingProgressListener;
import com.rock.Listener.MyShopGoodsClickListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

;

/**
 * Created by wym on 2014/11/3.
 */
public class ScoreShopAdapter extends BaseAdapter {
    private ArrayList<JSONObject> content;
    private LayoutInflater inflater;
    private Context UIContext;
    private String url1;
    private String url2;
    private int realPosition;
    private int textViewResourceId;
    private DisplayImageOptions options;
    public ScoreShopAdapter(Context context, int textViewResourceId, ArrayList<JSONObject> objects) {
        this.textViewResourceId = textViewResourceId;
        UIContext = context;
        content = objects;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.empty_photo) // resource or drawable
                .showImageForEmptyUri(R.drawable.empty_photo) // resource or drawable
                .showImageOnFail(R.drawable.connection_timeout) // resource or drawable
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

    }

    @Override
    public View getView(final int realPosition, View convertView, ViewGroup parent) {
        ViewHolder goods;
        if(convertView == null){
            convertView  = (LinearLayout) inflater.inflate(textViewResourceId, null);
            goods = new ViewHolder();
            goods.linearLayout1 = (LinearLayout) convertView.findViewById(R.id.linearLayout1);
            goods.linearLayout2 = (LinearLayout) convertView.findViewById(R.id.linearLayout2);
            convertView.setTag(goods);
        }else {
            goods = (ViewHolder)convertView.getTag();
        }

            LinearLayout goodsItem1 = goods.linearLayout1 ;
            LinearLayout goodsItem2 = goods.linearLayout2;

            TextView goodsName = (TextView)goodsItem1.findViewById(R.id.goods_name);
            ImageView goodsImage = (ImageView) goodsItem1.findViewById(R.id.goods_image);
            TextView scoreFee = (TextView) goodsItem1.findViewById(R.id.score_fee);
            TextView remain = (TextView) goodsItem1.findViewById(R.id.remain);

            TextView goodsName2 = (TextView)goodsItem2.findViewById(R.id.goods_name);
            ImageView goodsImage2 = (ImageView) goodsItem2.findViewById(R.id.goods_image);
            TextView scoreFee2 = (TextView) goodsItem2.findViewById(R.id.score_fee);
            TextView remain2 = (TextView) goodsItem2.findViewById(R.id.remain);
            try {
                url1 = (String) content.get(realPosition*2).get("goods_ico");
                String id = (String) content.get(realPosition*2).get("id");
                goodsItem1.setOnClickListener(new MyShopGoodsClickListener(UIContext,id));
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                MyImageLoader.getInstance().displayImage(url1, goodsImage, options, new MyImageLoadingListener(), new MyImageLoadingProgressListener());
                remain.setText((String) content.get(realPosition*2).get("goods_num"));
                scoreFee.setText((String) content.get(realPosition*2).get("tox_money_need"));
                goodsName.setText((String) content.get(realPosition*2).get("goods_name"));
                if(content.size()>realPosition*2+1){
                    String id = (String) content.get(realPosition*2+1).get("id");
                    goodsItem2.setOnClickListener(new MyShopGoodsClickListener(UIContext,id));
                    goodsName2.setText((String) content.get(realPosition*2+1).get("goods_name"));
                    remain2.setText((String) content.get(realPosition*2+1).get("goods_num"));
                    scoreFee2.setText((String) content.get(realPosition*2+1).get("tox_money_need"));
                    url2 = (String) content.get(realPosition*2+1).get("goods_ico");
                    ImageLoader.getInstance().displayImage(url2, goodsImage2, options, new MyImageLoadingListener(),new MyImageLoadingProgressListener());
                }
                else{
                    goodsItem2.removeAllViews();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        return convertView;
        }


    @Override
    public int getCount() {
        return (content.size()+1)/2;
    }

    @Override
    public Object getItem(int i) {
        return content.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void refresh(ArrayList<JSONObject> list) {
            content = list;
        notifyDataSetChanged();
    }
    static class ViewHolder {
        LinearLayout linearLayout1;
        LinearLayout linearLayout2;
        TextView text1;
        ImageView icon1;
        TextView text2;
        ImageView icon2;
    }
}