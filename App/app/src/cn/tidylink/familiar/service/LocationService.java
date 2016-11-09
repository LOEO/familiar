package cn.tidylink.familiar.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import cn.tidylink.familiar.R;
import cn.tidylink.familiar.db.DataBaseHelper;
import com.baidu.location.*;

import java.util.List;

/**
 * Created by LT on 2015/10/26 0026.
 */
public class LocationService extends Service {
    private LocationClient locationClient;
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LocationService.class.getName(), "定位服务启动");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("亲密互联正在后台运行");
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        builder.setSmallIcon(R.drawable.ic_launcher);
        //NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        startForeground(1, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LocationService.class.getName(), "定位服务启动onStartCommand");
        Toast.makeText(this,"LocationService",Toast.LENGTH_LONG).show();
        DataBaseHelper dataBaseHelper = new DataBaseHelper("familiar");
        locationClient = new LocationClient(this.getApplicationContext());
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：公里每小时
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndirection : ");
                    sb.append(location.getDirection());
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");

                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    //运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                sb.append("\nlocationdescribe : ");// 位置语义化信息
                sb.append(location.getLocationDescribe());
                List<Poi> list = location.getPoiList();// POI信息
                if (list != null) {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list) {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
                Log.i("BaiduLocationApiDem", sb.toString());
            }
        });
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系，
        option.setScanSpan(10000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(false);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationClient.setLocOption(option);
        locationClient.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationClient.stop();
    }
}
