package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.rock.Helper.ApiClient;
import com.rock.Helper.MyImageLoader;
import com.rock.Helper.UIHelper;
import com.rock.Helper.URL;
import com.rock.Listener.ExploreListClickListener;
import com.rock.Listener.MyImageLoadingListener;
import com.rock.Listener.MyImageLoadingProgressListener;
import com.rock.Listener.ServiceItemClickListener;
import com.rock.common.AdvPageUtils;
import com.rock.common.JsonUtil;
import com.rock.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    public static RelativeLayout advImageDefault;
	private JSONObject content;
	private LinearLayout bottom;
    private RelativeLayout head_middle;
    private  String id;
    private String url;
    private TextView community_name;
    public static ImageView pageImageView;
    private String title;
    private LinearLayout mainAdvList;
    private PullToRefreshScrollView mPullToRefreshScrollView;
    private ScrollView mScrollView;
    private Handler listHandler;
    private int page = 1;
    private int num = 3;
    private JSONObject item;
    private LayoutInflater inflater;
    private ArrayList<Article> articleArrayList;
    private ImageView imageView;
    private TextView viewNum;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.rock_main);
        UIHelper.showProgressDialog(MainActivity.this.getParent());
        initialHeader();
        inflater = LayoutInflater.from(this);
        community_name = (TextView)findViewById(R.id.community_name);
        mainAdvList = (LinearLayout) findViewById(R.id.main_adv_list);
        initGlobalList();
        mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        mPullToRefreshScrollView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ScrollView>() {
            @Override
            public void onPullEvent(PullToRefreshBase<ScrollView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("上拉加载更多");
            }
        });
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//向上拉加载
        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("加载更多");
                initListView();
            }
        });
        mScrollView = mPullToRefreshScrollView.getRefreshableView();
        advImageDefault = (RelativeLayout)findViewById(R.id.adv_page_list);
        try {
            initListView();
            initViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //默认为石头小区
        if(communityId == null||communityId.equals("")||communityId.equals("null")){
            communityId = "1";
        }
            Handler handler = getHandler();
            ApiClient.getCommunityDetail(context,handler,communityId);
        }


    public void initViews() throws Exception {
        initListener();
        initAdView();
    }
    public void initListener(){
        head_middle= (RelativeLayout)findViewById(R.id.head_middle);
        View.OnClickListener listener = new View.OnClickListener()
        {
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    case R.id.head_middle:
                        UIHelper.showCommunityListView(context);
                        break;

                }
            }
        };
        head_middle.setOnClickListener(listener);
    }
    /**
     * 初始化广告view
     * @throws org.json.JSONException
     * by wym
     */
    private void initAdView() throws JSONException {
        ViewPager advViewPager = (ViewPager) findViewById(R.id.adv_pager);
        bottom = (LinearLayout) findViewById(R.id.bottom_pointer);
        AdvPageUtils advPageUtils = AdvPageUtils.getInstance();;
        advPageUtils.initialize(context, URL.HOST_ADV_LIST,advViewPager,bottom);
    }

    protected Handler getHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                UIHelper.dismissProgressDialog();
                if(msg.what==-1){
                    return;
                }
                final JSONArray jsonArray = (JSONArray) msg.obj;
                try {
                    content = jsonArray.getJSONObject(0);
                    title = content.getString("title");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                community_name.setText(title);

                    try {
                        JSONArray functions = content.getJSONArray("cate_list");
                        for(int i=0;i<functions.length();i++){
                            JSONObject item = (JSONObject)functions.get(i);
                            String id = item.getString("id");
                            String title = item.getString("title");
                            if(title.equals(getResources().getText(R.string.main_publish))){
                                ImageButton function = (ImageButton) findViewById(R.id.xiaoqu_publish);
                                function.setOnClickListener(new ServiceItemClickListener(context,id,R.string.main_publish));
                            }
                            else if(title.equals(getResources().getText(R.string.main_forum))){
                                ImageButton function = (ImageButton) findViewById(R.id.xiaoqu_luntan);
                                function.setOnClickListener(new ServiceItemClickListener(context,id,R.string.main_forum));
                            }
                            else if(title.equals(getResources().getText(R.string.main_phone))){
                                ImageButton function = (ImageButton) findViewById(R.id.xiaoqu_phone);
                                function.setOnClickListener(new ServiceItemClickListener(context,id,R.string.main_phone));
                            }
//                            else if(title.equals(getResources().getText(R.string.main_shop))){
//                                RelativeLayout function = (RelativeLayout) findViewById(R.id.xiaoqu_bianli);
//                                function.setOnClickListener(new ServiceItemClickListener(context,id,R.string.main_shop));
//                            }
                            else if(title.equals(getResources().getText(R.string.main_wuye))){
                                ImageButton function = (ImageButton) findViewById(R.id.xiaoqu_wuye);
                                function.setOnClickListener(new ServiceItemClickListener(context,id,R.string.main_wuye));
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


        };
    }
    public void initialHeader() {
        super.initialHeader();
        ImageButton head = (ImageButton) headMiddle.findViewById(R.id.community_jiantou);
        head.setImageResource(R.drawable.shouye_jiantou);

        //TODO 分享地图测试接口在此
//        headLeft.setImageResource(R.drawable.shouye_saoyisao);
//        headLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                UIHelper.showErweimaView(context);
//            }
//        });
        headLeft.setOnClickListener(null);
        headLeft.setBackgroundResource(0);
        headLeft.setImageResource(0);
    }
    public void initListView(){
        listHandler = getListHandler();
        try {
            ApiClient.getMainList(context, listHandler, page, num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Handler getListHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                JSONArray jsonArray = (JSONArray) msg.obj;
                try {
                    articleArrayList = JsonUtil.jsonArrToArticleList(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initAdvListViews(articleArrayList);
                mPullToRefreshScrollView.onRefreshComplete();
//                mPullRefreshListView.setAdapter(new ExploreListAdapter(context,R.layout.explore_item,jsonArray));
            }
        };
    }
    public void initAdvListViews(ArrayList<Article> articleArrayList){
//        title = (TextView) view.findViewById(R.id.title);
//        describe = (TextView) view.findViewById(R.id.describe);
        options = new DisplayImageOptions.Builder()
//                //todo 默认图片
                .showImageOnLoading(R.drawable.loading_rotate_2_1) // resource or drawable
                .showImageForEmptyUri(R.drawable.empty_photo) // resource or drawable
                .showImageOnFail(R.drawable.empty_photo) // resource or drawable
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        for(Article article : articleArrayList){
            RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.main_list_item,null);
//            title.setText(article.getTitle());
//            describe.setText(article.getTitle());
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
//            TextView viewNum = (TextView) view.findViewById(R.id.view_num);
//            viewNum.setText(article.getViewNum());
            id = article.getArticleId();
            url = article.getCoverUrl();
            MyImageLoader.getInstance().displayImage(url, imageView, options, new MyImageLoadingListener(), new MyImageLoadingProgressListener());
            view.setOnClickListener(new ExploreListClickListener(context,id));
            mainAdvList.addView(view);
        }
        page++;
    }
    public void initGlobalList(){
        LinearLayout LLshop = (LinearLayout) findViewById(R.id.sale_shop);
        LinearLayout LLbianmin = (LinearLayout) findViewById(R.id.bianmin);
        LinearLayout LLcityForum = (LinearLayout) findViewById(R.id.city_forum);
        LinearLayout LLzhidao = (LinearLayout) findViewById(R.id.zhidao);
        LLshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.showCommunityArticleView(context,"58",R.string.main_youhui);
            }
        });
        LLbianmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.showBianminView(context);
            }
        });
        LLcityForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.showCommunityArticleView(context,"59",R.string.main_city);
            }
        });
        LLzhidao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.showCommunityArticleView(context,"57",R.string.main_banshi);
            }
        });
    }

}
