package cn.tidylink.familiar.util;

import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LT on 2015-05-26.
 */
public class EntityUtil {
    public static <T> T buildEntity(Class<T> cls,JSONObject jo){

        if(cls == null || jo == null){
            throw new IllegalArgumentException("参数错误");
        }
        T t = JSON.parseObject(jo.toString(),cls);
        return t;
    }

    public static <T> List<T> buildEntityList(Class<T> cls,JSONArray ja){
        if(cls == null || ja == null){
            throw new IllegalArgumentException("参数错误");
        }
        List<T> list = new ArrayList<>();
        for(int i=0;i<ja.length();i++){
            try {
                list.add(buildEntity(cls,(JSONObject)ja.get(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
