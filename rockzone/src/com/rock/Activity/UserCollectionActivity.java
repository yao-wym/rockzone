package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rock.Adapter.CollectionArticleAdapter;
import com.rock.Adapter.CollectionGoodsAdapter;
import com.rock.Adapter.MyCollectionPagerAdapter;
import com.rock.Helper.ApiClient;
import com.rock.Listener.CollectionPageScrollListener;
import com.rock.common.JsonUtil;
import com.rock.model.Article;
import com.rock.model.Goods;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by wym on 2014/11/25.
 */
public class UserCollectionActivity extends BaseActivity {
    private ViewPager collectionPager;
    private ArrayList<RelativeLayout> viewList = new ArrayList<RelativeLayout>();
    private PullToRefreshListView articleListview;
    private PullToRefreshListView goodsListview;
    private ArrayList<Goods> goodsList;
    private ArrayList<Article> articleList;
    private LayoutInflater inflater;
    private int goodsPageNum = 1;
    private int articlePageNum = 1;
    private CollectionArticleAdapter articleAdapter;
    private CollectionGoodsAdapter goodsAdapter;
    private RelativeLayout RLcollectGoods;
    private RelativeLayout RLcollectArticle;
    private MyCollectionPagerAdapter myAdapter;
    private ImageView IVlineGoods;
    private ImageView IVlineArticle;
    @Override
    public void initialHeader() {
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("我的收藏");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_layout);
        initialHeader();
        getCollectionInfo();
        inflater = LayoutInflater.from(this);
        RLcollectGoods = (RelativeLayout) findViewById(R.id.collect_goods);
        RLcollectArticle = (RelativeLayout) findViewById(R.id.collect_article);
        collectionPager = (ViewPager) findViewById(R.id.collection_pager);
        RelativeLayout articleList = (RelativeLayout) inflater.inflate(R.layout.collection_list,null);
        RelativeLayout goodsList = (RelativeLayout) inflater.inflate(R.layout.collection_list,null);

        articleListview = (PullToRefreshListView) articleList.findViewById(R.id.collection_list);
        goodsListview = (PullToRefreshListView) goodsList.findViewById(R.id.collection_list);

        IVlineArticle = (ImageView) findViewById(R.id.red_line_article);
        IVlineGoods = (ImageView) findViewById(R.id.red_line_goods);

        goodsAdapter = new CollectionGoodsAdapter(context,null);
        articleAdapter = new CollectionArticleAdapter(context,null);

        findViewById(R.id.red_line_goods);

        articleListview.setAdapter(articleAdapter);
        goodsListview.setAdapter(goodsAdapter);

        articleListview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//向上拉加载
        goodsListview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//向上拉加载

        articleListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                articleListview.onRefreshComplete();
            }
        });
        goodsListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                goodsListview.onRefreshComplete();
            }
        });


        viewList.add(articleList);
        viewList.add(goodsList);

        myAdapter = new MyCollectionPagerAdapter(context,viewList);
        collectionPager.setAdapter(myAdapter);
        collectionPager.setOnPageChangeListener(new CollectionPageScrollListener(context,collectionPager,IVlineArticle,IVlineGoods));

        RLcollectGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionPager.setCurrentItem(1);
            }
        });
        RLcollectArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionPager.setCurrentItem(0);
            }
        });
    }
    public void getCollectionInfo(){
        ApiClient.getGoodsCollection(context,getGoodsHandler(),uid,goodsPageNum);
        ApiClient.getArticleCollection(context,getArticleHandler(),uid,articlePageNum);
    }
    public Handler getGoodsHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                JSONArray response = (JSONArray)msg.obj;

                try {
                    goodsList = JsonUtil.jsonArrToGoodsList(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                goodsAdapter.refresh(goodsList);
            }
        };
    }
    public Handler getArticleHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                JSONArray response = (JSONArray)msg.obj;
                try {
                    articleList = JsonUtil.jsonArrToArticleList(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                articleAdapter.refresh(articleList);
            }
        };
    }
}
