package cn.tidylink.familiar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import cn.tidylink.familiar.R;

/**
 * Created by LT on 2015/10/8 0008.
 */
public class LoginActivity extends BaseActivity implements OnClickListener{
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.Login_Button_login);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
