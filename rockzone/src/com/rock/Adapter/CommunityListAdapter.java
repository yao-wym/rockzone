package com.rock.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.baidu.mapapi.cloud.CloudPoiInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.rock.Activity.R;
import com.rock.Helper.UIHelper;
import com.rock.Listener.MyImageLoadingListener;
import com.rock.Listener.MyImageLoadingProgressListener;
import com.rock.common.MySharePreference;

import java.util.ArrayList;

;

/**
 * Created by wym on 2014/11/3.
 */
public class CommunityListAdapter extends BaseAdapter {
    private CloudPoiInfo poiInfo;
    private String logoUrl;
    ArrayList<CloudPoiInfo> content;
    LayoutInflater inflater;
    Context UIContext;
    ImageLoaderConfiguration config;
    DisplayImageOptions options;
    Intent intent;
    public CommunityListAdapter(Context context, int textViewResourceId, ArrayList<CloudPoiInfo> objects) {
        intent = new Intent();
        UIContext = context;
        content = objects;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_rotate) // resource or drawable
                .showImageForEmptyUri(R.drawable.empty_photo) // resource or drawable
                .showImageOnFail(R.drawable.empty_photo) // resource or drawable
                .build();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View Community_item = inflater.inflate(R.layout.community_list_item, null);
        ImageButton logo = (ImageButton) Community_item.findViewById(R.id.community_logo);
        TextView address = (TextView) Community_item.findViewById(R.id.address);
        logo.setImageResource(R.drawable.shouye_home_press);
        TextView community_name = (TextView) Community_item.findViewById(R.id.community_name);

        address.setText(content.get(position).address);
        community_name.setText(content.get(position).title);
            logoUrl = (String) content.get(position).extras.get("logo");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        ImageLoader.getInstance().displayImage(logoUrl, logo, options, new MyImageLoadingListener(), new MyImageLoadingProgressListener());
        Community_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        poiInfo = content.get(position);
        String id = String.valueOf(poiInfo.extras.get("foreign_key"));
        if(id == null||id.equals("null")||id.equals("")){
            id = "1";
        }
        MySharePreference.putString(UIContext,"communityId",id);
        UIHelper.showCommunityDetailView(UIContext,id);

            }
        });
        return Community_item;
    }

    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public Object getItem(int i) {
        return content.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void refresh(ArrayList<CloudPoiInfo> list) {
        content = list;
        notifyDataSetChanged();
    }
}