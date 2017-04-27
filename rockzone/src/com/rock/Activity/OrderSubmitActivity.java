package com.rock.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.loopj.android.http.RequestParams;
import com.rock.Helper.ApiClient;
import com.rock.Helper.MyImageLoader;
import com.rock.Helper.UIHelper;
import com.rock.model.Goods;

/**
 * Created by wym on 2014/11/16.
 */
public class OrderSubmitActivity extends BaseActivity {
    private String address;
    private String phone;
    private String name;
    private String zipcode;
    private String goodsId;

    private TextView TVgoodsName;
    private TextView TVgoodsCost;
    private TextView TVMoneyNeed;
    private ImageView IVgoodsIcon;

    private EditText ETadrress;
    private EditText ETphone;
    private EditText ETname;
    private EditText ETzipcode;
    private Button   completeBtn;
    private Handler orderHandler;
    private Goods goods;
    private ArrayAdapter spinnerAdapter;
    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_order_submit);
        goods = (Goods)getIntent().getSerializableExtra("goods");
        initViews();
    }

    private void initViews(){
        initialHeader("订单提交");
        findViewById(R.id.goods_name);
        TVgoodsCost = (TextView) findViewById(R.id.goods_cost);
        TVgoodsName = (TextView) findViewById(R.id.goods_name);
        TVMoneyNeed = (TextView) findViewById(R.id.tox_money_need);
        IVgoodsIcon = (ImageView)findViewById(R.id.goods_icon);
        TVMoneyNeed.setText(goods.getgoodsCost());
        TVgoodsName.setText(goods.getgoodsName());
        TVgoodsCost.setText(goods.getgoodsCost());
        MyImageLoader.getInstance().displayImage(goods.getgoodsIconUrl(),IVgoodsIcon);
        ETadrress = (EditText)findViewById(R.id.address);
        ETname = (EditText)findViewById(R.id.name);
        ETphone = (EditText) findViewById(R.id.phone);
        ETzipcode = (EditText) findViewById(R.id.zip_code);
        completeBtn = (Button)findViewById(R.id.order_submit);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.showProgressDialog(context);
                address = ETadrress.getText().toString();
                name = ETname.getText().toString();
                zipcode = ETzipcode.getText().toString();
                phone = ETphone.getText().toString();
                RequestParams params = new RequestParams();
                params.put("uid", uid);
                params.put("address",address);
                params.put("zipcode",zipcode);
                params.put("phone",phone);
                params.put("address_id","");
                params.put("id",goods.getgoodsId());
                orderHandler = getOrderHandler();
                ApiClient.orderSubmit(context, orderHandler, params);


            }
        });
    }
    private Handler getOrderHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    UIHelper.dismissProgressDialog();
                    Toast.makeText(context, "兑换成功", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {UIHelper.dismissProgressDialog();
                    Toast.makeText(context, (String) msg.obj, Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    public void initialHeader(String title){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText(title);
    }
}
