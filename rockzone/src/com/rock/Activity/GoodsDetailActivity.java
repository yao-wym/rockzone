package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;
import com.loopj.android.http.RequestParams;
import com.rock.Helper.ApiClient;
import com.rock.Helper.MyImageLoader;
import com.rock.Helper.UIHelper;
import com.rock.Listener.MyImageLoadingListener;
import com.rock.Listener.MyImageLoadingProgressListener;
import com.rock.common.JsonUtil;
import com.rock.common.StringUtils;
import com.rock.model.Goods;
;
import org.json.JSONObject;

/**
 * Created by wym on 2014/11/6.
 */
public class GoodsDetailActivity extends BaseActivity {
    private String htmlContent;
    private String goodsDescribe;
    private Handler commentHandler;
    private LayoutInflater inflater;
    private String goodsId;
    private WebView articleContentWeb;
    private TextView TVdescribe;
    private TextView TVGoodsName;
    private TextView TVGoodsnum;
    private TextView TVSellNum;
    private TextView TVMoneyNeed;
    private Button BtBuyGoods;
    private RelativeLayout RLgoodsComment;
    private RelativeLayout RLgoodsDetail;
    private ImageButton IBgoodsCollect;
    private TextView TVgoodsCollect;
    private ImageView IVgoodsIcon;
    private Goods goods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_detail);
        goods = new Goods();
        context = this;
        initialHeader();
        inflater = LayoutInflater.from(context);
        goodsId = getIntent().getStringExtra("id");
        initialView(goodsId,uid);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    protected void initialView(String id,String uid){
        UIHelper.showProgressDialog(context);
        Handler handler = getHandler();
        ApiClient.getGoodsDetail(context,handler,id,uid);
    }
    protected Handler getHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                UIHelper.dismissProgressDialog();
                if(msg.what==-1||msg.obj == null||msg.obj instanceof JSONObject != true){
                    return;
                }
                JSONObject content = (JSONObject) msg.obj;
                goods = JsonUtil.jsonToGoods(content);
                if(goods.getIsCollect().equals("-1")){
                    IBgoodsCollect.setBackgroundResource(R.drawable.shangpin_collect);
                    TVgoodsCollect.setText("收藏");
                }else{
                    IBgoodsCollect.setBackgroundResource(R.drawable.shangpin_collect_ed);
                    TVgoodsCollect.setText("已收藏");
                }
//                TVdescribe.setText(goods.getgoodsInstruct());
                String goodsName = goods.getgoodsName();
                String cost = goods.getgoodsCost();
                String sellNum = goods.getSellNum();
                String remain = goods.getGoodsNum();
                TVMoneyNeed.setText(StringUtils.getStr(cost)+"积分");
                TVSellNum.setText("已售"+StringUtils.getStr(sellNum)+"套");
                TVGoodsnum.setText("剩余"+StringUtils.getStr(remain)+"套");
                TVGoodsName.setText(goodsName);
                View.OnClickListener listener = new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()){
                            case R.id.buy_submit:
                                Bundle mBundle = new Bundle();
                                mBundle.putSerializable("goods",goods);
                                UIHelper.showOrderSubmitView(context,mBundle);break;
                            case R.id.goods_comment:
                                UIHelper.showGoodsCommentView(context,goods.getgoodsId());break;
                            case R.id.detail_describe:
                                UIHelper.showGoodsDescribeView(context, goods.getgoodsDetail(), "商品详情");break;
                            case R.id.goods_collect:
                                if(isLogin!=true){
                                    Toast.makeText(context,"请先登陆",3).show();
                                    return;
                                }
                                RequestParams requestParams = new RequestParams();
                                requestParams.put("good_id",goods.getgoodsId());
                                requestParams.put("uid",uid);
                                if(goods.getIsCollect().equals("-1")){
                                    requestParams.put("add","1");
                                }else{
                                    requestParams.put("add","0");
                                }
                                ApiClient.collectGoods(context,getCollectHandler(),requestParams);
                        }
                    }
                };
                RLgoodsDetail.setOnClickListener(listener);
                BtBuyGoods.setOnClickListener(listener);
                IBgoodsCollect.setOnClickListener(listener);
                RLgoodsComment.setOnClickListener(listener);
                MyImageLoader.getInstance().displayImage(goods.getgoodsIconUrl(),IVgoodsIcon,options,new MyImageLoadingListener(),new MyImageLoadingProgressListener());
//                articleContentWeb.getSettings().setUseWideViewPort(true);
//                articleContentWeb.getSettings().setLoadWithOverviewMode(true);
//                articleContentWeb.loadData(htmlContent, "text/html; charset=UTF-8", null);

            }
        };
    }
    public Handler getCollectHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                    if(msg.what == 1){
                        Toast.makeText(context,(String)msg.obj,3).show();
                        if(goods.getIsCollect().equals("-1")){
                            IBgoodsCollect.setBackgroundResource(R.drawable.shangpin_collect_ed);
                            goods.setIsCollect("1");
                            TVgoodsCollect.setText("已收藏");
                        }else{
                            IBgoodsCollect.setBackgroundResource(R.drawable.shangpin_collect);
                            goods.setIsCollect("-1");
                            TVgoodsCollect.setText("收藏");
                        }
                    }
            }
        };
    }
    public void initialHeader(){
        super.initialHeader();
//        articleContentWeb = (WebView) findViewById(R.id.goods_detail);
//        TVdescribe = (TextView)findViewById(R.id.goods_describe);
        TVGoodsName = (TextView)findViewById(R.id.goods_name);
        TVGoodsnum = (TextView)findViewById(R.id.goods_remain);
        TVSellNum = (TextView)findViewById(R.id.sell_num);
        TVMoneyNeed = (TextView)findViewById(R.id.tox_money_need);
        BtBuyGoods = (Button)findViewById(R.id.buy_submit);
        RLgoodsComment = (RelativeLayout)findViewById(R.id.goods_comment);
        RLgoodsDetail = (RelativeLayout)findViewById(R.id.detail_describe);
        IBgoodsCollect = (ImageButton)findViewById(R.id.goods_collect);
        TVgoodsCollect = (TextView)findViewById(R.id.goods_collect_text);
        IVgoodsIcon = (ImageView) findViewById(R.id.goods_icon);
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("商品详情");
    }

}
