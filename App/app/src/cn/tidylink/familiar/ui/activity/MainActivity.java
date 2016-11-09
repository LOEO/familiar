package cn.tidylink.familiar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;
import cn.tidylink.familiar.R;
import cn.tidylink.familiar.service.LocationService;
import cn.tidylink.familiar.ui.fragment.Friend1Fragment;
import cn.tidylink.familiar.ui.fragment.FriendFragment;
import cn.tidylink.familiar.ui.fragment.MeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup radioGroup;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //设置actionbar 浮动在最上方
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.radioMenu);
        radioGroup.setOnCheckedChangeListener(this);
        //initActionBar();
        initToolbar();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main_content,new FriendFragment(), R.id.menu_friend+"");
        ft.commit();
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //toolbar.setLogo(R.drawable.logo);
        toolbar.setTitle("");
       /*  toolbar.setSubtitle("子标题");*/
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_track_main_golist);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String msg = "";
                switch (menuItem.getItemId()) {
                    case R.id.action_add:
                        msg = "添加";
                        break;
                    case R.id.action_delete:
                        msg = "删除";
                        break;
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        //menuBuilder.
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //显示logo
        actionBar.setDisplayUseLogoEnabled(true);
        //设置logo可点击
        actionBar.setHomeButtonEnabled(true);
        //设置tab
/*        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for(int i=0;i<3;i++){
            ActionBar.Tab tab = actionBar.newTab().setText("tab"+(i+1)).setIcon(R.drawable.ic_launcher).setTabListener(new ActionBar.TabListener() {
                @Override
                public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                    Toast.makeText(MainActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

                }

                @Override
                public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

                }
            });
            actionBar.addTab(tab);
        }*/


        //使用下拉列表
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("item1");
        arrayList.add("item2");
        arrayList.add("item3");
        arrayList.add("item4");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        actionBar.setListNavigationCallbacks(arrayAdapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                //Toast.makeText(MyActivity.this,itemPosition,Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        hideFragment();
        switch (checkedId) {
            case R.id.menu_friend:
                showFragment(R.id.menu_friend + "",FriendFragment.class);
                break;
            case R.id.menu_friend1:
                showFragment(R.id.menu_friend1 + "",Friend1Fragment.class);
                break;
            case R.id.menu_me:
                showFragment(R.id.menu_me + "",MeFragment.class);
                break;
            default:
                showFragment(R.id.menu_friend + "", FriendFragment.class);
                break;
        }
    }

    private void showFragment(String tag,Class clz){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment != null) {
            ft.show(fragment);
        } else {
            try {
                fragment = (Fragment) clz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            ft.add(R.id.main_content,fragment, tag);
        }
        ft.commit();
    }

    private void hideFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        List<Fragment> fragmentList = fm.getFragments();
        if (fragmentList != null && fragmentList.size() > 0) {
            for (Fragment fragment : fragmentList) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }
}
