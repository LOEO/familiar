package cn.tidylink.familiar.map.bdmap;

import cn.tidylink.familiar.map.api.model.LatLng;

/**
 * Created by LT on 2015/10/19 0019.
 */
public class BdLatLng extends LatLng {
    private com.baidu.mapapi.model.LatLng latLng;
    public BdLatLng(double lat,double lng) {
        super(lat,lng);
        this.latLng = new com.baidu.mapapi.model.LatLng(lat, lng);
    }
}
