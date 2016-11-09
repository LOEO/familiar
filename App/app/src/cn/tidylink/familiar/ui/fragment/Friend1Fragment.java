package cn.tidylink.familiar.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.tidylink.familiar.R;

/**
 * Created by LT on 2015/10/8 0008.
 */
public class Friend1Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend1, container, false);
        return view;
    }
}
