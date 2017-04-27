package com.rock.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rock.Helper.UIHelper;

public class RockBianminActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rock_bianmin);
        initialHeader();
        initViews();
    }

    public void initViews(){
        LinearLayout LLweixiu = (LinearLayout) findViewById(R.id.bianmin_weixiu);
        LinearLayout LLkaisuo = (LinearLayout) findViewById(R.id.bianmin_kaisuo);
        LinearLayout LLjiazheng = (LinearLayout) findViewById(R.id.bianmin_jiazheng);
        LinearLayout LLshutong = (LinearLayout) findViewById(R.id.bianmin_shutong);
        LinearLayout LLbanjia = (LinearLayout) findViewById(R.id.bianmin_banjia);
        LinearLayout LLcaipiao = (LinearLayout) findViewById(R.id.bianmin_caipiao);
        LinearLayout LLkuaidi = (LinearLayout) findViewById(R.id.bianmin_kuaidi);
        LinearLayout LLyiliao = (LinearLayout) findViewById(R.id.bianmin_yiliao);
        LinearLayout LLgengduo = (LinearLayout) findViewById(R.id.bianmin_gengduo);
        LinearLayout LLweizhang = (LinearLayout) findViewById(R.id.bianmin_weizhang);
        LinearLayout LLjiage = (LinearLayout) findViewById(R.id.bianmin_jiage);
        LinearLayout LLbanzheng = (LinearLayout) findViewById(R.id.bianmin_banzheng);
        LinearLayout LLdianying = (LinearLayout) findViewById(R.id.bianmin_dianying);
        LinearLayout LLjipiao = (LinearLayout) findViewById(R.id.bianmin_jipiao);
        LinearLayout LLshiti = (LinearLayout) findViewById(R.id.bianmin_shiti);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.showWebView(context, "http://m.xj.58.com/kaisuo","开锁维修");
                switch (view.getId()){
                    case R.id.bianmin_weixiu:break;
                    case R.id.bianmin_kaisuo:break;
                    case R.id.bianmin_jiazheng:break;
                    case R.id.bianmin_shutong:break;
                    case R.id.bianmin_banjia:break;
                    case R.id.bianmin_caipiao:break;
                    case R.id.bianmin_yiliao:break;
                    case R.id.bianmin_gengduo:break;
                    case R.id.bianmin_weizhang:break;
                    case R.id.bianmin_jiage:break;
                    case R.id.bianmin_banzheng:break;
                    case R.id.bianmin_jipiao:break;
                    case R.id.bianmin_shiti:break;
                    case R.id.bianmin_kuaidi:break;
                    case R.id.bianmin_dianying:break;
                    default:
//                        UIHelper.showWebView(context, "http://xj.58.com/kaisuo/?refrom=m58");
                }
            }
        };
        LLweixiu.setOnClickListener(listener);
        LLkaisuo.setOnClickListener(listener);
        LLshiti.setOnClickListener(listener);
        LLjipiao.setOnClickListener(listener);
        LLdianying.setOnClickListener(listener);
        LLyiliao.setOnClickListener(listener);
        LLbanzheng.setOnClickListener(listener);
        LLweizhang.setOnClickListener(listener);
        LLjiage.setOnClickListener(listener);
        LLcaipiao.setOnClickListener(listener);
        LLjiazheng.setOnClickListener(listener);
        LLkuaidi.setOnClickListener(listener);
        LLshutong.setOnClickListener(listener);
        LLbanjia.setOnClickListener(listener);
        LLgengduo.setOnClickListener(listener);

    }

    public void initialHeader(){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("便民服务");
        headRight.setImageResource(0);
    }

}
