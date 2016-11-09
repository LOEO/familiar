package cn.tidylink.familiar.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import cn.tidylink.familiar.adapter.CommonAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by LT on 2015-05-25.
 */
public class ViewUtil {
    public static View getView(Context context,int id){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(id,null);
    }

    public static <T> void bindSpinnerData(Context context,int layout,
                                           Spinner spinner,List<T> dataList,
                                           final Class clz,
                                           final int [] ids,
                                           final String[] fieldNames) {
        CommonAdapter<T> adapter = new CommonAdapter<T>(context, layout, dataList) {
            @Override
            protected void handleView(int position, ViewHolder viewHolder) {
                    for(int i=0;i<ids.length;i++){
                        Field f= null;
                        try {
                            f = clz.getDeclaredField(fieldNames[i]);
                            f.setAccessible(true);
                            String s = f.get(dataList.get(position)).toString();
                            viewHolder.setText(ids[i], s);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
            }
        };
        spinner.setAdapter(adapter);
    }
}
