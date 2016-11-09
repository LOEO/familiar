package cn.tidylink.familiar.map.bdmap;

import cn.tidylink.familiar.map.api.MyMap;
import cn.tidylink.familiar.map.api.model.LatLng;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;

/**
 * Created by LT on 2015/10/19 0019.
 */
public class BdMap implements MyMap {
    private BaiduMap baiduMap;

    @Override
    public void setLocation(LatLng latLng) {
        com.baidu.mapapi.model.LatLng latLng1 = new com.baidu.mapapi.model.LatLng(latLng.getLat(), latLng.getLng());
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng1);
        baiduMap.animateMapStatus(mapStatusUpdate);
    }

    @Override
    public void zoom(float v) {
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(baiduMap.getMapStatus().target, baiduMap.getMapStatus().zoom + v);
        baiduMap.animateMapStatus(mapStatusUpdate);
    }
}
