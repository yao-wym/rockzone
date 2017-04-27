package com.rock.Listener;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.rock.Activity.R;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

;

/**
 * Created by wym on 2014/11/16.
 */
public class AdvPageScrollListener implements ViewPager.OnPageChangeListener{
    ViewPager viewPager;
    private ScheduledExecutorService scheduledExecutorService;
    int currentItem;
    private ArrayList<ImageView> bottom;
    boolean isAutoPlay = false;
    public AdvPageScrollListener(View viewPager,ArrayList<ImageView> bottom){
        this.viewPager = (ViewPager) viewPager;
        this.bottom = bottom;
        startPlay();
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
        switch (arg0) {
            case 1:// 手势滑动，空闲中
                isAutoPlay = false;
                break;
            case 2:// 界面切换中
                isAutoPlay = true;
                break;
            case 0:// 滑动结束，即切换完毕或者加载完毕
                // 当前为最后一张，此时从右向左滑，

                if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                    viewPager.setCurrentItem(0);
                }
                // 当前为第一张，此时从左向右滑，则切换到最后一张
                else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                    viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                }

                break;
        }
    }
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }
    @Override
    public void onPageSelected(int pos) {
        for(int i=0;i < 3;i++){
                (bottom.get(i)).setBackgroundResource(R.drawable.feature_point);
        }
        (bottom.get(pos)).setBackgroundResource(R.drawable.feature_point_cur);
    }
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }

    };
    private class SlideShowTask implements Runnable {
        @Override
        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % viewPager.getChildCount();
                handler.obtainMessage().sendToTarget();
            }
        }
    }
    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 5, 10, TimeUnit.SECONDS);
    }
}
