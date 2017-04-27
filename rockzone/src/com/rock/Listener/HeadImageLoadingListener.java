package com.rock.Listener;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.rock.common.BitmapUtil;

/**
 * Created by wym on 2014/11/12.
 */
public class HeadImageLoadingListener implements ImageLoadingListener {
    @Override
    public void onLoadingStarted(String s, View view) {

    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {

    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        if(bitmap != null){
            Bitmap targetBitmap = BitmapUtil.getCircleImage(bitmap);
            ((ImageView)view).setImageBitmap(targetBitmap);
        }
    }

    @Override
    public void onLoadingCancelled(String s, View view) {

    }
}
