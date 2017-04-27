package com.rock.common;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.rock.Activity.R;
import com.rock.Adapter.MyPagerAdapter;
import com.rock.Helper.ApiClient;
import com.rock.Listener.AdvPageScrollListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

;

/**
 * Created by wym on 2014/11/21.
 */
public class AdvPageUtils {
    private JSONArray advJSONArrayData;
    private Context context;
    private LayoutInflater inflater;
    private LinearLayout bottom;
    private ViewPager advViewpager;
    private Handler advHandler;
    private RelativeLayout pagerViewItem;
    private int pagerViewItemId;
    private static AdvPageUtils obj;
    private String advUrl;

    private AdvPageUtils() {

    }

    public static AdvPageUtils getInstance(){
        if(obj == null){
            obj = new AdvPageUtils();
        }
        return obj;
    }
    public void initialize(Context context,String advUrl,ViewPager advPagerView,LinearLayout bottom) throws JSONException {
        this.advUrl = advUrl;
        this.advViewpager = advPagerView;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.bottom = bottom;
        initAdvPager();
    }
    private void initAdvPager() throws JSONException {
        advHandler = getAdvHandler();
        ApiClient.getExploreAdvList(context,advHandler,advUrl);
    }
    public Handler getAdvHandler(){
    return new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                advJSONArrayData = (JSONArray) msg.obj;
                ArrayList<ImageView> dotViews = new ArrayList<ImageView>();
                for(int i=0;i<advJSONArrayData.length();i++){
                    ImageView point = new ImageView(context);
                    if(i==0){
                        point.setBackgroundResource(R.drawable.feature_point_cur);
                    }
                    else{
                        point.setBackgroundResource(R.drawable.feature_point);
                    }
                    bottom.addView(point);
                    dotViews.add(point);
                }
                MyPagerAdapter adapter = new MyPagerAdapter(context,advJSONArrayData);
                advViewpager.setAdapter(adapter);
                advViewpager.setOnPageChangeListener(new AdvPageScrollListener(advViewpager,dotViews));
            }
            }
    };
}
}
