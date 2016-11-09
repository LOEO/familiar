package cn.tidylink.familiar.ui.widget.indexablelistview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by LT on 2015/10/17 0017.
 */
public class IndexableListView extends ListView {
    private IndexScroller indexScroller;
    public IndexableListView(Context context) {
        super(context);
    }

    public IndexableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (indexScroller != null) {
            indexScroller.draw(canvas);
        }
    }
}
