package cn.tidylink.familiar.util;

import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by LT on 2015-07-03.
 */
public class ViewHolder {
    private View convertView;
    private SparseArray<View> views;
    private boolean visible;

    private ViewHolder(View convertView) {
        this.convertView = convertView;
        views = new SparseArray<>();
    }

    public static ViewHolder get(View convertView) {
        return new ViewHolder(convertView);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public View getView(int id){
        View view = views.get(id);
        if(view == null){
            view = convertView.findViewById(id);
            views.put(id,view);
        }
        return view;
    }

    public ViewHolder setText(int id,String text){
        TextView tv = (TextView) getView(id);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImage(int id,int resId) {
        ImageView imageView = (ImageView) getView(id);
        imageView.setImageResource(resId);
        return this;
    }

    public ViewHolder setChecked(int id,boolean checked) {
        CheckBox checkBox = (CheckBox) getView(id);
        checkBox.setChecked(checked);
        return this;
    }
}
