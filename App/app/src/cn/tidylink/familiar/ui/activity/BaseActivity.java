package cn.tidylink.familiar.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import cn.tidylink.familiar.AppManager;

/**
 * Created by LT on 2015/10/9 0009.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_bg));
    }
}
