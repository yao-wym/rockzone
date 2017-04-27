package com.rock.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.rock.Helper.ApiClient;
import com.rock.Helper.MyImageLoader;
import com.rock.Listener.CommunityLogoBgListener;
;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by wym on 2014/11/5.
 */
public class CommunityDetailActivity extends Activity {
    ImageButton logo_background;
    ImageButton logo;
    ImageLoaderConfiguration config;
    DisplayImageOptions options;
    public Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_foot);
        context = this;
        File cacheDir = StorageUtils.getCacheDirectory(this);
        config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//设置磁盘缓存文件名称
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_0) // resource or drawable
                .showImageForEmptyUri(R.drawable.empty) // resource or drawable
                .showImageOnFail(R.drawable.loading_0) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(1000)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .build();
        //Initialize ImageLoader with configuration
        ImageLoader.getInstance().init(config);
        String id = getIntent().getStringExtra("id");
        Handler handler = getHandler();
        ApiClient.getCommunityDetail(context,handler,id);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    protected Handler getHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==-1){
                    return;
                }
                final JSONObject content = (JSONObject) msg.obj;
                if (content!=null){
                    logo_background = (ImageButton) findViewById(R.id.community_logo_background);
                    logo = (ImageButton)findViewById(R.id.community_logo);
                    try {
                        MyImageLoader.getInstance().displayImage(content.getString("background"), logo_background, options, new CommunityLogoBgListener(), new ImageLoadingProgressListener() {
                            @Override
                            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                            }
                        });
                        MyImageLoader.getInstance().displayImage(content.getString("logo"), logo, options, new CommunityLogoBgListener(), new ImageLoadingProgressListener() {
                            @Override
                            public void onProgressUpdate(String imageUri, View view, int current, int total) {

                            }
                        });
                        RelativeLayout xiaoqu_publish = (RelativeLayout)findViewById(R.id.xiaoqu_publish);
                        RelativeLayout xiaoqu_qinzi = (RelativeLayout)findViewById(R.id.xiaoqu_qinzi);
                        RelativeLayout xiaoqu_weixiu = (RelativeLayout)findViewById(R.id.xiaoqu_weixiu);
                        RelativeLayout xiaoqu_phoneNum = (RelativeLayout)findViewById(R.id.xiaoqu_phoneNum);
                        LinearLayout functionsLayout = (LinearLayout)findViewById(R.id.xiapqu_functions);
                        JSONArray functions = content.getJSONArray("cate_list");
                        for(int i=0;i<functions.length();i++){
                            JSONObject item = (JSONObject)functions.get(i);
                            RelativeLayout funcction = (RelativeLayout) functionsLayout.getChildAt(i);
                            TextView title = (TextView)funcction.getChildAt(0);
                            ImageButton button = (ImageButton)funcction.getChildAt(1);
                            title.setText(item.getString("title"));
                            button.setId(Integer.parseInt(item.getString("id")));
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int id = view.getId();
//                                    UIHelper.showCommunityArticleView(context,id);
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

        };
    }
}
