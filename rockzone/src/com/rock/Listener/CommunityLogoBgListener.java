package com.rock.Listener;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by wym on 2014/11/6.
 */
public class CommunityLogoBgListener implements ImageLoadingListener{
        @Override
        public void onLoadingStarted(String imageUri, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            view.setBackgroundDrawable(new BitmapDrawable(bitmap));
        }


        @Override
        public void onLoadingCancelled(String imageUri, View view) {

        }

}
