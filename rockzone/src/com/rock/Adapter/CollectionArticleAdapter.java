package com.rock.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.rock.Activity.R;
import com.rock.Helper.MyImageLoader;
import com.rock.Listener.ArticleItemClickListener;
import com.rock.model.Article;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wym on 2014/11/3.
 */
public class CollectionArticleAdapter extends BaseAdapter {
    private ArrayList<Article> articleArrayList;
    private int layoutId;
    JSONArray content;
    LayoutInflater inflater;
    Context context;
    DisplayImageOptions options;
    Intent intent;
    public CollectionArticleAdapter(Context context, ArrayList<Article> articleArrayList) {
        intent = new Intent();
        this.context = context;
        this.articleArrayList = articleArrayList;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_rotate) // resource or drawable
                .showImageForEmptyUri(R.drawable.rock_zone_default_icon) // resource or drawable
                .showImageOnFail(R.drawable.empty_photo) // resource or drawable
                .build();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View articleItemView = inflater.inflate(R.layout.collection_article_item, null);
        Article articleItem = articleArrayList.get(position);
        TextView TVtitle = (TextView) articleItemView.findViewById(R.id.title);
        TextView TVauthor = (TextView) articleItemView.findViewById(R.id.author);
        ImageView IVicon = (ImageView)articleItemView.findViewById(R.id.article_icon);
        TVtitle.setText(articleItem.getTitle());
        TVauthor.setText(articleItem.getAuthor());
        MyImageLoader.getInstance().displayImage(articleItem.getCoverUrl(), IVicon, options, 100,50);
        articleItemView.setOnClickListener(new ArticleItemClickListener(context,articleItem));
        return articleItemView;
    }

    @Override
    public int getCount() {
        if(articleArrayList == null){
            return 0;
        }
        return articleArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        JSONObject item=null;
        try {
            item = content.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void refresh(ArrayList<Article> list) {
        articleArrayList = list;
        notifyDataSetChanged();
    }
}