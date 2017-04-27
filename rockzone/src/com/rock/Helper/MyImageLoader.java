package com.rock.Helper;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.rock.Listener.MyImageLoadingListener;
import com.rock.Listener.MyImageLoadingProgressListener;

/**
 * Created by wym on 2014/12/7.
 */
public class MyImageLoader {
    int width;
    int height;
    String url;
    View view;
    Handler handler;
    DisplayImageOptions options;
    public static MyImageLoader getInstance(){
          MyImageLoader myImageLoader = new MyImageLoader();
        return myImageLoader;
    }
    public void displayImage(String url, View view) {
        this.view = view;
        this.url = url;
//        handler = getHandler();
        view.post(new myImageRunnable());

    }

    public void displayImage(String url, View view, DisplayImageOptions options, ImageLoadingListener myImageLoadingListener, ImageLoadingProgressListener myImageLoadingProgressListener) {
        this.options = options;
        this.view = view;
        this.url = url;
//        handler = getHandler();
        view.post(new myImageRunnable2());

    }
    public void displayImage(String url, View view, DisplayImageOptions options,int width,int height) {
        this.options = options;
        this.width = width;
        this.height = height;
        this.view = view;
        this.url = url;
//        handler = getHandler();
        String imgUrl = url + "?imageMogr2/format/jpg"+"/gravity/center/";
        ImageLoader.getInstance().displayImage(imgUrl, (ImageView) view, options, new MyImageLoadingListener(), new MyImageLoadingProgressListener());


    }
    class myImageRunnable implements Runnable {
        @Override
        public void run() {
            width = (int) (view.getMeasuredWidth()/1.5);
            height = (int) (view.getMeasuredHeight()/1.5);
            String imgUrl = url + "?imageMogr2/thumbnail/" + width + "x" + height+"/format/jpg";
            ImageLoader.getInstance().displayImage(imgUrl, (ImageView) view);
        }
    }
    class myImageRunnable2 implements Runnable {
        @Override
        public void run() {
            width = (int) (view.getMeasuredWidth()/1.5);
            height = (int) (view.getMeasuredHeight()/1.5);
            String imgUrl = url + "?imageMogr2/thumbnail/" + width + "x" + height+"/format/jpg";
            ImageLoader.getInstance().displayImage(imgUrl, (ImageView) view, options, new MyImageLoadingListener(), new MyImageLoadingProgressListener());

        }
    }
    class myImageRunnable3 implements Runnable {
        @Override
        public void run() {

        }
    }
    //    Handler getHandler(){
//        return new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                width =view.getMeasuredWidth();
//                height =view.getMeasuredHeight();
//                String imgUrl = url+"?imageMogr2/thumbnail/"+width+"x"+height;
//                ImageLoader.getInstance().displayImage(imgUrl,(ImageView)view);
//            }
//        };
//    }//    Handler getHandler(){
//        return new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                width =view.getMeasuredWidth();
//                height =view.getMeasuredHeight();
//                String imgUrl = url+"?imageMogr2/thumbnail/"+width+"x"+height;
//                ImageLoader.getInstance().displayImage(imgUrl,(ImageView)view);
//            }
//        };
//    }
}
