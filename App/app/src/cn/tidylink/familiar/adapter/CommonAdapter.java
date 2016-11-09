package cn.tidylink.familiar.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import cn.tidylink.familiar.util.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LT on 2015-07-03.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private Filter filter = new CommFilter();
    private List<T> dataListCopy;
    protected List<T> dataList;
    protected LayoutInflater inflater;
    protected int layoutId;
    protected Context context;
    public CommonAdapter(Context context, int layoutId, List<T> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList == null ? null : dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        return filter;
    }

    public FilterResults doFilter(CharSequence constraint,List<T> dataListCopy) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(layoutId,null);
            viewHolder = ViewHolder.get(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        handleView(position,viewHolder);
        return convertView;
    }


    protected abstract void handleView(int position,ViewHolder viewHolder);

    private class CommFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(dataListCopy == null)
                dataListCopy = new ArrayList<>(dataList);
            FilterResults results = new FilterResults();
            if (TextUtils.isEmpty(constraint)) {
                results.values = dataListCopy;
                results.count = dataListCopy.size();
            }else{
                CommonAdapter.FilterResults filterResults = doFilter(constraint,dataListCopy);
                if (filterResults != null) {
                    results.count = filterResults.count;
                    results.values = filterResults.values;
                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            if (results.count == 0) {
                notifyDataSetInvalidated();
            } else {
                dataList.addAll((List<T>) results.values);
                notifyDataSetChanged();
            }
        }
    }

    public class FilterResults{
        public int count;
        public List<T> values;
    }
}
