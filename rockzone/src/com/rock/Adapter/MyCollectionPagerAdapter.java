package com.rock.Adapter;

/**
 * Created by wym on 2014/11/2.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.rock.Activity.R;
import org.json.JSONArray;

import java.util.ArrayList;

;

/**
 * 填充ViewPager的页面适配器
 * @author caizhiming
 */
public class MyCollectionPagerAdapter extends PagerAdapter{
    private ArrayList<RelativeLayout> viewList = new ArrayList<RelativeLayout>();
    private LayoutInflater inflater;
    private String url;
    private String id;
    private Context context;
    private JSONArray advJSONArrayData;
    private DisplayImageOptions options;
    private LinearLayout bottom;
    public MyCollectionPagerAdapter(Context context,ArrayList<RelativeLayout> viewList) {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_rotate) // resource or drawable
                .showImageForEmptyUri(R.drawable.empty_photo) // resource or drawable
                .showImageOnFail(R.drawable.connection_timeout) // resource or drawable
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();
        this.context = context;
        this.viewList = viewList;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        RelativeLayout view = viewList.get(position);
        if(container.getChildAt(position) == null){
            container.addView(view);
        }
//        view.setAdapter(new CollectionListAdapter(context,new JSONArray()));
        return viewList.get(position);
    }
    @Override
    public int getCount() {

        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }


    @Override
    public void startUpdate(ViewGroup arg0) {


    }

    @Override
    public void finishUpdate(ViewGroup arg0) {


    }

}