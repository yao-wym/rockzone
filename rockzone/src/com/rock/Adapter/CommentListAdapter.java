package com.rock.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.baidu.mapapi.cloud.CloudPoiInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.rock.Activity.R;
import com.rock.Listener.HeadImageLoadingListener;
import com.rock.Listener.MyImageLoadingProgressListener;
import com.rock.common.TimeUtil;
import com.rock.model.Comment;

import java.util.ArrayList;

;

/**
 * Created by wym on 2014/11/3.
 */
public class CommentListAdapter extends BaseAdapter {
    private CloudPoiInfo poiInfo;
    private String logoUrl;
    private EditText ETcomment;
    ArrayList<Comment> content;
    LayoutInflater inflater;
    Context UIContext;
    ImageLoaderConfiguration config;
    DisplayImageOptions options;
    Intent intent;
    public CommentListAdapter(Context context, ArrayList<Comment> commentArrayList,EditText ETcomment) {
        intent = new Intent();
        UIContext = context;
        this.ETcomment = ETcomment;
        content = commentArrayList;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.rock_zone_circle_icon) // resource or drawable
                .showImageOnFail(R.drawable.rock_zone_circle_icon) // resource or drawable
                .showImageOnLoading(R.drawable.loading_rotate) // resource or drawable
                .build();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        RelativeLayout commentItem = (RelativeLayout) inflater.inflate(R.layout.article_comment_item, null);
        ImageView IVuserIcon = (ImageView) commentItem.findViewById(R.id.comment_user_icon);
        TextView TVcommentAuthor = (TextView) commentItem.findViewById(R.id.comment_author);
        TextView TVcommentContent  = (TextView) commentItem.findViewById(R.id.comment_content);
        TextView TVcreateTime  = (TextView) commentItem.findViewById(R.id.create_time);
        String time = TimeUtil.getTime(Integer.parseInt(content.get(position).getCreateTime()));
        TVcreateTime.setText(time);
        String author = content.get(position).getAuthorNickname();
        if(author==null||author.equals("null")||author.equals("")){
            TVcommentAuthor.setText("游客");
        }else {
            TVcommentAuthor.setText(content.get(position).getAuthorNickname());
        }
        TVcommentContent.setText(content.get(position).getContent());
        commentItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ETcomment.setText("回复 @" + content.get(position).getAuthorNickname() + " ：");
                ETcomment.setSelection(ETcomment.getText().length());
            }
        });
        ImageLoader.getInstance().displayImage(content.get(position).getAuthorIcon(),IVuserIcon,options,new HeadImageLoadingListener(),new MyImageLoadingProgressListener());
        return commentItem;
    }

    @Override
    public int getCount() {
        if(content!=null){
            return content.size();
        }
        else return 0;
    }

    @Override
    public Object getItem(int i) {
        return content.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void refresh(ArrayList<Comment> list) {
        for(Comment comment : list){
            content.add(comment);
        }
        notifyDataSetChanged();
    }
    public void reset() {
        if(content != null){
            content.clear();
            notifyDataSetChanged();
        }
    }

}