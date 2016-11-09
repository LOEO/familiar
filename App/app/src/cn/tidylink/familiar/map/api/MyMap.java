package cn.tidylink.familiar.map.api;

import cn.tidylink.familiar.map.api.model.LatLng;

/**
 * Created by LT on 2015/10/19 0019.
 */
public interface MyMap {
    void setLocation(LatLng latLng);
    void zoom(float v);
}
