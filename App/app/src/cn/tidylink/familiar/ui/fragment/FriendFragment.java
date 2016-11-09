package cn.tidylink.familiar.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import cn.tidylink.familiar.R;
import cn.tidylink.familiar.ui.activity.MapActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LT on 2015/10/8 0008.
 */
public class FriendFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        listView = (ListView) view.findViewById(R.id.list_friend);
        initFriendList();
        return view;
    }

    private void initFriendList() {
        List<Map<String, Object>> friends = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("friend_name", "好友" + i);
            friends.add(map);
        }
        SimpleAdapter sa = new SimpleAdapter(this.getActivity(), friends, R.layout.friend_list_item, new String[]{"friend_name"}, new int[]{R.id.friend_name});
        listView.setAdapter(sa);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getActivity(), MapActivity.class);
        startActivity(intent);
    }
}
