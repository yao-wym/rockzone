package com.rock.common;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.baidu.mapapi.cloud.*;

/**
 * Created by wym on 2014/11/22.
 */
public class BaiduMapUtil implements CloudListener{
    private static String ak = "dcF4BUUbO6rgt3WFSwHMuTo5";
    private static String sn = "cDStnAGEdbnRM5BeSiQkMOG1MtwW3moL";
    private static int geoTableId = 86816;
    private static BaiduMapUtil mapUtil;
    private Handler handler;
    private Context context;
    private BaiduMapUtil(){
        CloudManager.getInstance().init(BaiduMapUtil.this);
    }
    public static BaiduMapUtil getInstance(){
        if(mapUtil == null){
            mapUtil = new BaiduMapUtil();
        }
        return mapUtil;
    }
    public void setHandler(Handler handler){
        this.handler = handler;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public void getNearCommunity(String keyWord,int distance){
        NearbySearchInfo info = new NearbySearchInfo();
        info.ak = ak;
        info.geoTableId = geoTableId;
        info.q = keyWord;
        info.sn= sn;
        info.radius = distance;
        //todo 获取定位
        info.location = "116.35669,39.964261";
//        info.location = String.valueOf(data.lontitude)+","+String.valueOf(data.latitude);
        CloudManager.getInstance().nearbySearch(info);
    }

    @Override
    public void onGetSearchResult(CloudSearchResult cloudSearchResult, int i) {
        Message message = new Message();
        message.what = 0;
        if(cloudSearchResult == null){
            message.what = -2;
            message.obj=null;
        }
        else {
            if(cloudSearchResult.status != 0){

                message.what = 0;
                message.obj = cloudSearchResult.status;
            }else if(cloudSearchResult.size == 0){
                message.what = -1;
                message.obj=null;
            }
            else{
                message.obj = cloudSearchResult.poiList;
                message.what = 1;
            }
        }
        handler.sendMessage(message);
    }

    @Override
    public void onGetDetailSearchResult(DetailSearchResult detailSearchResult, int i) {

    }

}
