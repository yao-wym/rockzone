package com.rock.common;

import android.app.Activity;
import android.content.Intent;
import com.rock.Activity.AdvDetailActivity;

/**
 * Created by wym on 2014/10/29.
 */
public class UIHelper {
    public static void showAdvDetail(Activity context,int advId){
        Intent intent = new Intent(context, AdvDetailActivity.class);
        intent.putExtra("advId",advId);
        context.startActivityForResult(intent, 1);
    }


}
