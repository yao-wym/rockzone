package com.rock.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rock.common.FileUtils;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wym on 2014/11/19.
 */
public class HttpRequestUtil extends JsonHttpResponseHandler {
    private Context context;
    private String url = null;
    private JSONObject response = null;
    private Handler handler;
    private JSONArray jsonArray = null;
    private Message message;
    private int flag = 1;
    private String cache = null;
    public HttpRequestUtil(Context context){
        this.context = context;
    }
    public HttpRequestUtil(Context context,Handler handler,String url){
        message = new Message();
        this.context = context;
        this.url = url;
        this.handler = handler;
    }

    /**
     *
     * @param context
     * @param handler
     * @param url
     * @param flag 判断是否缓存记录
     */
    public HttpRequestUtil(Context context,Handler handler,String url,int flag){
        message = new Message();
        this.context = context;
        this.url = url;
        this.handler = handler;
        this.flag = flag;
    }
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        FileUtils.saveObject(context,response.toString(), url);
        try {
            checkResponse(response);
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        Toast.makeText(context, "网络错误", 3).show();
        if(this.flag == 1){
             cache = (String) FileUtils.readObject(context, url);
        }
        if(cache != null){
            try {
                response = new JSONObject(cache);
                checkResponse(response);
                if(message.what == 1){
                    handler.sendMessage(message);
                }else {
                    message.what = -1;
                    handler.sendMessage(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    public void checkResponse(JSONObject response) throws JSONException {
        if (response.getString("status").equals("ok"))
        {
            if(response.get("content") instanceof JSONArray == true){
                jsonArray = response.getJSONArray("content");
                message.what = 1;
                message.obj = jsonArray;
            }
            else if(response.get("content") instanceof JSONObject == true){
                JSONObject jsonObject = response.getJSONObject("content");
                message.what = 1;
                message.obj = jsonObject;
            }else if(response.get("content") instanceof String == true){
                String strObject = response.getString("content");
                message.what = 1;
                message.obj = strObject;
            }
            else {
//                Toast.makeText(context, "数据格式异常", 3).show();
            }
        }
        else {
            message.obj = response.optString("content");
            message.what = 0;
        }
    }

}
