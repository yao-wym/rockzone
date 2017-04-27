package com.rock.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rock.Activity.R;
import com.rock.Listener.MyImageLoadingListener;
import com.rock.Listener.MyImageLoadingProgressListener;
import com.rock.common.TimeUtil;
import com.rock.model.UserMessage;

import java.util.ArrayList;

;


/**
 * Created by wym on 2014/11/3.
 */
public class MessageListAdapter extends BaseAdapter {
    private String logoUrl;
    ArrayList<UserMessage> content;
    LayoutInflater inflater;
    Context context;
    DisplayImageOptions options;
    Intent intent;
    public MessageListAdapter(Context context, ArrayList<UserMessage> objects) {
        intent = new Intent();
        this.context = context;
        if(objects == null){
            content = new ArrayList<UserMessage>();
        }else {
            content = objects;
        }
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_rotate) // resource or drawable
                .showImageForEmptyUri(R.drawable.empty_photo) // resource or drawable
                .showImageOnFail(R.drawable.empty_photo) // resource or drawable
                .build();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View goodsItem = inflater.inflate(R.layout.message_list_item, null);
        ImageButton logo = (ImageButton) goodsItem.findViewById(R.id.user_head);
        TextView time = (TextView) goodsItem.findViewById(R.id.time);
        TextView title = (TextView) goodsItem.findViewById(R.id.title);
        TextView message = (TextView) goodsItem.findViewById(R.id.message);
        UserMessage userMessage = content.get(position);

            time.setText(TimeUtil.getTime(Integer.parseInt(userMessage.getCreateTime())));
            message.setText(userMessage.getContent());
            title.setText(userMessage.getTitle());
            logoUrl = userMessage.getFromUser().getHeadIcon();

        if(logoUrl == null||"".equals(logoUrl)){
            logo.setImageDrawable(context.getResources().getDrawable(R.drawable.rock_zone_default_icon));
        }else if(logoUrl.substring(0,4).equals("http")){
            ImageLoader.getInstance().displayImage(logoUrl, logo, options,new MyImageLoadingListener(), new MyImageLoadingProgressListener());
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo 显示用户详细信息
                }
            });
        }else {
            logo.setImageDrawable(context.getResources().getDrawable(R.drawable.rock_zone_default_icon));
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo 显示用户详细信息
                }
            });
        }
        goodsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    String id=  content.get(position).getId();
//                    UIHelper.showCommunityDetailView(context, id);

            }
        });
        return goodsItem;
    }

    @Override
    public int getCount() {
        if(content == null){
            return 0;
        }
        return content.size();
    }

    @Override
    public Object getItem(int i) {
        if(content == null){
            return null;
        }
        return content.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void refresh(ArrayList<UserMessage> list) {
        for(UserMessage userMessage:list){
            content.add(userMessage);
        }
        notifyDataSetChanged();
    }
}