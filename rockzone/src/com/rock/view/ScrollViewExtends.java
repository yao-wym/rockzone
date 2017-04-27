package com.rock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * Created by wym on 2014/12/8.
 */
/**
 * 能够兼容ViewPager的ScrollView
 * @Description: 解决了ViewPager在ScrollView中的滑动反弹问题
 */
public class ScrollViewExtends extends PullToRefreshScrollView {
    private boolean canScroll;

    private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;
    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;

    public ScrollViewExtends(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public ScrollViewExtends(Context context, Mode mode) {
        super(context, mode);

    }


    public ScrollViewExtends(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mGestureDetector = new GestureDetector(new YScrollDetector());
//        canScroll = true;
    }

    public ScrollViewExtends(Context context) {
        super(context);
    }

    @Override
    protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
        return super.createRefreshableView(context, attrs);
    }
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        super.onInterceptTouchEvent(ev);
//        if(ev.getAction() == MotionEvent.ACTION_UP)
//            canScroll = true;
//        return false;
////        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
//    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }
//    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            if(canScroll)
//                if (Math.abs(distanceY) >= Math.abs(distanceX))
//                    canScroll = true;
//                else
//                    canScroll = false;
//            return canScroll;
//        }
//    }
}
