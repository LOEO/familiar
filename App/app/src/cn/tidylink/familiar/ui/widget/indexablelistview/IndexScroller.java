package cn.tidylink.familiar.ui.widget.indexablelistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ListView;

/**
 * Created by LT on 2015/10/17 0017.
 */
public class IndexScroller {

    private ListView lv;
    private Context context;

    public IndexScroller(Context context, ListView listView) {
        this.context = context;
        this.lv = listView;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        Rect rect = new Rect();
    }
}
