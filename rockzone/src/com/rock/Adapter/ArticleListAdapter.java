package com.rock.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rock.Activity.R;
import com.rock.Helper.UIHelper;
import com.rock.Listener.HeadImageLoadingListener;
import com.rock.Listener.MyArticleListItemClickListener;
import com.rock.Listener.MyImageLoadingProgressListener;
import com.rock.common.StringUtils;
import com.rock.common.TimeUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

;

/**
 * Created by wym on 2014/11/3.
 */
public class ArticleListAdapter extends BaseAdapter {
    private ArrayList<JSONObject> content;
    private LayoutInflater inflater;
    private Context UIContext;
    private DisplayImageOptions options;
    private String url;
    private int layoutId;
    public  ArticleListAdapter(Context context,int layoutId, ArrayList<JSONObject> objects) {
        this.layoutId = layoutId;
        UIContext = context;
        content = objects;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_rotate_1_1) // resource or drawable
                .showImageForEmptyUri(R.drawable.empty_photo) // resource or drawable
                .showImageOnFail(R.drawable.empty_photo) // resource or drawable
                .build();

    }

    @Override
    public View getView(final int position, View articleItem, ViewGroup parent) {
        // ViewHolder holder;
        //todo 布局优化参考 http://blog.csdn.net/sweetvvck/article/details/12753851
        if (articleItem == null &&layoutId!=R.layout.xiaoqu_forum_item) {
            articleItem = inflater.inflate(layoutId, null);
        }
        JSONObject item = content.get(position);
        try {
            switch (layoutId){
                case R.layout.xiaoqu_forum_item:
//                    if (articleItem == null ) {
                        url =  item.getString("cover_id");
                        if(StringUtils.isObjEmpty(url)){
                            articleItem = inflater.inflate(R.layout.xiaoqu_forum_nopic_item, null);
                        }else {
                            articleItem = inflater.inflate(R.layout.xiaoqu_forum_item, null);
                            ImageView imageView = (ImageView)articleItem.findViewById(R.id.post_image);
                            ImageLoader.getInstance().displayImage(url,imageView,options,new HeadImageLoadingListener(),new MyImageLoadingProgressListener());
                        }
//                    }
                    TextView articleTitle = (TextView) articleItem.findViewById(R.id.title);
                    articleTitle.setText( item.getString("title")   );
                    TextView articleDescribe = (TextView) articleItem.findViewById(R.id.describe);
                    TextView createTime = (TextView)articleItem.findViewById(R.id.create_time);
                    TextView author = (TextView)articleItem.findViewById(R.id.author);
                    TextView commentNum = (TextView)articleItem.findViewById(R.id.comment_num);
                    String time = TimeUtil.getTime(Integer.parseInt(item.getString("create_time")));
                    createTime.setText(time);
                    commentNum.setText(item.getString("comment"));
                    author.setText(item.getString("author"));
                    articleDescribe.setText(item.getString("description"));

                    articleItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                String id= content.get(position).getString("id");
                                UIHelper.showPostDetailView(UIContext, id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case R.layout.xiaoqu_phonenum_item:
                    initPhonenumView(articleItem, item);break;
                case R.layout.article_youhui_item:layoutId:
                    initYouhuiView(articleItem, item);;break;
                case R.layout.xiaoqu_publish_item:
                    initPublishView(articleItem, item);break;
                case R.layout.xiaoqu_wuye_item:
                    initWuyeView(articleItem, item);break;
                case R.layout.community_article_item:
                    initArticleItemView(articleItem, item);break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        ImageButton logo = (ImageButton) articleItem.findViewById(R.id.community_article_logo);
//        try {
//            url =  item.getString("cover_id");
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//        ImageLoader.getInstance().displayImage(url, logo, options, new MyImageLoadingListener(), new MyImageLoadingProgressListener());

        return articleItem;
    }

    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public Object getItem(int i) {
        return content.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void refresh(ArrayList<JSONObject> list) {
        content = list;
        notifyDataSetChanged();
    }
    public void initView(View articleItem,JSONObject item) throws JSONException {
        TextView articleTitle = (TextView) articleItem.findViewById(R.id.title);
        articleTitle.setText( item.getString("title")   );
    }
    public void initPhonenumView(View articleItem, final JSONObject item) throws JSONException {
        TextView articleTitle = (TextView) articleItem.findViewById(R.id.title);
        TextView callNum = (TextView) articleItem.findViewById(R.id.xiaoqu_phone_num);
        callNum.setText(UIContext.getString(R.string.wuye_phone_num, item.getString("view")));
        articleTitle.setText(item.getString("title"));
        articleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    UIHelper.showPhoneDetailView(UIContext, item.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void initPublishView(View articleItem,JSONObject item) throws JSONException {
        TextView articleTitle = (TextView) articleItem.findViewById(R.id.title);
        articleTitle.setText( item.getString("title")   );
        TextView articleDescribe = (TextView) articleItem.findViewById(R.id.describe);
        articleDescribe.setText(item.getString("description"));
    }
    public void initForumView(final int position, View articleItem, ViewGroup parent, JSONObject item) throws JSONException {

        }
    public void initYouhuiView(View articleItem, final JSONObject item) throws JSONException {
        TextView articleDescribe = (TextView) articleItem.findViewById(R.id.describe);
        ImageView imageView = (ImageView)articleItem.findViewById(R.id.post_image);
        TextView createTime = (TextView)articleItem.findViewById(R.id.create_time);
        TextView articleTitle = (TextView) articleItem.findViewById(R.id.title);
        articleTitle.setText( item.getString("title")   );
        url =  item.getString("cover_id");
        String time = TimeUtil.getTime(Integer.parseInt(item.getString("create_time")));
        createTime.setText(time);
        articleDescribe.setText(item.getString("description"));
        ImageLoader.getInstance().displayImage(url,imageView,options,new HeadImageLoadingListener(),new MyImageLoadingProgressListener());
        articleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    UIHelper.showArticleDetailView(UIContext, item.getString("id"),"折扣优惠");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void initWuyeView(View articleItem, final JSONObject item) throws JSONException {
        TextView title = (TextView) articleItem.findViewById(R.id.title);
        TextView articleDescribe = (TextView) articleItem.findViewById(R.id.describe);
        articleDescribe.setText( item.getString("description"));
        title.setText( item.getString("title"));
        articleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    UIHelper.showPhoneDetailView(UIContext, item.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void initArticleItemView(View articleItem, final JSONObject item) throws JSONException {
        ImageView logo = (ImageView) articleItem.findViewById(R.id.logo);
        TextView title = (TextView) articleItem.findViewById(R.id.title);
        TextView articleDescribe = (TextView) articleItem.findViewById(R.id.describe);
        articleDescribe.setText( item.getString("description"));
        title.setText( item.getString("title"));
        url =  item.getString("cover_id");
        ImageLoader.getInstance().displayImage(url,logo);
        String id= item.getString("id");
        articleItem.setOnClickListener(new MyArticleListItemClickListener(UIContext,id));
    }
}