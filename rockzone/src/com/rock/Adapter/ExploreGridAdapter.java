package com.rock.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rock.Helper.MyImageLoader;
import com.rock.Listener.MyExploreGridListener;
import com.rock.Activity.R;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by wym on 2014/11/16.
 */
public class ExploreGridAdapter extends BaseAdapter {
    private JSONArray data;
    private Context context;
    private int layoutId;
    private String id;
    private String url;
    private String title;
    private String description;
    private LayoutInflater inflater;
    public ExploreGridAdapter(Context context,int layoutId,JSONArray data){
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout item = (LinearLayout)inflater.inflate(layoutId,null);

        ImageView imageView = (ImageView) item.findViewById(R.id.bar_image);
        TextView titleView = (TextView) item.findViewById(R.id.title);
        TextView describeView = (TextView) item.findViewById(R.id.describe);
            try {
                id = data.getJSONObject(i).getString("id");
                url = data.getJSONObject(i).getString("cover_id");
                title = data.getJSONObject(i).getString("title");
                description = data.getJSONObject(i).getString("description");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            titleView.setText(title);
            describeView.setText(description);
            item.setOnClickListener(new MyExploreGridListener(context,id));
        MyImageLoader.getInstance().displayImage(url,imageView);
        return item;
    }
}
