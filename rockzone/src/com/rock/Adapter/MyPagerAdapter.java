package com.rock.Adapter;

/**
 * Created by wym on 2014/11/2.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.rock.Activity.R;
import com.rock.Helper.MyImageLoader;
import com.rock.Listener.MyExploreGridListener;
import com.rock.Listener.MyImageLoadingListener;
import com.rock.Listener.MyImageLoadingProgressListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

;

/**
 * 填充ViewPager的页面适配器
 * @author caizhiming
 */
public class MyPagerAdapter  extends PagerAdapter{
    private ArrayList<RelativeLayout> viewList = new ArrayList<RelativeLayout>();
    private LayoutInflater inflater;
    private String url;
    private String id;
    private Context context;
    private JSONArray advJSONArrayData;
    private DisplayImageOptions options;
    private LinearLayout bottom;
    public MyPagerAdapter(Context context, JSONArray advJSONArrayData) {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_rotate_2_1) // resource or drawable
                .showImageForEmptyUri(R.drawable.empty_photo) // resource or drawable
                .showImageOnFail(R.drawable.empty_photo) // resource or drawable
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.bottom = (LinearLayout) bottom;
        this.advJSONArrayData = advJSONArrayData;
        for(int i=0;i<advJSONArrayData.length();i++){
            RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.adv_pager_item, null);
            viewList.add(item);
        }

    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // TODO Auto-generated method stub

        View view = viewList.get(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if(container.getChildAt(position) == null){
            container.addView(view);
        }
        try {
            id =  advJSONArrayData.getJSONObject(position).getString("id");
            url = advJSONArrayData.getJSONObject(position).getString("cover_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.setOnClickListener(new MyExploreGridListener(context, id));
        MyImageLoader.getInstance().displayImage(url, imageView, options, new MyImageLoadingListener(),new MyImageLoadingProgressListener());
        return viewList.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return advJSONArrayData.length();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }


    @Override
    public void startUpdate(ViewGroup arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void finishUpdate(ViewGroup arg0) {
        // TODO Auto-generated method stub

    }

}