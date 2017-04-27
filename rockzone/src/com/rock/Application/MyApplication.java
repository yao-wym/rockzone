package com.rock.Application;

import android.app.Application;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.rock.Listener.MyLocationListener;
import com.rock.model.User;
import com.umeng.socialize.controller.UMSocialService;

import java.io.File;

/**
 * Created by wym on 2014/11/13.
 */
public class MyApplication extends Application {
    private File cacheDir;
    private ImageLoaderConfiguration config;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener =  new MyLocationListener();
    private User user;
    private Boolean isLogin = false;
    private String  userId = "";
    private String  communityId = "";
    private UMSocialService mController;
    @Override
    public void onCreate() {
        super.onCreate();
        cacheDir = StorageUtils.getCacheDirectory(this);
        SDKInitializer.initialize(getApplicationContext());
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        initImageLoader();

    }
    public void initImageLoader(){

        config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//设置磁盘缓存文件名称
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }
    public User gerUserStatus(){
        return user;
    }
    public void setUserStatus(User user){
        this.user = user;
    }
    public void setLoginStatus(Boolean status){
        this.isLogin = status;
    }
    public Boolean getLoginStatus(){
        return this.isLogin;
    }
    public void setUserId(String userId ){
        this.userId = userId;
    }
    public String getUserId(){
        return userId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityId() {

        return communityId;
    }
}
