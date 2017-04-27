package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.*;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.rock.Adapter.ExploreGridAdapter;
import com.rock.Helper.ApiClient;
import com.rock.Helper.MyImageLoader;
import com.rock.Helper.UIHelper;
import com.rock.Helper.URL;
import com.rock.Listener.ExploreListClickListener;
import com.rock.Listener.MyImageLoadingListener;
import com.rock.Listener.MyImageLoadingProgressListener;
import com.rock.common.AdvPageUtils;
import com.rock.common.JsonUtil;
import com.rock.model.Article;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wym on 2014/11/5.
 */
public class ExploreActivity extends BaseActivity{
    private int page=1;
    private int num=3;
    private Handler advHandler;
    private Handler barHandler;
    private Handler listHandler;
    private ViewPager advViewpager;
    private RelativeLayout advItem;
    private ImageView imageView;
    private TextView title;
    private TextView viewNum;
    private GridView barGrideView;
    private PullToRefreshListView mPullRefreshListView;
    private ScrollView mScrollView;
    private JSONObject item;
    private String url;
    private String id;
    private JSONArray advJSONArrayData;
    private JSONArray barJSONArrayData;
    private ExploreGridAdapter MyGridAdapter;
    private LayoutInflater inflater;
    private LinearLayout advList;
    private LinearLayout bottom;
    private ArrayList<Article> articleArrayList;
    private Article article;
    private DisplayImageOptions options;
    private PullToRefreshScrollView mPullRefreshScrollView;
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore);
//        if(!ExploreActivity.this.isFinishing()&&!ExploreActivity.this.getParent().isFinishing()){
            UIHelper.showProgressDialog(ExploreActivity.this.getParent());
//        }
        inflater = LayoutInflater.from(this);
        //默认图片
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_rotate_2_1) // resource or drawable
                .showImageForEmptyUri(R.drawable.empty_photo) // resource or drawable
                .showImageOnFail(R.drawable.empty_photo) // resource or drawable
                .cacheOnDisk(true)
                .build();
        advList = (LinearLayout) findViewById(R.id.explore_list_fresh);
        mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//向上拉加载
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initListView();
            }
        });
        mScrollView = mPullRefreshScrollView.getRefreshableView();
        initialHeader();
        initViews();
        try {
            initAdvView();
            initBarView();
            initListView();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void initialHeader(){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("发现");
        headLeft.setOnClickListener(null);
        headLeft.setImageResource(0);
        headLeft.setBackgroundResource(0);
    }
    public void initAdvView() throws JSONException {
        AdvPageUtils advPageUtils = AdvPageUtils.getInstance();;
        advPageUtils.initialize(context,URL.EXPLORE_ADV_LIST,advViewpager,bottom);
//        advItem = (RelativeLayout) inflater.inflate(R.layout.adv_pager_item,null);
//        advHandler = getAdvHandler();
//        ApiClient.getExploreAdvList(advHandler);
    }
    public void initBarView(){


        barHandler = getBarHandler();
        try {
            ApiClient.getExploreBarList(context,barHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void initListView(){
        listHandler = getListHandler();
        try {
            advList = (LinearLayout) findViewById(R.id.explore_list_fresh);
            ApiClient.getExploreList(context,listHandler,page,num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Handler getBarHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                UIHelper.dismissProgressDialog();
                if(msg.what == 1){
                    barJSONArrayData = (JSONArray) msg.obj;
                    MyGridAdapter = new ExploreGridAdapter(context,R.layout.explore_grid_item,barJSONArrayData);
                    barGrideView.setBackgroundColor(getResources().getColor(R.color.graywhite));
                    barGrideView.setAdapter(MyGridAdapter);
                }
            }
        };
    }
    public Handler getListHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
//                UIHelper.dismissProgressDialog();
                super.handleMessage(msg);
                JSONArray jsonArray = (JSONArray) msg.obj;
                try {
                    articleArrayList = JsonUtil.jsonArrToArticleList(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initAdvListViews(articleArrayList);
                mPullRefreshScrollView.onRefreshComplete();
//                mPullRefreshListView.setAdapter(new ExploreListAdapter(context,R.layout.explore_item,jsonArray));
            }
        };
    }
    public void initViews(){
        bottom = (LinearLayout) findViewById(R.id.bottom_pointer);
        advViewpager = (ViewPager) findViewById(R.id.adv_pager);
        barGrideView = (GridView) findViewById(R.id.barGridView);
    }
    public void initAdvListViews(ArrayList<Article> articleArrayList){

        for(Article article : articleArrayList){
            RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.explore_item,null);
            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
            TextView viewNum = (TextView) view.findViewById(R.id.view_num);
            title.setText(article.getTitle());
            viewNum.setText(article.getViewNum());
            id = article.getArticleId();
            url = article.getCoverUrl();
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            MyImageLoader.getInstance().displayImage(url, imageView, options, new MyImageLoadingListener(), new MyImageLoadingProgressListener());
            view.setOnClickListener(new ExploreListClickListener(context,id));
            advList.addView(view);
        }
        page++;
    }
}
