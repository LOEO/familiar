package cn.tidylink.familiar.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by LT on 2015-07-31.
 */
public class DimenUtil {
    private static DimenUtil dimenUtil = null;
    private DimenUtil(){}

    public static DimenUtil getInstance() {
        if (dimenUtil == null) {
            dimenUtil = new DimenUtil();
        }
        return dimenUtil;
    }

    public int dp2px(Context context,int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
