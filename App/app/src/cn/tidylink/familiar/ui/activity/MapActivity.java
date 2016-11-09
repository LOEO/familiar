package cn.tidylink.familiar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import cn.tidylink.familiar.App;
import cn.tidylink.familiar.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by LT on 2015/10/8 0008.
 */
public class MapActivity extends BaseActivity {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private ImageButton getLocationBtn;
    private ImageButton zoomInBtn;
    private ImageButton zoomOutBtn;
    private ImageButton goBtn;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListener myListener = new MyLocationListener();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;

    // UI相关
    RadioGroup.OnCheckedChangeListener radioButtonListener;
    Button requestLocButton;
    boolean isFirstLoc = true;// 是否首次定位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(this.getApplication());
        setContentView(R.layout.activity_map);
        initBMap();
    }

    private void initBMap(){
        Intent intent = getIntent();

        mMapView = (MapView) findViewById(R.id.bmapView);
        //隐藏百度 logo
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(false);
       /* if (intent.hasExtra("x") && intent.hasExtra("y")) {
            // 当用intent参数时，设置中心点为指定点
            Bundle b = intent.getExtras();
            LatLng p = new LatLng(b.getDouble("y"), b.getDouble("x"));

            mMapView = new MapView(this,
                    new BaiduMapOptions().mapStatus(new MapStatus.Builder()
                            .target(p).build()));
        } else {
            BaiduMapOptions baiduMapOptions = new BaiduMapOptions();
            baiduMapOptions.scaleControlEnabled(false);
            mMapView = new MapView(this, baiduMapOptions);

        }*/
        mBaiduMap = mMapView.getMap();

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this.getApplication());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        //option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        getLocationBtn = (ImageButton) findViewById(R.id.get_location);
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocClient.start();
            }
        });
        zoomInBtn = (ImageButton) findViewById(R.id.zoom_in_btn);
        zoomInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(1);
            }
        });
        zoomOutBtn = (ImageButton) findViewById(R.id.zoom_out_btn);
        zoomOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(-1);
            }
        });
        //getSupportActionBar().hide();

        goBtn = (ImageButton) findViewById(R.id.go);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void zoom (float v){
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(mBaiduMap.getMapStatus().target,mBaiduMap.getMapStatus().zoom + v);
        mBaiduMap.animateMapStatus(mapStatusUpdate);    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            mLocClient.stop();
            Toast.makeText(MapActivity.this, "ssss123123sss", Toast.LENGTH_SHORT).show();
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationEnabled(true);
            mBaiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);
            /*if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }*/
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
        mMapView = null;
    }

}
