package com.rock.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.cloud.*;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

/**
 * Created by wym on 2014/11/5.
 */
public class ErweimaActivity extends BaseActivity implements CloudListener {
    UMSocialService mController;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
//todo 友盟与百度地图测试页面
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baidumap_test);
        SDKInitializer.initialize(getApplicationContext());
        CloudManager.getInstance().init(ErweimaActivity.this);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
//普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
// 设置分享内容
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
// 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(this,"http://www.umeng.com/images/pic/banner_module_social.png"));

        context = this;

        initialHeader();
    }
    public void initialHeader(){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("分享");
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.openShare((android.app.Activity) context, false);
                NearbySearchInfo info = new NearbySearchInfo();
                info.ak = "dcF4BUUbO6rgt3WFSwHMuTo5";
                info.geoTableId = 85589;
                info.q = "石头";
                info.sn= "cDStnAGEdbnRM5BeSiQkMOG1MtwW3moL";
                info.radius = 30000;
                info.location = "116.343799,39.92639";
                CloudManager.getInstance().nearbySearch(info);

            }
        });
        headLeft.setImageResource(0);
    }

    @Override
    public void onGetSearchResult(CloudSearchResult cloudSearchResult, int i) {

    }

    @Override
    public void onGetDetailSearchResult(DetailSearchResult detailSearchResult, int i) {

    }
}
