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
import com.rock.Listener.ExploreListClickListener;
import com.rock.Listener.MyImageLoadingListener;
import com.rock.Listener.MyImageLoadingProgressListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

;

/**
 * Created by wym on 2014/11/3.
 */
public class ExploreListAdapter extends BaseAdapter {
    private String url;
    private JSONArray content;
    LayoutInflater inflater;
    Context UIContext;
    DisplayImageOptions options;
    Intent intent;
    private Context context;
    private String id;
    private JSONObject data;
    private ImageView imageView;
    private TextView title;
    private TextView describe;
    private TextView zanNum;
    private int textViewResourceId;
    public ExploreListAdapter(Context context, int textViewResourceId, JSONArray objects) {
        intent = new Intent();
        UIContext = context;
        content = objects;
        this.context = context;
        this.textViewResourceId = textViewResourceId;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_rotate) // resource or drawable
                .showImageForEmptyUri(R.drawable.empty_photo) // resource or drawable
                .showImageOnFail(R.drawable.connection_timeout) // resource or drawable
                .build();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View imageItem = inflater.inflate(textViewResourceId, null);
        title = (TextView) imageItem.findViewById(R.id.title);
        describe = (TextView) imageItem.findViewById(R.id.describe);
        imageView = (ImageView) imageItem.findViewById(R.id.image_view);
        try {
            data = content.getJSONObject(position);
            title.setText(data.getString("title"));
            describe.setText(data.getString("description"));
            url = data.getString("cover_id");
            id = data.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyImageLoader.getInstance().displayImage(url,imageView,options, new MyImageLoadingListener(), new MyImageLoadingProgressListener());
        imageItem.setOnClickListener(new ExploreListClickListener(context,id));
        return imageItem;
    }

    @Override
    public int getCount() {
        return content.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return content.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
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