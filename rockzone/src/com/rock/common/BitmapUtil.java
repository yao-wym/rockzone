package com.rock.common;

import android.content.Context;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 图片工具类
 * 
 * @author pew
 * 
 */
public class BitmapUtil {
    /**
     * 画一个圆角图
     * 
     * @param bitmap
     *            传入的bitmap
     * @param roundPx
     *            圆角弧度
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        if (bitmap == null) {
            return bitmap;
        }
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        } catch (OutOfMemoryError e) {
            // TODO: handle exception
            System.gc();
            return null;
        }
    }

    /**
     * 是bitmap旋转一定角度
     * 
     * @param b
     *            传入的bitmap
     * @param degrees
     *            旋转角度
     * @return
     */
    public static Bitmap rotate(Bitmap b, int degrees) {
        if (degrees != 0 && b != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) b.getWidth() / 2,
                    (float) b.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
                        b.getHeight(), m, true);
                return b2;// 正常情况下返回旋转角度的图
            } catch (OutOfMemoryError ex) {
                return b;// 内存溢出返回原图
            } finally {
                b.recycle();// 释放资源
            }
        }
        return b;
    }

    /**
     * 从view 得到图片
     * 
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
    }
    
	/**
	 * Drawable 转 Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmapByBD(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		return bitmapDrawable.getBitmap();
	}

	/**
	 * Bitmap 转 Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawableByBD(Context context, Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
		return drawable;
	}

    /**
     * 生成原型图标的bitmap
     * @param bitmap
     * @return
     */
    public static Bitmap getCircleImage( Bitmap bitmap){
        int x = bitmap.getWidth();
        int y = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(x,
                y, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        // 根据原来图片大小画一个矩形
        final Rect rect = new Rect(0, 0, x, y);
        paint.setAntiAlias(true);
        paint.setColor(color);
        // 画出一个圆
        canvas.drawCircle(x/2, x/2, x/2-15, paint);
        //canvas.translate(-25, -6);
        // 取两层绘制交集,显示上层
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        // 将图片画上去
        canvas.drawBitmap(bitmap, rect, rect, paint);
        // 返回Bitmap对象
        return output;
    }
    /**
     * 生成原型图标的bitmap
     * @param bitmap
     * @return
     */
    public static Bitmap getCircleImage( Bitmap bitmap,int x,int y){
        Bitmap output = Bitmap.createBitmap(x,
                y, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        // 根据原来图片大小画一个矩形
        final Rect rect = new Rect(0, 0, x, y);
        paint.setAntiAlias(true);
        paint.setColor(color);
        // 画出一个圆
        canvas.drawCircle(x/2, x/2, x/2-15, paint);
        //canvas.translate(-25, -6);
        // 取两层绘制交集,显示上层
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        // 将图片画上去
        canvas.drawBitmap(bitmap, rect, rect, paint);
        // 返回Bitmap对象
        return output;
    }
}
