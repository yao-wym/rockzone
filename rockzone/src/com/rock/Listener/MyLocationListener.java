package com.rock.Listener;

import android.util.Log;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.rock.database.data;

/**
 * Created by wym on 2014/11/18.
 */
public class MyLocationListener implements BDLocationListener {

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation == null)
            return ;
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(bdLocation.getTime());
        sb.append("\nerror code : ");
        sb.append(bdLocation.getLocType());
        sb.append("\nlatitude : ");
        sb.append(bdLocation.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(bdLocation.getLongitude());
        sb.append("\nradius : ");
        sb.append(bdLocation.getRadius());
        if (bdLocation.getLocType() == BDLocation.TypeGpsLocation){
            sb.append("\nspeed : ");
            sb.append(bdLocation.getSpeed());
            sb.append("\nsatellite : ");
            sb.append(bdLocation.getSatelliteNumber());
        } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
            sb.append("\naddr : ");
            sb.append(bdLocation.getAddrStr());
        }
        data.latitude = bdLocation.getLatitude();
        data.lontitude = bdLocation.getLongitude();
        Log.i("baiduditu",sb.toString());
    }

}
