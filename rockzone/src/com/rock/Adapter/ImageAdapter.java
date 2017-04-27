package com.rock.Adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import com.rock.Activity.MainActivity;
import com.rock.Activity.R;
import com.rock.Listener.ImageCallBack;
import com.rock.http.AsyncBitmapLoader;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

	private ArrayList<String> data;
	private LayoutInflater inflater;
	private int width;
    private Context context;
	private AsyncBitmapLoader asyncBitmapLoader;

	public ImageAdapter(Context context, ArrayList<String> data, int width) {
        this.context = context;
		this.data = data;
		this.inflater = LayoutInflater.from(context);
		this.width = width;
		this.asyncBitmapLoader = new AsyncBitmapLoader();
	}

	@Override
	public int getCount() {
		int count = 0;
		if (data != null) {
			count = data.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		String item = null;
		if (data != null) {
			item = data.get(position);
		}
		return item;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.cell, null);
		}
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
		convertView.setLayoutParams(new Gallery.LayoutParams(width, 300));
		//iv.setScaleType(ImageView.ScaleType.FIT_XY);
		BitmapDrawable bitmapDrawable = asyncBitmapLoader.loadBitmap(
				data.get(position), MainActivity.pageImageView, new ImageCallBack() {

					@Override
					public void imageLoad(ImageView imageView,
							BitmapDrawable bitmap) {
                        if(bitmap != null)
                        {
                            imageView.setImageBitmap(bitmap.getBitmap());
                            notifyDataSetChanged();
                        }
					}
				});
		if (bitmapDrawable != null) {
//            Main.pageImageView.setImageBitmap(bitmapDrawable.getBitmap());
            iv.setBackgroundDrawable(bitmapDrawable);
		} else
        {
            iv.setBackgroundResource(R.drawable.empty);

		}

		return convertView;
	}

}
