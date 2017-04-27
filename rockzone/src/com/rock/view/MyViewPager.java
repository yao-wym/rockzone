package com.rock.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wym on 2014/12/8.
 */
    /**
     * 能够兼容ViewPager的ScrollView
     * @Description: 解决了ViewPager在ScrollView中的滑动反弹问题
     */
    public class MyViewPager extends ViewPager {
        // 滑动距离及坐标
        private float xDistance, yDistance, xLast, yLast;
        public MyViewPager(Context context) {
            super(context);
        }

        public MyViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }


        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return super.onInterceptTouchEvent(ev);
//            return false;
        }
//
//        @Override
//        public boolean onTouchEvent(MotionEvent ev) {
//            switch (ev.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    xDistance = yDistance = 0f;
//                    xLast = ev.getX();
//                    yLast = ev.getY();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    final float curX = ev.getX();
//                    final float curY = ev.getY();
//
//                    xDistance += Math.abs(curX - xLast);
//                    yDistance += Math.abs(curY - yLast);
//                    xLast = curX;
//                    yLast = curY;
//
//            }
//            return true;
////            return true;
////            return super.onInterceptTouchEvent(ev);
//        }
    }

